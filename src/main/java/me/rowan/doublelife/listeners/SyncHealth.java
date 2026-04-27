package me.rowan.doublelife.listeners;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.SaveManager;
import me.rowan.doublelife.scoreboard.TeamHandler;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SyncHealth implements Listener {

    private final List<UUID> justDiedIdList = new ArrayList<>();
    private final List<UUID> killOnJoinIdList = new ArrayList<>();

    @EventHandler
    public void killIfSoulmateDiedWhileOffline(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (
            !killOnJoinIdList.contains(playerId) ||
            player.getGameMode() == GameMode.SPECTATOR
        )
        { return; }

        Bukkit.getScheduler().runTaskLater(DoubleLife.instance, () -> player.setHealth(0.0d), 100);
    }

    @EventHandler
    public void synchronizeHealthAfterJoining(PlayerJoinEvent event){
        Player player = event.getPlayer();

        double pairHealth = SaveManager.getPairHealth(player);
        if (pairHealth <= 0.0d) { return; }

        player.setHealth(pairHealth);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void killBackup(PlayerDeathEvent event){
        Player playerWhoDied = event.getEntity();
        if (killOnJoinIdList.contains(playerWhoDied.getUniqueId())) { return; }

        Bukkit.getScheduler().runTask(DoubleLife.instance, () -> {
            Player soulmate = SaveManager.getSoulmate(playerWhoDied);
            if (soulmate == null || justDiedIdList.contains(soulmate.getUniqueId())) { return; }
            soulmate.setHealth(0);
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void removeLifeOnDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        UUID playerId = player.getUniqueId();

        int livesAmount = SaveManager.getPairLivesAmount(player);
        if (killOnJoinIdList.contains(playerId)) {
            killOnJoinIdList.remove(playerId);
            event.setDeathMessage(TeamHandler.livesNumberToColor(livesAmount) + player.getPlayerListName() + ChatColor.RESET + " was killed for leaving their soulmate on their own!");
            return;
        }

        if (justDiedIdList.contains(playerId)) { return; }
        justDiedIdList.add(playerId);

        Bukkit.getScheduler().runTaskLater(DoubleLife.instance, () -> justDiedIdList.remove(playerId), 5);

        boolean killOfflineSoulmate = DoubleLife.instance.getConfig().getBoolean("misc.kill-soulmate-on-join-if-offline-during-death");
        OfflinePlayer offlineSoulmate = SaveManager.getOfflineSoulmate(player);

        // Return if the player doesn't have a soulmate
        if (offlineSoulmate == null) { return; }

        if (offlineSoulmate.isOnline()) {
            // Return if soulmate is online and has already executed `updatePlayerPair()`
            if (this.justDiedIdList.contains(offlineSoulmate.getUniqueId())) {
                event.setDeathMessage(null);
                return;
            }
        } else if (killOfflineSoulmate) {
            // Add to killOnJoinIdList if soulmate is offline and the option is enabled
            this.killOnJoinIdList.add(offlineSoulmate.getUniqueId());
        }

        ChatColor newLifeColour = TeamHandler.livesNumberToColor(livesAmount - 1);
        event.setDeathMessage(newLifeColour + player.getName() + ChatColor.RESET + " died and took " + newLifeColour + offlineSoulmate.getName() + ChatColor.RESET + " with them!");
        DoubleLife.instance.getLogger().info(event.getDeathMessage());

        this.updatePlayerPair(player);
    }

    private void updatePlayerPair(Player pairMember) {
        boolean playExplosion = DoubleLife.instance.getConfig().getBoolean("misc.global-explosion-sound-on-final-death");
        int currentLivesAmount = SaveManager.getPairLivesAmount(pairMember);

        if ((currentLivesAmount - 1) == 0 && playExplosion) {
            pairMember.getWorld().playSound(pairMember.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
        }

        SaveManager.setPairLivesAmount(pairMember, (currentLivesAmount - 1));
        SaveManager.setPairHealth(pairMember, 20.0d);
    }

    @EventHandler
    public void damageEvent(EntityDamageEvent event) {
        if (
            !(event.getEntity() instanceof Player damagedPlayer) ||
            event.getCause() == EntityDamageEvent.DamageCause.CUSTOM ||
            killOnJoinIdList.contains(damagedPlayer.getUniqueId()) ||
            damagedPlayer.getNoDamageTicks() > 10
        )
        { return; }

        double finalDamage = event.getFinalDamage();

        Player soulmate = SaveManager.getSoulmate(damagedPlayer);
        if (soulmate != null && !soulmate.isDead()) {
            soulmate.damage(finalDamage);
        }

        SaveManager.setPairHealth(damagedPlayer, damagedPlayer.getHealth() - finalDamage);
    }

    @EventHandler
    public void regainHealthEvent(EntityRegainHealthEvent event){
        if (
            !(event.getEntity() instanceof Player healedPlayer) ||
            event.getRegainReason() == EntityRegainHealthEvent.RegainReason.CUSTOM
        )
        { return; }

        double newHealth = healedPlayer.getHealth() + event.getAmount();
        Player soulmate = SaveManager.getSoulmate(healedPlayer);

        if (soulmate != null && !soulmate.isDead()) {
            newHealth = Math.clamp(newHealth, 0.0d, 20.0d);

            AttributeInstance maxHealthAttribute = soulmate.getAttribute(Attribute.MAX_HEALTH);
            if (maxHealthAttribute != null) {
                newHealth = Math.clamp(newHealth, 0.0d, maxHealthAttribute.getValue());
            }

            soulmate.sendHealthUpdate(newHealth, soulmate.getFoodLevel(), soulmate.getSaturation());
            soulmate.setHealth(newHealth);
        }

        SaveManager.setPairHealth(healedPlayer, healedPlayer.getHealth());
    }

}

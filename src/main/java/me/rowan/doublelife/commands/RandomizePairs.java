package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.SaveManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomizePairs {

    private static boolean rolling = false;

    public static void onCommand(CommandSender sender, String[] args) {
        if (rolling) { return; }

        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(ChatColor.RED + "This is a player only command!");
            return;
        }
        if ((args.length != 2) || !args[1].equalsIgnoreCase("confirm")){
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Are you sure? " + ChatColor.RESET + ChatColor.GRAY + "Please run '/dl randomize confirm' to execute the command!");
            return;
        }

        SaveManager.clearFile();

        Player executor = (Player) sender;
        World world = executor.getWorld();

        List<UUID> availablePlayers = new ArrayList<>();
        for(Player player : world.getPlayers()) {
            if (!player.hasPermission("doublelife.nopair")) {
                availablePlayers.add(player.getUniqueId());
            }
        }

        if (availablePlayers.size() == 1){
            sender.sendMessage(ChatColor.DARK_RED + "Not enough players!");
            return;
        }
        rolling = true;

        availablePlayers.forEach(playerUUID -> {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                player.sendTitle(ChatColor.GREEN + "Your soulmate is..", null, 10, 100, 10);
            }
        });

        Bukkit.getScheduler().runTaskLater(DoubleLife.instance, () -> {
            Random r = new Random();
            while (availablePlayers.size() > 1) {
                UUID playerOneUUID = availablePlayers.get(r.nextInt(availablePlayers.size()));
                UUID playerTwoUUID = availablePlayers.get(r.nextInt(availablePlayers.size()));
                if (playerOneUUID != playerTwoUUID) {
                    availablePlayers.remove(playerOneUUID);
                    availablePlayers.remove(playerTwoUUID);
                    SaveManager.createPair(playerOneUUID, playerTwoUUID, DoubleLife.instance.getConfig().getInt("misc.max-lives"));

                    Player playerOne = Bukkit.getPlayer(playerOneUUID);
                    Player playerTwo = Bukkit.getPlayer(playerTwoUUID);
                    if (playerOne != null && playerTwo != null) {
                        if (DoubleLife.instance.getConfig().getBoolean("misc.reveal-soulmates")) {
                            playerOne.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + playerTwo.getName(), null, 10, 100, 10);
                            playerOne.playSound(playerOne, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                            playerTwo.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + playerOne.getName(), null, 10, 100, 10);
                            playerTwo.playSound(playerTwo, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                        } else {
                            playerOne.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + "????", null, 10, 100, 10);
                            playerOne.playSound(playerOne, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                            playerTwo.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + "????", null, 10, 100, 10);
                            playerTwo.playSound(playerTwo, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                        }
                    }
                }
            }
            rolling = false;
        }, 100);

        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "Soulmates are being randomized!");
    }

}
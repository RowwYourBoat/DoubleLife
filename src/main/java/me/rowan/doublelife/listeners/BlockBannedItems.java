package me.rowan.doublelife.listeners;

import me.rowan.doublelife.DoubleLife;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class BlockBannedItems implements Listener {

    @EventHandler
    public void discoverRecipesOnJoin(PlayerJoinEvent event) {
        DoubleLife.instance.playerDiscoverRecipes(event.getPlayer());
    }

    @EventHandler
    public void blockEnchantedGoldenApples(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null)
            return;

        if (itemStack.getType() == Material.ENCHANTED_GOLDEN_APPLE && DoubleLife.instance.getConfig().getBoolean("items.ban-god-apples")) {

            itemStack.setAmount(0);
            for (HumanEntity viewer : event.getClickedInventory().getViewers()) {
                Player cheater = (Player) viewer;
                cheater.sendMessage(ChatColor.DARK_RED + "Enchanted Golden Apples aren't allowed!");
            }
        }
    }

    @EventHandler
    public void blockVillagerTrading(VillagerAcquireTradeEvent event) {
        DoubleLife.instance.getLogger().info("VillagerAcquireTradeEvent");
//        event.setCancelled(DoubleLife.instance.getConfig().getBoolean("misc.kill-villagers"));
    }



    @EventHandler
    public void blockBookshelfRecipe(CraftItemEvent event) {
        Material recipeType = event.getRecipe().getResult().getType();

        if (
            recipeType != Material.BOOKSHELF ||
            DoubleLife.instance.getConfig().getBoolean("enchantments.bookshelves-craftable")
        ) { return; }

        event.setCancelled(true);
    }

    @EventHandler
    public void potionBlacklistCancelBrewing(BrewEvent event) {
        FileConfiguration config = DoubleLife.instance.getConfig();
        if (!config.getBoolean("items.potions.whitelist-enabled")) { return; }

        List<String> potionWhitelist = config.getStringList("items.potions.whitelist");
        if (potionWhitelist.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        for (ItemStack item : event.getResults()) {
            if (
                item == null ||
                !(item.getItemMeta() instanceof PotionMeta potionMeta)
            ) { continue; }

            PotionType potionType = potionMeta.getBasePotionType();
            if (
                potionType == null ||
                potionWhitelist.contains(potionType.name())
            ) { continue; }

            for (HumanEntity viewer : event.getContents().getViewers()) {
                viewer.sendMessage(ChatColor.DARK_RED + "The type of potion you're trying to brew isn't on the whitelist!");
            }

            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void removeHelmets(InventoryClickEvent event) {
        if (!DoubleLife.instance.getConfig().getBoolean("items.ban-helmets"))
            return;

        ItemStack item = event.getCurrentItem();
        if (item != null) {
            if (item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.GOLDEN_HELMET || item.getType() == Material.TURTLE_HELMET || item.getType() == Material.NETHERITE_HELMET){
                event.setCancelled(true);
                item.setAmount(0);
                for (HumanEntity viewer : event.getInventory().getViewers()) {
                    Player cheater = (Player) viewer;
                    cheater.sendMessage(ChatColor.DARK_RED + "Helmets are banned!");
                }
            }
        }
    }

}
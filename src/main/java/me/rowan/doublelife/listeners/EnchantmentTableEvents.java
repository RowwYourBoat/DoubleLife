package me.rowan.doublelife.listeners;

import me.rowan.doublelife.DoubleLife;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentTableEvents implements Listener {

    @EventHandler
    public void blockEnchantingTableRecipe(CraftItemEvent event) {
        Material recipeType = event.getRecipe().getResult().getType();

        boolean enchantmentTableCraftable = DoubleLife.instance.getConfig().getBoolean("enchantments.enchantment-table-craftable");
        boolean isEnchantmentTable = recipeType == Material.ENCHANTING_TABLE;
        if (enchantmentTableCraftable || !isEnchantmentTable) { return; }

        event.setCancelled(true);
    }

    @EventHandler
    public void makeEnchantmentTableIndestructibleOnDrop(PlayerDropItemEvent event) {
        if (!DoubleLife.instance.getConfig().getBoolean("enchantments.enchantment-table-indestructible-on-drop"))
            return;
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (droppedItem.getType() == Material.ENCHANTING_TABLE){
            Item item = event.getItemDrop();
            item.setCustomName("Invulnerable");
            item.setUnlimitedLifetime(true);
            item.setInvulnerable(true);
        }
    }

    @EventHandler
    public void preventExplodingOfEnchantmentTable(EntityDamageByEntityEvent event){
        if (!DoubleLife.instance.getConfig().getBoolean("enchantments.enchantment-table-indestructible-on-drop"))
            return;
        for (Entity entity : event.getDamager().getNearbyEntities(10,10,10)) {
            if (entity.getType() == EntityType.ITEM){
                if (entity.getCustomName() != null) {
                    if (entity.getCustomName().equalsIgnoreCase("Invulnerable")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void blockDestroyingEnchantmentTable(BlockBreakEvent event) {
        if (DoubleLife.instance.getConfig().getBoolean("enchantments.enchantment-table-breakable"))
            return;

        if (event.getBlock().getType() == Material.ENCHANTING_TABLE) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.DARK_RED +"You're not allowed to break the enchantment table!");
        }
    }

}

package me.rowwyourboat.doublelife_v2.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    private ItemStack item;
    private String actionId;

    public MenuItem(String actionId, Material material, String displayName, boolean glint) {
        this.actionId = actionId;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;
        boolean noDisplayName = displayName == null;
        if (noDisplayName) displayName = " ";
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (!noDisplayName) {
            List<String> loreList = new ArrayList<>();
            loreList.add(ChatColor.GRAY + "Click to open page");
            meta.setLore(loreList);
        }

        if (glint) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, false);
        }

        item.setItemMeta(meta);
        this.item = item;
    }

    public String getActionId() {
        return this.actionId;
    }

    public ItemStack getItemStack() {
        return item;
    }

}

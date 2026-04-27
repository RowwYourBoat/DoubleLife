package me.rowan.doublelife.listeners;

import me.rowan.doublelife.DoubleLife;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NerfEnchantments implements Listener {

    private void nerfEnchantableItem(ItemStack toolItem, int maxLevel, HashMap<String, Integer> nerfMap) {
        if (!(toolItem.getItemMeta() instanceof ItemMeta itemMeta)) { return; }

        Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
        List<Map.Entry<Enchantment, Integer>> exceedingEnchantEntryList =
            enchantments.entrySet().stream().filter((enchantment) -> enchantment.getValue() > maxLevel).toList();

        if (exceedingEnchantEntryList.isEmpty()) { return; }

        for (Map.Entry<Enchantment, Integer> exceedingEnchantEntry : exceedingEnchantEntryList) {
            Enchantment enchantment = exceedingEnchantEntry.getKey();
            int oldLevel = exceedingEnchantEntry.getValue();

            itemMeta.removeEnchant(enchantment);

            // Use deprecated getKey() method because alternatives cause a `NoSuchMethodError` exception
            @SuppressWarnings("deprecation") NamespacedKey namespacedKey = enchantment.getKey();

            nerfMap.put(namespacedKey.getKey(), oldLevel);
            itemMeta.addEnchant(enchantment, maxLevel, false);
        }

        toolItem.setItemMeta(itemMeta);
    }

    private void nerfEnchantmentBook(ItemStack book, int maxLevel, HashMap<String, Integer> nerfMap) {
        if (!(book.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta)) { return; }

        Map<Enchantment, Integer> enchantments = enchantmentStorageMeta.getStoredEnchants();
        List<Map.Entry<Enchantment, Integer>> exceedingEnchantEntryList =
            enchantments.entrySet().stream().filter((enchantment) -> enchantment.getValue() > maxLevel).toList();

        if (exceedingEnchantEntryList.isEmpty()) { return; }

        for (Map.Entry<Enchantment, Integer> exceedingEnchantEntry : exceedingEnchantEntryList) {
            Enchantment enchantment = exceedingEnchantEntry.getKey();
            int oldLevel = exceedingEnchantEntry.getValue();

            enchantmentStorageMeta.removeEnchant(enchantment);

            // Use deprecated getKey() method because alternatives cause a `NoSuchMethodError` exception
            @SuppressWarnings("deprecation") NamespacedKey namespacedKey = enchantment.getKey();

            nerfMap.put(namespacedKey.getKey(), oldLevel);
            enchantmentStorageMeta.addStoredEnchant(enchantment, maxLevel, false);
        }

        book.setItemMeta(enchantmentStorageMeta);
    }

    @EventHandler
    public void nerfGeneratedLoot(LootGenerateEvent event) {
        int maxEnchantLevel = DoubleLife.instance.getConfig().getInt("enchantments.max-level");
        if (maxEnchantLevel == -1) { return; }

        HashMap<String, Integer> nerfMap = new HashMap<>();
        for (ItemStack lootItem : event.getLoot()) {
            if (lootItem.getType() == Material.ENCHANTED_BOOK) {
                nerfEnchantmentBook(lootItem, maxEnchantLevel, nerfMap);
            } else {
                nerfEnchantableItem(lootItem, maxEnchantLevel, nerfMap);
            }
        }

        if (nerfMap.isEmpty()) { return; }

        for (String enchantmentName : nerfMap.keySet()) {
            String message = String.format(
                """
                The %s enchantment has been nerfed from level %d to %d.
                """,
                enchantmentName.toUpperCase(),
                nerfMap.get(enchantmentName),
                maxEnchantLevel
            );
            Bukkit.broadcastMessage(ChatColor.DARK_RED + message);
        }
    }

}

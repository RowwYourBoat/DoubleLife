package me.rowwyourboat.doublelife_v2.ui;

import me.rowwyourboat.doublelife_v2.DoubleLife;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class InventoryHandler implements Listener {

    private HashMap<String, Page> pageDefinitions;
    private HashMap<UUID, Page> currentPage;

    private final InventoryParser inventoryParser;

    public InventoryHandler() {
        this.inventoryParser = new InventoryParser();
        this.currentPage = new HashMap<>();

        this.initPageDefinitions();

        Bukkit.getPluginManager().registerEvents(this, DoubleLife.instance);
    }

    public void openInventory(Player player, String identifier) {
        this.inventoryParser.parseInventories();
    }

    private void initPageDefinitions() {

    }

//    @EventHandler
//    private void onInventoryClick(InventoryClickEvent event) {
//        event.setCancelled(true);
//        Inventory inventory = event.getInventory();
//        Player player = (Player) event.getWhoClicked();
//        DoubleLife.instance.getLogger().info(event.getWhoClicked().getName());
//        if (inventory != currentPage.get(player.getUniqueId()).getInventory()) return;
//    }

}

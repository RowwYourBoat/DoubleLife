package me.rowwyourboat.doublelife_v2.menu;

import me.rowwyourboat.doublelife_v2.DoubleLife;
import me.rowwyourboat.doublelife_v2.menu.content.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryHandler implements Listener {

    private List<Page> pages;
    private HashMap<UUID, Page> currentPage;

    public InventoryHandler() {
        this.definePages();
        this.currentPage = new HashMap<>();
    }

    public void openMainMenu(Player player) {
        this.currentPage.put(player.getUniqueId(), pages.getFirst());

        player.openInventory(pages.getFirst().getInventory());
    }

    public void performAction() {

    }

    private void definePages() {
        this.pages = new ArrayList<>();
        pages.add(new Page("MAIN_MENU", "Main Menu", 54, MainMenu.getContent()));
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

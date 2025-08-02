package me.rowwyourboat.doublelife_v2.menu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Page {

    public final String id;

    private Inventory inventory;
    private final Integer size;
    private final String title;
    private final HashMap<Integer, MenuItem> content;

    public Page(String id, String title, int size, HashMap<Integer, MenuItem> content) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.content = content;
    }

    public Inventory getInventory() {
        this.generateAndOpenInventory();
        return inventory;
    }

    private void generateAndOpenInventory() {
        this.inventory = Bukkit.createInventory(null, size, title);
        for (int slotId : content.keySet()) {
            this.inventory.setItem(slotId, content.get(slotId).getItemStack());
        }
    }

}

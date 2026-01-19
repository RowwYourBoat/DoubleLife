package me.rowwyourboat.doublelife_v2.ui.content;

import me.rowwyourboat.doublelife_v2.ui.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;

public final class Template {

    private static HashMap<Integer, MenuItem> templateContent;

    public static HashMap<Integer, MenuItem> getContent() {
        if (templateContent != null) return templateContent;

        HashMap<Integer, MenuItem> content = new HashMap<>();

        content.put(0, new MenuItem("NAV_BACK", Material.ARROW, ChatColor.RED + "Back", false));

        // top row
        for (int i = 1; i < 8; i++) {
            content.put(i, new MenuItem(null, Material.BLACK_STAINED_GLASS_PANE, null, false));
        }

        // bottom row
        for (int i = 45; i < 54; i++) {
            content.put(i, new MenuItem(null, Material.BLACK_STAINED_GLASS_PANE, null, false));
        }

        // left side column
        for (int i = 9; i < 37; i = i + 9) {
            content.put(i, new MenuItem(null, Material.BLACK_STAINED_GLASS_PANE, null, false));
        }

        // right side column
        for (int i = 8; i < 45; i = i + 9) {
            content.put(i, new MenuItem(null, Material.BLACK_STAINED_GLASS_PANE, null, false));
        }

        templateContent = content;
        return content;
    }

}

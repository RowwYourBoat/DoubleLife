package me.rowwyourboat.doublelife_v2.ui.content;

import me.rowwyourboat.doublelife_v2.ui.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;

public final class MainMenu {

    public static HashMap<Integer, MenuItem> getContent() {
        HashMap<Integer, MenuItem> content = Template.getContent();

        content.put(20, new MenuItem("NAV_CONFIG", Material.COMMAND_BLOCK, ChatColor.GOLD + ChatColor.BOLD.toString() + "Configuration", true));

        return content;
    }

}
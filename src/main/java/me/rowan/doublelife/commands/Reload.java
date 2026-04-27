package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Reload {

    public static void onCommand(CommandSender sender) {
        DoubleLife.instance.reloadConfig();
        sender.sendMessage(ChatColor.DARK_GREEN + "You've reloaded the configuration file!");
    }

}

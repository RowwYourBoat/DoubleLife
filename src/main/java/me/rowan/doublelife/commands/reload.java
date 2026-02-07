package me.rowan.doublelife.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class reload {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.DARK_GREEN + "You've reloaded the configuration file!");
        return true;
    }

}

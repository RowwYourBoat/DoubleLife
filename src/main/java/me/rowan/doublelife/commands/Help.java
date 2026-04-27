package me.rowan.doublelife.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Help {

    public static void onCommand(CommandSender sender) {
        sender.sendMessage(
                ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Double Life Help: (Page 1/1)" + ChatColor.RESET +
                        ChatColor.GREEN + "\n\n/dl help - " +
                        ChatColor.GRAY + "Shows this list." +
                        ChatColor.GREEN + "\n\n/dl reload - " +
                        ChatColor.GRAY + "Loads changes from the configuration file." +
                        ChatColor.GREEN + "\n\n/dl config <setting> <value> - " +
                        ChatColor.GRAY + "Changes plugin settings without having to restart the server." +
                        ChatColor.GREEN + "\n\n/dl setup - " +
                        ChatColor.GRAY + "Sets the world spawn at your current location and sets up the world border." +
                        ChatColor.GREEN + "\n\n/dl randomizepairs - " +
                        ChatColor.GRAY + "Randomizes soulmates." +
                        ChatColor.GREEN + "\n\n/dl pair <player1> <player2> [<lives>] - " +
                        ChatColor.GRAY + "Pairs the two specified players together and optionally gives them the specified number of lives." +
                        ChatColor.GREEN + "\n\n/dl unpair <player> - " +
                        ChatColor.GRAY + "Disbands the pair of the specified player." +
                        ChatColor.GREEN + "\n\n/dl lives <player> [set <number>] - " +
                        ChatColor.GRAY + "Gets or sets the number of lives the specified player's pair has."
        );
    }

}

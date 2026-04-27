package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.SaveManager;
import me.rowan.doublelife.util.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Lives {

    public static void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid arguments! Usage: /dl lives <player> [set <number>]");
            return;
        }

        Player playerFromArgument = Bukkit.getPlayer(args[1]);
        if (playerFromArgument == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid player argument!");
            return;
        }

        OfflinePlayer soulmate = SaveManager.getOfflineSoulmate(playerFromArgument);
        if (soulmate == null) {
            sender.sendMessage(ChatColor.DARK_RED + playerFromArgument.getName() + " doesn't have a soulmate!");
            return;
        }

        if (args.length < 3) {
            int livesNum = SaveManager.getPairLivesAmount(playerFromArgument);
            sender.sendMessage(
                ChatColor.GREEN + playerFromArgument.getName() + ChatColor.DARK_GREEN + " and " + ChatColor.GREEN + soulmate.getName() +
                ChatColor.DARK_GREEN + " are on " + ChatColor.GREEN + livesNum + ChatColor.DARK_GREEN + " " + CommandHelper.toLivesString(livesNum) + "!"
            );
            return;
        }

        String setArgument = args[2];
        if (!setArgument.equalsIgnoreCase("set")) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid argument! Usage: /dl lives <player> [set <number>]");
            return;
        }

        if (args.length < 4) {
            sender.sendMessage(ChatColor.DARK_RED + "Missing number argument! Usage: /dl lives <player> [set <number>]");
            return;
        }

        int livesToSet;
        try {
            livesToSet = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid number argument!");
            return;
        }

        int maxLivesNum = DoubleLife.instance.getConfig().getInt("misc.max-lives");
        if (livesToSet < 0 || livesToSet > maxLivesNum) {
            sender.sendMessage(ChatColor.DARK_RED + "The number of lives must be between 0 and " + maxLivesNum + "!");
            return;
        }

        SaveManager.setPairLivesAmount(playerFromArgument, livesToSet);

        sender.sendMessage(
            ChatColor.GREEN + playerFromArgument.getName() + ChatColor.DARK_GREEN + " and " + ChatColor.GREEN + soulmate.getName() +
            ChatColor.DARK_GREEN + " are now on " + ChatColor.GREEN + livesToSet + ChatColor.DARK_GREEN + " " + CommandHelper.toLivesString(livesToSet) + "!"
        );
    }

    public static List<String> getAppropriateArguments(String[] args) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                arguments.add(player.getName());
            }
        } else if (args.length == 3) {
            arguments.add("set");
        } else if (args.length == 4 && args[2].equalsIgnoreCase("set")){
            arguments.add("<number>");
        }

        return arguments;
    }

}

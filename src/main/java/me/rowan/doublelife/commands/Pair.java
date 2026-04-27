package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.SaveManager;
import me.rowan.doublelife.util.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    public static void onCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid arguments! Usage: /dl pair <player1> <player2> [<lives>]");
            return;
        }

        Player playerOne = Bukkit.getPlayer(args[1]);
        Player playerTwo = Bukkit.getPlayer(args[2]);

        int lives = DoubleLife.instance.getConfig().getInt("misc.max-lives"); // use max lives number if not specified
        if (args.length == 4) {
            try {
                lives = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_RED + "Invalid number argument! Usage: /dl pair <player1> <player2> [<lives>]");
                return;
            }
        }

        if (playerOne == playerTwo) {
            sender.sendMessage(ChatColor.DARK_RED + "You can't pair someone to themselves!");
            return;
        }

        if (playerOne == null || playerTwo == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid player arguments!");
            return;
        }

        if (SaveManager.getPair(playerOne) != null || SaveManager.getPair(playerTwo) != null) {
            sender.sendMessage(ChatColor.DARK_RED + "One of those players is already paired!");
            return;
        }

        SaveManager.createPair(playerOne.getUniqueId(), playerTwo.getUniqueId(), lives);
        sender.sendMessage(
            ChatColor.DARK_GREEN + "You've successfully paired " + ChatColor.GREEN + playerOne.getName() + ChatColor.DARK_GREEN + " and " + ChatColor.GREEN + playerTwo.getName() +
            ChatColor.DARK_GREEN + " with " + ChatColor.GREEN + lives + ChatColor.DARK_GREEN + " " + CommandHelper.toLivesString(lives) + "!"
        );
    }

    public static List<String> getAppropriateArguments(String[] args) {
        List<String> arguments = new ArrayList<>();

        List<String> playerNames = new ArrayList<>(
            Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()
        );

        if (args.length == 2) {
            return playerNames;
        } else if (args.length == 3) {
            String firstPlayerName = args[1];
            return playerNames.stream().filter((name) -> !name.equals(firstPlayerName)).toList();
        } else if (args.length == 4) {
            arguments.add("<lives>");
        }

        return arguments;
    }

}

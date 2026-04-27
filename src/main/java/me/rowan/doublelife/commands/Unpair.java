package me.rowan.doublelife.commands;

import me.rowan.doublelife.data.SaveManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Unpair {

    public static void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) { return; }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null){
            sender.sendMessage(ChatColor.DARK_RED + "Invalid player argument!");
            return;
        }

        if (SaveManager.getPair(player) == null) {
            sender.sendMessage(ChatColor.DARK_RED + "That player doesn't have a soulmate!");
            return;
        }

        SaveManager.deletePair(player);
        sender.sendMessage(ChatColor.DARK_GREEN + "You've successfully unpaired " + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN +  "!");
    }

    public static List<String> getAppropriateArguments(String[] args) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 2){
            Bukkit.getOnlinePlayers().forEach(player -> arguments.add(player.getName()));
        }

        return arguments;
    }

}

package me.rowan.doublelife.commands;

import me.rowan.doublelife.data.SaveManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class unpair {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2)
            return false;

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null){
            sender.sendMessage(ChatColor.DARK_RED + "Invalid player argument!");
            return true;
        }

        if (SaveManager.getPair(player) == null){
            sender.sendMessage(ChatColor.DARK_RED + "That player doesn't have a soulmate!");
            return true;
        }

        SaveManager.deletePair(player);
        sender.sendMessage(ChatColor.DARK_GREEN + "You've successfully unpaired " + player.getName() + "!");
        return true;
    }

    public static List<String> getAppropriateArguments(String[] args) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 2){
            Bukkit.getOnlinePlayers().forEach(player -> {
                arguments.add(player.getName());
            });
        }

        return arguments;
    }

}

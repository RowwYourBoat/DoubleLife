package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class config {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2)
            return false;

        if (!argumentIsLegal(args[1].toLowerCase())) {
            sender.sendMessage(ChatColor.DARK_RED + "Illegal setting argument! That setting can't be changed from within the game!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.DARK_RED + "Missing arguments!");
            return true;
        }

        String settingToChange = args[1].toLowerCase();
        String newValue = args[2].toLowerCase();
        if (!DoubleLife.plugin.getConfig().contains(settingToChange)) {
            sender.sendMessage(ChatColor.DARK_RED + "That setting doesn't exist.");
            return true;
        }

        if (DoubleLife.plugin.getConfig().get(settingToChange).toString().equalsIgnoreCase("false") || DoubleLife.plugin.getConfig().get(settingToChange).toString().equalsIgnoreCase("true")) {
            if (newValue.equalsIgnoreCase("false") || newValue.equalsIgnoreCase("true")){
                DoubleLife.plugin.getConfig().set(settingToChange, Boolean.parseBoolean(newValue));
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "That setting is a boolean value!");
                return true;
            }
        } else {
            int newValueInt;
            try {
                newValueInt = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_RED + "That setting is a number value!");
                return true;
            }
            DoubleLife.plugin.getConfig().set(settingToChange, newValueInt);
        }

        sender.sendMessage(ChatColor.DARK_GREEN + "You've successfully changed the setting " + ChatColor.GREEN + settingToChange + ChatColor.DARK_GREEN + " to " + ChatColor.GREEN + newValue + ChatColor.DARK_GREEN + "!");
        DoubleLife.plugin.saveConfig();
        DoubleLife.plugin.reloadConfig();
        return true;
    }

    public static List<String> getAppropriateArguments(String[] args){
        List<String> finalArguments = new ArrayList<>();

        Set<String> settings = DoubleLife.plugin.getConfig().getValues(true).keySet();
        if (args.length == 2) {
            settings.forEach(setting -> {
                if (argumentIsLegal(setting))
                    finalArguments.add(setting);
            });
        } else if (args.length == 3) {
            String setting = args[1];
            if (setting != null && DoubleLife.plugin.getConfig().contains(setting.toLowerCase())) {
                if (argumentIsLegal(setting.toLowerCase())) {
                    if (DoubleLife.plugin.getConfig().get(setting.toLowerCase()).toString().equalsIgnoreCase("false") || DoubleLife.plugin.getConfig().get(setting.toLowerCase()).toString().equalsIgnoreCase("true")) {
                        finalArguments.add("true");
                        finalArguments.add("false");
                    } else {
                        finalArguments.add("num");
                    }
                }
            }
        }

        return finalArguments;
    }

    private static boolean argumentIsLegal(String setting) {
        return !setting.equalsIgnoreCase("gamerules") && !setting.equalsIgnoreCase("items") && !setting.equalsIgnoreCase("items.potions") && !setting.equalsIgnoreCase("items.potions.whitelist") && !setting.equalsIgnoreCase("enchantments") && !setting.equalsIgnoreCase("recipes") && !setting.equalsIgnoreCase("misc") && !setting.equalsIgnoreCase("config-version");
    }

}

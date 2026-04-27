package me.rowan.doublelife.commands;

import me.rowan.doublelife.DoubleLife;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    public static void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) { return; }
        DoubleLife plugin = DoubleLife.instance;

        if (!argumentIsLegal(args[1].toLowerCase())) {
            sender.sendMessage(ChatColor.DARK_RED + "Illegal setting argument! That setting can't be changed from within the game!");
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.DARK_RED + "Missing arguments!");
            return;
        }

        String settingToChange = args[1].toLowerCase();
        String newValue = args[2].toLowerCase();
        if (!DoubleLife.instance.getConfig().contains(settingToChange)) {
            sender.sendMessage(ChatColor.DARK_RED + "That setting doesn't exist.");
            return;
        }

        if (plugin.getConfig().get(settingToChange).toString().equalsIgnoreCase("false") || plugin.getConfig().get(settingToChange).toString().equalsIgnoreCase("true")) {
            if (newValue.equalsIgnoreCase("false") || newValue.equalsIgnoreCase("true")){
                plugin.getConfig().set(settingToChange, Boolean.parseBoolean(newValue));
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "That setting is a boolean value!");
                return;
            }
        } else {
            int newValueInt;
            try {
                newValueInt = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_RED + "That setting is a number value!");
                return;
            }
            plugin.getConfig().set(settingToChange, newValueInt);
        }

        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.DARK_GREEN + "You've successfully changed the setting " + ChatColor.GREEN + settingToChange + ChatColor.DARK_GREEN + " to " + ChatColor.GREEN + newValue + ChatColor.DARK_GREEN + "!");
    }

    public static List<String> getAppropriateArguments(String[] args){
        DoubleLife plugin = DoubleLife.instance;
        List<String> finalArguments = new ArrayList<>();

        Set<String> settings = plugin.getConfig().getValues(true).keySet();
        if (args.length == 2) {
            settings.forEach(setting -> {
                if (argumentIsLegal(setting))
                    finalArguments.add(setting);
            });
        } else if (args.length == 3) {
            String setting = args[1];
            if (setting != null && plugin.getConfig().contains(setting.toLowerCase())) {
                if (argumentIsLegal(setting.toLowerCase())) {
                    if (plugin.getConfig().get(setting.toLowerCase()).toString().equalsIgnoreCase("false") || plugin.getConfig().get(setting.toLowerCase()).toString().equalsIgnoreCase("true")) {
                        finalArguments.add("true");
                        finalArguments.add("false");
                    } else {
                        finalArguments.add("<number>");
                    }
                }
            }
        }

        return finalArguments;
    }

    private static boolean argumentIsLegal(String setting) {
        List<String> illegalArguments = List.of("gamerules", "items", "items.potions", "items.potions.whitelist", "enchantments", "recipes", "misc", "config-version");
        return !illegalArguments.contains(setting);
    }

}

package me.rowwyourboat.doublelife_v2;

import me.rowwyourboat.doublelife_v2.ui.InventoryHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final InventoryHandler inventoryHandler;

    public CommandHandler() {
        this.inventoryHandler = new InventoryHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return this.sendError(sender, ChatColor.DARK_RED + "Only players can run this command.");

        this.inventoryHandler.openInventory(player, "main_menu");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return List.of();
    }

    private boolean sendError(CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

}

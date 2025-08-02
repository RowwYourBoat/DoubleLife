package me.rowwyourboat.doublelife_v2;

import me.rowwyourboat.doublelife_v2.menu.CommandHandler;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;


// /give @p minecraft:command_block[minecraft:enchantments={unbreaking:2},  minecraft:custom_name={text:"Configuration", color:"gold", italic:false, bold:true},  tooltip_display={hidden_components:["minecraft:enchantments"]},  lore=[{text:"Click to view options", color:"gray", italic:false}]]
public final class DoubleLife extends JavaPlugin {

    public static DoubleLife instance;

    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        this.commandHandler = new CommandHandler();

        PluginCommand command = getCommand("doublelife");
        if (command == null) return;
        command.setExecutor(this.commandHandler);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package me.rowan.doublelife.data;


import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.config.ConfigEntry;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static me.rowan.doublelife.data.config.ConfigEntries.*;

public class ConfigManager {

    private static YamlConfiguration yamlConfig;
    private static File configFile;

    public static void init() {
        yamlConfig = new YamlConfiguration();
        configFile = new File(DoubleLife.instance.getDataFolder(), "config.yml");
        saveConfigFile();
        loadConfig();
    }

    private static void saveConfigFile() {
        configFile.getParentFile().mkdirs();
        DoubleLife.instance.saveResource("config.yml", false);
    }

    private static void loadConfig() {
        try {
            yamlConfig.load(configFile);
        } catch (Exception e) {
            DoubleLife.instance.getLogger().severe("Could not load config.yml, default config will be used.");
        }
    }

    private static ArrayList<ConfigEntry<?>> configEntries = new ArrayList<>(List.of(
            MAX_LIVES,
            BAN_SPECTATORS,
            ANNOUNCE_ELIMINATIONS,
            REVEAL_SOULMATES,

            BAN_HELMETS,
            BAN_BOOKSHELVES,
            BAN_GOD_APPLES,

            POTION_BLACKLIST_ENABLED,
            POTION_BLACKLIST,

            NERF,
            TABLE_CRAFTABLE,
            TABLE_BREAKABLE,
            TABLE_ITEM_INDESTRUCTIBLE,

            SPORE_BLOSSOM_RECIPE,
            NAME_TAG_RECIPE,
            SADDLE_RECIPE,
            PAPER_TNT_RECIPE
    ));

}

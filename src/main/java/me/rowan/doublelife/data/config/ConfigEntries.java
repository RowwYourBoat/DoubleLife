package me.rowan.doublelife.data.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigEntries {

    private static final ConfigSection SEASON = new ConfigSection("lives");
    public static final ConfigEntry<Integer> MAX_LIVES = new ConfigEntry<>(3, "start-and-max-amount", SEASON);
    public static final ConfigEntry<Boolean> BAN_SPECTATORS = new ConfigEntry<>(false, "ban-spectators", SEASON);
    public static final ConfigEntry<Boolean> ANNOUNCE_ELIMINATIONS = new ConfigEntry<>(true, "announce-eliminations", SEASON);
    public static final ConfigEntry<Boolean> REVEAL_SOULMATES = new ConfigEntry<>(false, "reveal-soulmates", SEASON);

    private static final ConfigSection BANNED_ITEMS = new ConfigSection("banned-items");
    public static final ConfigEntry<Boolean> BAN_HELMETS = new ConfigEntry<>(true, "helmets", BANNED_ITEMS);
    public static final ConfigEntry<Boolean> BAN_BOOKSHELVES = new ConfigEntry<>(true, "bookshelves", BANNED_ITEMS);
    public static final ConfigEntry<Boolean> BAN_GOD_APPLES = new ConfigEntry<>(true, "enchanted-golden-apples", BANNED_ITEMS);

    private static final ConfigSection BANNED_POTIONS = new ConfigSection("banned-potions");
    public static final ConfigEntry<Boolean> POTION_BLACKLIST_ENABLED = new ConfigEntry<>(true, "enabled", BANNED_POTIONS);
    public static final ConfigEntry<List<String>> POTION_BLACKLIST = new ConfigEntry<>(new ArrayList<>(), "blacklist", BANNED_POTIONS);

    private static final ConfigSection ENCHANTMENTS = new ConfigSection("enchantments");
    public static final ConfigEntry<Boolean> NERF = new ConfigEntry<>(true, "nerf-enchantment-stacking", ENCHANTMENTS); // Nerf enchantments on all acquired items regardless of source
    public static final ConfigEntry<Boolean> TABLE_CRAFTABLE = new ConfigEntry<>(false, "table-craftable", ENCHANTMENTS);
    public static final ConfigEntry<Boolean> TABLE_BREAKABLE = new ConfigEntry<>(false, "table-breakable", ENCHANTMENTS);
    public static final ConfigEntry<Boolean> TABLE_ITEM_INDESTRUCTIBLE = new ConfigEntry<>(true, "table-item-indestructible", ENCHANTMENTS);

    private static final ConfigSection CUSTOM_RECIPES = new ConfigSection("custom-recipes");
    public static final ConfigEntry<Boolean> SPORE_BLOSSOM_RECIPE = new ConfigEntry<>(true, "spore-blossom", CUSTOM_RECIPES);
    public static final ConfigEntry<Boolean> NAME_TAG_RECIPE = new ConfigEntry<>(true, "name-tag", CUSTOM_RECIPES);
    public static final ConfigEntry<Boolean> SADDLE_RECIPE = new ConfigEntry<>(true, "saddle", CUSTOM_RECIPES);
    public static final ConfigEntry<Boolean> PAPER_TNT_RECIPE = new ConfigEntry<>(true, "paper-tnt", CUSTOM_RECIPES);

}

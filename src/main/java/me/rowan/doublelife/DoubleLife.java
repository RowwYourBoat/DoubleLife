package me.rowan.doublelife;

import me.rowan.doublelife.commands.*;
import me.rowan.doublelife.data.SaveManager;
import me.rowan.doublelife.listeners.*;
import me.rowan.doublelife.scoreboard.TeamHandler;
import me.rowan.doublelife.util.CommandHelper;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class DoubleLife extends JavaPlugin {

    public static DoubleLife instance;
    private HashMap<NamespacedKey, ShapedRecipe> recipeMap;

    @Override
    public void onEnable() {
        instance = this;
        new Metrics(this, 16819);

        saveDefaultConfig();
        SaveManager.init();

        this.registerCommands();
        this.registerEventListeners();
        this.checkForUpdates();

        this.recipeMap = this.createRecipes();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerDiscoverRecipes(player);
        }
    }

    private void registerCommands() {
        PluginCommand pluginCommand = getCommand("doublelife");
        if (pluginCommand == null) { return; }
        pluginCommand.setExecutor(new MainCommandExecutor());
        pluginCommand.setTabCompleter(new MainTabCompleter());
    }

    private void registerEventListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Listener[] listeners = {
            new TeamHandler(),
            new SyncHealth(),
            new SyncEffects(),
            new BlockBannedItems(),
            new EnchantmentTableEvents(),
            new NerfEnchantments()
        };

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    private void checkForUpdates() {
        new UpdateChecker(this).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("The plugin is up to date!");
            } else {
                getLogger().warning(String.format(
                    """
                    \nThere is a newer version available! Updating will improve functionality and stability.
                    Running: %s  Newest: %s
                    You may download it here: https://modrinth.com/plugin/double-life
                    """,
                    this.getDescription().getVersion(),
                    version
                ));
            }
        });

        Configuration configDefaults = getConfig().getDefaults();
        if (configDefaults == null) { return; }
        int newConfigVersion = configDefaults.getInt("config-version");
        int configVersion = getConfig().getInt("config-version");
        if (newConfigVersion > configVersion) {
            getLogger().warning(
                """
                \nThe configuration file is outdated! This means new settings have been added that you are currently unable to access.
                To access these new settings; please delete the config.yml file and restart the server.
                """
            );
        }
    }

    private ShapedRecipe createShapedRecipe(ItemStack result, String key, String... recipeShape) {
        NamespacedKey recipeKey = new NamespacedKey(this, key);
        ShapedRecipe newShapedRecipe = new ShapedRecipe(recipeKey, result);
        newShapedRecipe.shape(recipeShape);
        return newShapedRecipe;
    }

    private HashMap<NamespacedKey, ShapedRecipe> createRecipes() {
        HashMap<NamespacedKey, ShapedRecipe> newRecipeMap = new HashMap<>();

        if (getConfig().getBoolean("recipes.craftable-saddle")) {
            ShapedRecipe saddleRecipe = this.createShapedRecipe(new ItemStack(Material.SADDLE, 1), "saddle", "   ", " L ", "L L");
            saddleRecipe.setIngredient('L', Material.LEATHER);
            newRecipeMap.put(saddleRecipe.getKey(), saddleRecipe);
        }

        if (getConfig().getBoolean("recipes.craftable-name-tag")) {
            ShapedRecipe nameTagRecipe = this.createShapedRecipe(new ItemStack(Material.NAME_TAG, 1), "name_tag", "   ", " S ", "  P");
            nameTagRecipe.setIngredient('S', Material.STRING);
            nameTagRecipe.setIngredient('P', Material.PAPER);
            newRecipeMap.put(nameTagRecipe.getKey(), nameTagRecipe);
        }

        if (getConfig().getBoolean("recipes.paper-tnt")) {
            ShapedRecipe TNTRecipe = this.createShapedRecipe(new ItemStack(Material.TNT, 1), "paper_tnt", "PSP", "SGS", "PSP");
            TNTRecipe.setIngredient('P', Material.PAPER);
            TNTRecipe.setIngredient('S', Material.SAND);
            TNTRecipe.setIngredient('G', Material.GUNPOWDER);
            newRecipeMap.put(TNTRecipe.getKey(), TNTRecipe);
        }

        if (getConfig().getBoolean("recipes.craftable-spore-blossom")) {
            ShapedRecipe sporeBlossomRecipe = this.createShapedRecipe(new ItemStack(Material.SPORE_BLOSSOM, 1), "spore_blossom", "  M", " L ", "   ");
            sporeBlossomRecipe.setIngredient('M', Material.MOSS_BLOCK);
            sporeBlossomRecipe.setIngredient('L', Material.LILAC);
            newRecipeMap.put(sporeBlossomRecipe.getKey(), sporeBlossomRecipe);
        }

        for (ShapedRecipe recipe : newRecipeMap.values()) {
            Bukkit.addRecipe(recipe);
        }
        return newRecipeMap;
    }

    public void playerDiscoverRecipes(Player player) {
        for (ShapedRecipe recipe : this.recipeMap.values()) {
            player.discoverRecipe(recipe.getKey());
        }
    }

    public static class MainCommandExecutor implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length < 1) { return false; }

            switch (args[0].toLowerCase()) {
                case "randomizepairs" -> RandomizePairs.onCommand(sender, args);
                case "pair" -> Pair.onCommand(sender, args);
                case "unpair" -> Unpair.onCommand(sender, args);
                case "lives" -> Lives.onCommand(sender, args);
                case "distributeplayers" -> DistributePlayers.onCommand();
                case "help" -> Help.onCommand(sender);
                case "reload" -> Reload.onCommand(sender);
                case "config" -> Config.onCommand(sender, args);
                default -> { return false; }
            };

            return true;
        }

    }

    public static class MainTabCompleter implements TabCompleter {

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            if (!command.getName().equalsIgnoreCase("doublelife") || !sender.hasPermission("doublelife.admin")) { return List.of(); }

            if (args.length == 1) {
                return CommandHelper.getAdministrativeCommands();
            }

            return switch (args[0].toLowerCase()) {
                case "pair" -> Pair.getAppropriateArguments(args);
                case "unpair" -> Unpair.getAppropriateArguments(args);
                case "lives" -> Lives.getAppropriateArguments(args);
                case "config" -> Config.getAppropriateArguments(args);
                default -> List.of();
            };
        }

    }

}

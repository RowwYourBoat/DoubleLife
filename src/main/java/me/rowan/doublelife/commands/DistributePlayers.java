package me.rowan.doublelife.commands;

import com.google.common.collect.ImmutableList;
import me.rowan.doublelife.DoubleLife;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class DistributePlayers {

    private final static HashMap<Player, Location> spawnLocationMap = new HashMap<>();

    private static Location getRandomLocation(World world){
        WorldBorder worldBorder = world.getWorldBorder();
        double borderSize = worldBorder.getSize();
        Location borderCenter = worldBorder.getCenter();
        Random rand = new Random();
        int randomX = (rand.nextInt(((int) borderSize/2) + ((int) borderSize/2)) - ((int) borderSize/2)) + (int) borderCenter.getX();
        int randomZ = (rand.nextInt(((int) borderSize/2) + ((int) borderSize/2)) - ((int) borderSize/2)) + (int) borderCenter.getZ();
        int highestYPoint = world.getHighestBlockYAt(randomX, randomZ);
        return new Location(world, randomX, highestYPoint, randomZ);
    }

    public static void onCommand() {
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "You are being teleported to a random location.");

        List<Player> players = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (Player participant : players) {
            scheduler.runTaskAsynchronously(DoubleLife.instance, () -> DistributePlayers.findLocationAsynchronously(participant, players.size(), scheduler));
        }
    }

    private static void findLocationAsynchronously(Player player, int totalPlayersNum, BukkitScheduler scheduler) {
        Location teleportLocation = null;
        for (int i = 0; i < 5; i++) {
            Location possibleLocation = getRandomLocation(player.getWorld());
            if (possibleLocation.getBlock().getType() == Material.WATER) { continue; }
            teleportLocation = possibleLocation;
            break;
        }

        if (teleportLocation != null) {
            teleportLocation.setY(teleportLocation.getY() + 1);
        }
        spawnLocationMap.put(player, teleportLocation);

        if (spawnLocationMap.size() == totalPlayersNum) {
            scheduler.runTask(DoubleLife.instance, DistributePlayers::teleportPlayersToLocations);
        }
    }

    private static void teleportPlayersToLocations() {
        boolean changeRespawnLocation = DoubleLife.instance.getConfig().getBoolean("misc.set-spawn-point-at-distributed-location");

        for (Player player : spawnLocationMap.keySet()) {
            Location spawnLocation = spawnLocationMap.get(player);
            if (spawnLocation != null) {
                player.teleport(spawnLocation);
            }

            if (changeRespawnLocation) {
                player.setRespawnLocation(spawnLocation, true);
            }

            player.setSaturation(20);
            player.setExhaustion(0);
            player.setHealth(20);
            player.setFoodLevel(20);
        }
    }

}

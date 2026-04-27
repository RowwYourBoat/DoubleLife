package me.rowan.doublelife.scoreboard;

import me.rowan.doublelife.DoubleLife;
import me.rowan.doublelife.data.SaveManager;
import org.bukkit.*;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Date;
import java.util.UUID;

public class TeamHandler implements Listener {

    private final static int numberOfTeams = 5;
    private final static Team[] teamArray = new Team[numberOfTeams];
    private final static ChatColor[] teamColorsArray = { ChatColor.GRAY, ChatColor.RED, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN };

    public static Scoreboard scoreboard;

    public TeamHandler() {
        if (Bukkit.getScoreboardManager() != null) {
            scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DoubleLife] Please restart the server for the plugin to create the required teams properly!");
            return;
        }

        if (!scoreboard.getTeams().isEmpty()) {
            for (Team team : scoreboard.getTeams()) {
                team.unregister();
            }
        }

        for (int i = 0; i < teamArray.length; i++) {
            Team newTeam = scoreboard.registerNewTeam(String.valueOf(i));
            newTeam.setCanSeeFriendlyInvisibles(false);
            newTeam.setColor(teamColorsArray[i]);
            teamArray[i] = newTeam;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            String pair = SaveManager.getPair(player);
            if (pair == null) { return; }
            changePlayerTeamAccordingly(pair, SaveManager.getPairLivesAmount(player));
        }
    }

    public static void modifyTeamAndGameMode(Player player, Integer livesAmount) {
        if (player == null) { return; }

        if (livesAmount == -1) {
            // If the player's pair has been removed
            player.setDisplayName(null);
            for (Team team : teamArray) {
                team.removeEntry(player.getName());
            }
            return;
        }

        int colourIndex = Math.clamp(livesAmount, 0, 4);
        String teamName = String.valueOf(colourIndex);
        Team newTeam = scoreboard.getTeam(teamName);
        if (newTeam == null) { return; }

        player.setDisplayName(teamColorsArray[colourIndex] + player.getName());
        newTeam.addEntry(player.getName());

        boolean shouldBanEliminated = DoubleLife.instance.getConfig().getBoolean("misc.ban-players-upon-losing");
        if (livesAmount == 0) {
            player.setGameMode(GameMode.SPECTATOR);
            if (shouldBanEliminated) {
                ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
                profileBanList.addBan(player.getPlayerProfile(), ChatColor.RED + "You're eliminated!", (Date) null, "DoubleLife");
                player.kickPlayer(ChatColor.RED + "You've been banned because you're out of lives!");
            }
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);
    }

    public static void changePlayerTeamAccordingly(String pair, Integer newLivesAmount) {
        String[] splitMessage = pair.split(" : ");
        Player playerOne = Bukkit.getPlayer(UUID.fromString(splitMessage[0]));
        Player playerTwo = Bukkit.getPlayer(UUID.fromString(splitMessage[1]));
        modifyTeamAndGameMode(playerOne, newLivesAmount);
        modifyTeamAndGameMode(playerTwo, newLivesAmount);
    }

    public static ChatColor livesNumberToColor(int livesNum) {
        int colourIndex = Math.clamp(livesNum, 0, 4);
        return teamColorsArray[colourIndex];
    }

    @EventHandler
    public void updatePlayerTeamOnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int amountOfLives = SaveManager.getPairLivesAmount(player);
        if (amountOfLives == -1) { return; }
        modifyTeamAndGameMode(player, amountOfLives);
    }

}

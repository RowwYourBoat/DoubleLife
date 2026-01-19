package me.rowwyourboat.doublelife_v2;

import org.bukkit.entity.Player;

public class Helpers {

    public static Participant getParticipant(Player player) {
        return new Participant(player);
    }

    public static void logError(String message) {
        DoubleLife.instance.getLogger().severe(message);
    }

}

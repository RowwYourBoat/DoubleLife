package me.rowwyourboat.doublelife_v2;

import org.bukkit.entity.Player;

public class Participant {
    private final Player instance;

    public Participant(Player player) {
        this.instance = player;
    }

    public Integer getLifeCount() {
        return 0;
    }

}

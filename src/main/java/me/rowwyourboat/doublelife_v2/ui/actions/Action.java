package me.rowwyourboat.doublelife_v2.ui.actions;

import org.bukkit.entity.Player;

public abstract class Action {

    protected final Player player;

    protected Action(Player player) {
        this.player = player;
    }

    protected abstract void perform();

}

package me.rowwyourboat.doublelife_v2.ui.actions;

import org.bukkit.entity.Player;

public class NavigateAction extends Action {

    private final String destination;

    public NavigateAction(Player player, String destination) {
        super(player);
        this.destination = destination;
    }

    public void perform() {

    }

}
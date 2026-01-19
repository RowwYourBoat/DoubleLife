package me.rowwyourboat.doublelife_v2.records.ui;

import org.bukkit.inventory.ItemStack;

public record RItem(
        Integer position,
        String field,
        ItemStack item,
        RAction action
) { }

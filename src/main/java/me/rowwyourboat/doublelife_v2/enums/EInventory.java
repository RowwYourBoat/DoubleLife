package me.rowwyourboat.doublelife_v2.enums;

public enum EInventory {
    MAIN_MENU("main_menu"),
    CONFIGURATION("configuration"),
    ITEMS_CONFIG("items_config");

    private final String id;

    EInventory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

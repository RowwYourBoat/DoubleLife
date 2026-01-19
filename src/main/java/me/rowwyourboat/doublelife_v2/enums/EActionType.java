package me.rowwyourboat.doublelife_v2.enums;

public enum EActionType {
    NAVIGATE("NAVIGATE"),
    CONFIG_TOGGLE("CONFIG_TOGGLE"),
    CONFIG_CHAT("CONFIG_CHAT"),
    CONFIG_CHAT_LIST("CONFIG_CHAT_LIST");

    private final String id;

    EActionType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

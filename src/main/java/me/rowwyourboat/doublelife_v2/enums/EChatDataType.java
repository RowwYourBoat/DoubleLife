package me.rowwyourboat.doublelife_v2.enums;

public enum EChatDataType {
    INT("INT"),
    STRING_LIST("STRING_LIST");

    private final String id;

    EChatDataType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

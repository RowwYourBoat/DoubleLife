package me.rowwyourboat.doublelife_v2.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.rowwyourboat.doublelife_v2.DoubleLife;
import me.rowwyourboat.doublelife_v2.Helpers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InventoryParser {

    private final String interfaceFolderPath;

    public InventoryParser() {
        try {
            this.interfaceFolderPath = DoubleLife.instance.getDataFolder().getCanonicalPath() + File.separator + "ui";
        } catch (IOException e) {
            throw new RuntimeException("Failed to locate ui folder: " + e.getMessage());
        }
    }

    public void parseInventories() {
        JsonArray inventories = this.getJson(File.separator + "inventories.json", JsonArray.class);
        if (inventories == null) return;

        for (JsonElement element : inventories) {
            if (!element.isJsonObject()) return;
            JsonObject inventory = (JsonObject) element;

            Helpers.logError(inventory.toString());
        }
    }

    private void parseContent() {

    }

    private void parseAction() {

    }

    private <T extends JsonElement> T getJson(String relativePath, Class<T> clazz) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(interfaceFolderPath + relativePath);
        } catch (IOException e) {
            Helpers.logError("Failed to read JSON file: " + e.getMessage());
        }
        if (fileReader == null) return null;

        JsonElement parsed = JsonParser.parseReader(fileReader);
        return clazz.isInstance(parsed) ? clazz.cast(parsed) : null;
    }

}

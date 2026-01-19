package me.rowan.doublelife;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class UpdateChecker {

    private final JavaPlugin plugin;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://api.modrinth.com/v2/project/double-life/version")).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));
                consumer.accept(this.getLastVersionFromResponse(response.body()));
            } catch (IOException | UncheckedIOException | InterruptedException e) {
                plugin.getLogger().info("Unable to check for updates: " + e.getMessage());
            }
        });
    }

    private String getLastVersionFromResponse(String response) {
        JsonArray array = new Gson().fromJson(response, JsonArray.class);
        return array.get(0).getAsJsonObject().get("version_number").getAsString();
    }

}

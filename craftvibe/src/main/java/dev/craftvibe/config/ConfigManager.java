package dev.craftvibe.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigManager {
    public static Config CONFIG = new Config();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        try {
            File f = new File("config/craftvibe.json");
            if (!f.exists()) {
                save();
                return;
            }
            try (FileReader r = new FileReader(f)) {
                CONFIG = GSON.fromJson(r, Config.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            File f = new File("config/craftvibe.json");
            f.getParentFile().mkdirs();
            try (FileWriter w = new FileWriter(f)) {
                GSON.toJson(CONFIG, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

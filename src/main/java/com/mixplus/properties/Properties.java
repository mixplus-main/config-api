package com.mixplus.properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Properties {
    private final Map<String, String> data = new HashMap<>();
    private final String path;


    public Properties(String path) {
        this.path = path;


    }

    public void load() throws IOException{
        List<String> lines = Files.readAllLines(Path.of(this.path));

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) continue;
            if (line.startsWith("#")) continue;

            String[] split = line.split("=", 2);
            if (split.length != 2) continue;

            String key = split[0].trim();
            String value = split[1].trim();

            data.put(key, value);
        }

    }

    public String getString(String key, String defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return Integer.parseInt(data.getOrDefault(key, String.valueOf(defaultValue)));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(data.getOrDefault(key, String.valueOf(defaultValue)));
    }

    public void set(String key, Object value) {
        data.put(key, String.valueOf(value));
    }

    public void save() throws IOException{
        List<String> lines = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            lines.add(entry.getKey() + "=" + entry.getValue());
        }
        Files.write(Path.of(path), lines);
    }
}
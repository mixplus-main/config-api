package com.mixplus.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.ToNumberPolicy;

import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Json {
    private final  String path;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();
    private Map<String, Object> data = new HashMap<>();
    private static final Logger logger = Logger.getLogger(Json.class.getName());

    public Json(String path) {
        this.path = path;
    }

    public void load() {
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            data = gson.fromJson(reader, type);

            if (data == null) {
                data = new HashMap<>();
            }
        } catch (IOException e) {
            logger.severe("Failed to load json: " + e.getMessage());
        }
    }

    public String getString(String key, String defaultValue) {
        Object value = data.get(key);

        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    public int getInt(String key, int defaultValue) {
        Object value = data.get(key);

        if (value == null) {
            return defaultValue;
        }
        return ((Number) value).intValue();
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Object value = data.get(key);

        if (value == null) {
            return defaultValue;
        }
        return (Boolean) value;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        Object value = data.get(key);

        if (value == null) {
            return new HashMap<>();
        }

        if (!(value instanceof Map<?, ?> map)) {
            throw new IllegalArgumentException(
                    "Key '" + key + "' is not a Map."
            );
        }

        return (Map<String, Object>) map;
    }


    public void set(String path, Object value) {
        String[] keys = path.split("\\.");

        Map<String, Object> current = data;

        for (int i = 0; i < keys.length - 1; i++) {
            current = getOrCreateMap(current, keys[i]);
        }

        current.put(keys[keys.length - 1], value);
    }

    public void save() {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save json", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getOrCreateMap(
            Map<String, Object> parent,
            String key
    ) {
        Object value = parent.get(key);

        if (value == null) {
            Map<String, Object> map = new HashMap<>();
            parent.put(key, map);
            return map;
        }

        if (!(value instanceof Map<?, ?>)) {
            throw new IllegalArgumentException(
                    "Path '" + key + "' is not a Map."
            );
        }

        return (Map<String, Object>) value;
    }
}

package com.mixplus.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public void save() {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save json", e);
        }
    }
}

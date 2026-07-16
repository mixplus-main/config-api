package com.mixplus;

import com.mixplus.json.Json;
import com.mixplus.properties.Properties;


import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Config {
    private static final Logger logger = Logger.getLogger(Config.class.getName());
    private final String path;
    private final FileType fileType;

    private Properties properties;
    private Json json;

    public static void main(String[] args) {}


    public Config(String path, FileType fileType) {
        this.path = path;
        this.fileType = fileType;

        switch (fileType) {
            case PROPERTIES -> properties = new Properties(this.path);

            case JSON -> json = new Json(this.path);

        }
    }


    public void load() throws IOException {
        switch (fileType) {
            case PROPERTIES -> properties.load();
            case JSON -> json.load();
        }
    }


    public String getString(String key, String defaultValue) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getString(key, defaultValue);
            }
            case JSON -> {
                return json.getString(key, defaultValue);
            }
            case INI -> {
                return "INI";
            }
        }
        return defaultValue;
    }


    public int getInt(String key, int defaultValue) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getInt(key, defaultValue);
            }
            case JSON -> {
                return json.getInt(key, defaultValue);
            }
        }
        return defaultValue;
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getBoolean(key, defaultValue);
            }
            case JSON -> {
                return json.getBoolean(key, defaultValue);
            }
        }
        return defaultValue;
    }

    public Map<String, Object> getMap(String key) {
        switch (fileType) {
            case PROPERTIES -> {
                return null;
            }
            case JSON -> {
                return json.getMap(key);
            }
        }
        return null;
    }


    public void set(String key, Object value) {
        switch (fileType) {
            case PROPERTIES -> properties.set(key, value);

            case JSON -> json.set(key, value);
        }
    }

    public void save() throws IOException {
        switch (fileType) {
            case PROPERTIES -> properties.save();

            case JSON -> json.save();
        }
    }

    public void createFile() {
        File file = new File(path);

        if (!file.exists()) {
            try {
                boolean cf = file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create file", e);
            }
        }
    }

    public String getPath() {return path; }
}
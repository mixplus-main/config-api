package com.mixplus;

import com.mixplus.properties.Properties;


import java.io.IOException;


public class Config {
    private final String path;
    private final FileType fileType;

    private Properties properties;

    public static void main(String[] args) {}

    public Config(String path, FileType fileType) {
        this.path = path;
        this.fileType = fileType;

        switch (fileType) {
            case PROPERTIES -> {
                properties = new Properties(this.path);
            }
            case JSON -> {}
        }
    }

    public void load() throws IOException {
        switch (fileType) {
            case PROPERTIES -> {
                properties.load();
            }
            case JSON -> {}
        }
    }

    public String getString(String key, String Default) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getString(key, Default);
            }
            case JSON -> {
                return "JSON";
            }
            case INI -> {
                return "INI";
            }
        }
        return Default;
    }

    public int getInt(String key, int Default) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getInt(key, Default);
            }
            case INI -> {}
        }
        return Default;
    }

    public boolean getBoolean(String key, boolean Default) {
        switch (fileType) {
            case PROPERTIES -> {
                return properties.getBoolean(key, Default);
            }
            case INI -> {}
        }
        return Default;
    }

    public void set(String key, Object value) {
        switch (fileType) {
            case PROPERTIES -> {
                properties.set(key, value);
            }
            case INI -> {}
        }
    }

    public void save() throws IOException {
        properties.save();
    }

    public String getPath() {return path; }
}
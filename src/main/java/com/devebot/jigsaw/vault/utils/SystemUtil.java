package com.devebot.jigsaw.vault.utils;

public class SystemUtil {
    
    public static String getEnvironVariable(String key) {
        return getEnvironVariable(key, null);
    }
    
    public static String getEnvironVariable(String key, String def) {
        if (key == null) return null;
        try {
            String value = System.getenv(key);
            if (value != null) return value;
            return def;
        } catch (Throwable t) {
            return def;
        }
    }
    
    public static String getSystemProperty(String key) {
        return getSystemProperty(key, null);
    }
    
    public static String getSystemProperty(String key, String def) {
        if (key == null) return null;
        try {
            return System.getProperty(key, def);
        } catch (Throwable t) {
            return def;
        }
    }
}

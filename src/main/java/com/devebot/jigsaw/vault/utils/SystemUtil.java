package com.devebot.jigsaw.vault.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
    private final static Logger LOG = LoggerFactory.getLogger(SystemUtil.class);

    public static void printStackTrace(Throwable t) {
        if (t != null) {
            t.printStackTrace();
        }
    }
    
    public static String getEnv(String sysProp, String envName) {
        return getEnv(sysProp, envName, null);
    }
    
    public static String getEnv(String sysProp, String envName, String def) {
        String value = getSystemProperty(sysProp);
        
        if (value == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("System property [{}] is undefined", sysProp);
            }
            value = getEnvironVariable(envName);
        }
        
        if (value == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Environment variable [{}] is undefined", envName);
            }
            return def;
        }
        
        return value;
    }
    
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

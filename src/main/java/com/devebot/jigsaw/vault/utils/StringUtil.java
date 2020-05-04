package com.devebot.jigsaw.vault.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

public class StringUtil {
    public static final String CHAR_ENCODING = "UTF-8";
    public static final String LINE_BREAK = "\n";
    
    public static String newString(byte[] byteArray) {
        try {
            return new String(byteArray, CHAR_ENCODING);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public static String join(String[] datalines) {
        return String.join("", Arrays.asList(datalines));
    }
    
    public static String format(String template, Object ... params) {
        Object[] args = new Object[params.length];
        for (int i=0; i<params.length; i++) {
            args[i] = params[i];
        }
        return MessageFormat.format(template, args);
    }
    
    public static String maskPassword(String password) {
        if (password == null) return null;
        char[] charArray = new char[password.length()];
        Arrays.fill(charArray, '*');
        return new String(charArray);
    }
    
    public static String flattenVault(String vaultBlock) {
        if (vaultBlock == null) {
            return vaultBlock;
        }
        return vaultBlock.replace('\n', '|');
    }
    
    public static String deflattenVault(String vaultBlock) {
        if (vaultBlock == null) {
            return vaultBlock;
        }
        return vaultBlock.replace('|', '\n');
    }
}

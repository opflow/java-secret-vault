package com.devebot.jigsaw.vault.utils;

import java.io.IOException;
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
}

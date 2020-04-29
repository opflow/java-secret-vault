package com.devebot.jigsaw.vault.utils;

public class HexadecimalUtil {
    public static final int DEFAULT_LINE_LENGTH = 80;
    
    public static String encode(byte [] data) {
        return encode(data, DEFAULT_LINE_LENGTH);
    }
    
    public static String encode(byte [] data, int lineLength) {
        String result = "";
        int colIdx = 0;
        for (byte val: data) {
            result += String.format("%02x", val);
            colIdx++;
            if (lineLength > 0 && colIdx >= lineLength/2) {
                result += StringUtil.LINE_BREAK;
                colIdx=0;
            }
        }
        return result;
    }
    
    public static byte[] decode(String data) {
        int dataLen = data.length();
        byte[] output = new byte[dataLen/2];
        for (int charIdx = 0; charIdx < dataLen; charIdx += 2) {
            output[charIdx/2] = (byte) ((Character.digit(data.charAt(charIdx), 16) << 4) +
                Character.digit(data.charAt(charIdx+1), 16));
        }
        return output;
    }
}

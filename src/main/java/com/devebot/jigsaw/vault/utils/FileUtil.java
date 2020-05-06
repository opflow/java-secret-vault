package com.devebot.jigsaw.vault.utils;

import com.devebot.jigsaw.vault.exceptions.FileLoadingException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author acegik
 */
public class FileUtil {
    public static byte[] readAll(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] dataInBytes = new byte[fis.available()];
            fis.read(dataInBytes);
            fis.close();
            return dataInBytes;
        } catch (IOException ex) {
            SystemUtil.printStackTrace(ex);
            throw new FileLoadingException("Failed to open the file", ex);
        }
    }
}

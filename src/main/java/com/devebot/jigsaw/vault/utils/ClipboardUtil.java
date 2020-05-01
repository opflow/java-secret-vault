package com.devebot.jigsaw.vault.utils;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {
    public static boolean copy(String text) {
        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            return true;
        } catch (HeadlessException e) {
            SystemUtil.printStackTrace(e);
            return false;
        }
    }
}

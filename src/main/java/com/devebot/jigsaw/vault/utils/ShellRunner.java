package com.devebot.jigsaw.vault.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author drupalex
 */
public class ShellRunner {

    public static String executeCommand(String command) {
        List<String> output = new LinkedList<>();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException | InterruptedException e) {
            SystemUtil.printStackTrace(e);
        }
        return String.join("\n", output);
    }
}

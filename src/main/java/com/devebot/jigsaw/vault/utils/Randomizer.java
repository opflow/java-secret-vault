package com.devebot.jigsaw.vault.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Randomizer {
    private Random r = null;

    public Randomizer() {
        try {
            r = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            r = new Random();
        }
    }
    
    public byte[] nextBytes(byte[] buffer) {
        if (buffer != null) {
            r.nextBytes(buffer);
        }
        return buffer;
    }
}

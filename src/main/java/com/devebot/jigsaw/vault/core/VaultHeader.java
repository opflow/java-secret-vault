package com.devebot.jigsaw.vault.core;

public class VaultHeader {
    public final static String HEADER_SEPARATOR = ";";
    public final static int ID_INDEX = 0;
    public final static int VERSION_INDEX = 1;
    public final static int ALGO_INDEX = 2;

    public final static String VAULT_FORMAT_ID = "$ANSIBLE_VAULT";
    public final static String VAULT_FORMAT_VERSION_1_1 = "1.1";
    public final static String VAULT_FORMAT_VERSION_1_2 = "1.2";
    
    private boolean validSeal = false;
    private String algorithm;
    private String version;
    
    public VaultHeader(String vaultHeader) {
        try {
            String [] headers = vaultHeader.split(HEADER_SEPARATOR);
            if (headers.length >= 3) {
                if ( headers[ID_INDEX].equals(VAULT_FORMAT_ID) ) {
                    validSeal = true;
                    version = headers[VERSION_INDEX];
                    algorithm = headers[ALGO_INDEX];
                }
            }
        } catch (RuntimeException e) {
            // do nothing
        }
    }
    
    public boolean isValid() {
        return validSeal && (VAULT_FORMAT_VERSION_1_1.equals(version) || VAULT_FORMAT_VERSION_1_2.equals(version));
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public String getVersion() {
        return version;
    }
    
    private static final String VAULT_PREFIX = VaultHeader.VAULT_FORMAT_ID + HEADER_SEPARATOR;
    
    public static boolean isVaultBlock(String vault) {
        return vault != null && vault.length() > VAULT_PREFIX.length() && vault.startsWith(VAULT_PREFIX);
    }
}

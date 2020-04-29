package com.devebot.jigsaw.vault.crypto.impl;

import com.devebot.jigsaw.vault.core.VaultPayload;
import com.devebot.jigsaw.vault.crypto.CipherInterface;
import com.devebot.jigsaw.vault.exceptions.DecryptingException;
import com.devebot.jigsaw.vault.exceptions.EncryptingException;
import com.devebot.jigsaw.vault.exceptions.PaddingException;
import com.devebot.jigsaw.vault.exceptions.RestrictedCipherException;
import com.devebot.jigsaw.vault.utils.HexadecimalUtil;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class CipherImplAES256 implements CipherInterface {
    private final static Logger LOG = LoggerFactory.getLogger(CipherImplAES256.class);

    public final static int AES_KEYLEN = 256;
    public final static String KEYGEN_ALGO = "HmacSHA256";
    public final static String CIPHER_MAIN_ALGO = "AES";
    public static final String CIPHER_ALGO = "AES/CTR/NoPadding";

    public final static int SALT_LENGTH = 32;
    public final static int KEYLEN = 32;
    public final static int IVLEN = 16;
    public final static int ITERATIONS = 10000;

    public CipherImplAES256() {
        checkValidCipherAlgorithm();
    }

    @Override
    public byte[] decrypt(VaultPayload vaultBody, String password) {
        byte[] salt = vaultBody.getSalt();
        byte[] hmac = vaultBody.getHmac();
        byte[] data = vaultBody.getData();

        KeyGenerator keys = new KeyGenerator(KEYGEN_ALGO, password, salt, KEYLEN, IVLEN, ITERATIONS);
        keys.generateKeys();

        if (verifyHMAC(hmac, keys.getHmacKey(), data)) {
            return decryptAES(data, keys.getCipherKey(), keys.getIv());
        } else {
            throw new DecryptingException("HMAC Digest doesn't match with wrong password.");
        }
    }

    @Override
    public VaultPayload encrypt(byte[] data, String password) {
        KeyGenerator keys = new KeyGenerator(KEYGEN_ALGO, password, SALT_LENGTH, KEYLEN, IVLEN, ITERATIONS);
        keys.generateKeys();

        byte[] encrypted = encryptAES(pad(data), keys.getCipherKey(), keys.getIv());
        byte[] hmacHash = calculateHMAC(keys.getHmacKey(), encrypted);
        return new VaultPayload(keys.getSalt(), hmacHash, encrypted);
    }
    
    public byte[] decryptAES(byte[] cypher, byte[] key, byte[] iv) {
        SecretKeySpec keySpec = new SecretKeySpec(key, CIPHER_MAIN_ALGO);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return unpad(cipher.doFinal(cypher));
        } catch (Exception ex) {
            throw new DecryptingException("Failed to decrypt data", ex);
        }
    }

    public byte[] encryptAES(byte[] cleartext, byte[] key, byte[] iv) {
        SecretKeySpec keySpec = new SecretKeySpec(key, CIPHER_MAIN_ALGO);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(cleartext);
        } catch (Exception ex) {
            throw new EncryptingException("Failed to encrypt data", ex);
        }
    }
    
    public byte[] calculateHMAC(byte[] key, byte[] data) {
        try {
            SecretKeySpec hmacKey = new SecretKeySpec(key, KEYGEN_ALGO);
            Mac mac = Mac.getInstance(KEYGEN_ALGO);
            mac.init(hmacKey);
            return mac.doFinal(data);
        } catch (Exception ex) {
            throw new DecryptingException("Error decrypting HMAC hash", ex);
        }
    }

    public boolean verifyHMAC(byte[] hmac, byte[] key, byte[] data) {
        return Arrays.equals(hmac, calculateHMAC(key, data));
    }

    public int paddingLength(byte[] decrypted) {
        if (decrypted.length == 0) {
            return 0;
        }
        return decrypted[decrypted.length - 1];
    }

    public byte[] pad(byte[] cleartext) {
        byte[] padded = null;

        try {
            int blockSize = Cipher.getInstance(CIPHER_ALGO).getBlockSize();
            int padding_length = (blockSize - (cleartext.length % blockSize));
            if (padding_length == 0) {
                padding_length = blockSize;
            }
            padded = Arrays.copyOf(cleartext, cleartext.length + padding_length);
            padded[padded.length - 1] = (byte) padding_length;
        } catch (Exception ex) {
            throw new PaddingException("Error calculating padding for " + CIPHER_ALGO + ": " + ex.getMessage());
        }

        return padded;
    }
    
    public byte[] unpad(byte[] decrypted) {
        int length = decrypted.length - paddingLength(decrypted);
        return Arrays.copyOfRange(decrypted, 0, length);
    }
    
    private static final String JDK8_UPF_URL = "http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html";
    
    private boolean checkValidCipherAlgorithm() {
        try {
            int maxKeyLen = Cipher.getMaxAllowedKeyLength(CIPHER_ALGO);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Available keylen: {}", maxKeyLen);
            }
            if (maxKeyLen < AES_KEYLEN) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("JRE doesn't support {} keylength for {}\nInstall unrestricted policy profiles from:\n{}",
                            AES_KEYLEN, CIPHER_MAIN_ALGO, JDK8_UPF_URL);
                }
                throw new RestrictedCipherException("Missing valid AES256 provider, install unrestricted policy profiles.");
            }
        } catch (NoSuchAlgorithmException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to check for proper Cipher Algorithms: {}", ex.getMessage());
                ex.printStackTrace();
            }
            throw new RestrictedCipherException(ex);
        }
        return true;
    }
}

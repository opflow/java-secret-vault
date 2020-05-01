package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.utils.TestingUtil;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultCryptorTest {
    private final static Logger LOG = LoggerFactory.getLogger(VaultCryptorTest.class);
    
    @Test
    public void test_decryptVault_valid() throws IOException {
        TestingUtil.setEnv("JAVA_SECRET_VAULT_PASSWORD", "qwerty");
        
        String vault = "$ANSIBLE_VAULT;1.1;AES256\n" +
                "39376266396133623035633932363162363331386134366332636238356536373430313536613936\n" +
                "3137306465373661653731626536346639366331623662300a393032633933363834643139636638\n" +
                "38303230653333393361386463323033373865316433383565353036313530313231306138323236\n" +
                "3665333031633732320a623930363062356131323866616665643531353537623966336663343562\n" +
                "3132";
        
        VaultCryptor cryptor  = new VaultCryptor();
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Decrypted: " + cryptor.decryptVault(vault));
        }
        Assert.assertEquals("letmein", cryptor.decryptVault(vault));
    }
    
    @Test
    public void test_encryptVault_valid() throws IOException {
        TestingUtil.setEnv("JAVA_SECRET_VAULT_PASSWORD", "qwerty");
        
        VaultCryptor cryptor  = new VaultCryptor();
        String vault = cryptor.encryptVault("letmein");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Encrypted: " + vault);
        }
        Assert.assertEquals("letmein", cryptor.decryptVault(vault));
    }
}

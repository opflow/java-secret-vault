package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.utils.TestingUtil;
import java.io.IOException;
import org.junit.Test;

public class VaultHandlerTest {
    
    @Test
    public void testValidVault() throws IOException {
        String vault = "$ANSIBLE_VAULT;1.1;AES256\n" +
                "39376266396133623035633932363162363331386134366332636238356536373430313536613936\n" +
                "3137306465373661653731626536346639366331623662300a393032633933363834643139636638\n" +
                "38303230653333393361386463323033373865316433383565353036313530313231306138323236\n" +
                "3665333031633732320a623930363062356131323866616665643531353537623966336663343562\n" +
                "3132";

        TestingUtil.setEnv("JAVA_ANSIBLE_VAULT_PASSWORD", "qwerty");
        
        VaultHandler cryptor  = new VaultHandler();
        
        System.out.println("Output: " + cryptor.decryptVault(vault));
    }
}

package com.devebot.jigsaw.vault.utils;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtilTest {
    private final static Logger LOG = LoggerFactory.getLogger(StringUtilTest.class);
    
    @Test
    public void test_flatten_deflatten() throws Exception {
        String vaultBlock = "$ANSIBLE_VAULT;1.1;AES256\n" +
                "39376266396133623035633932363162363331386134366332636238356536373430313536613936\n" +
                "3137306465373661653731626536346639366331623662300a393032633933363834643139636638\n" +
                "38303230653333393361386463323033373865316433383565353036313530313231306138323236\n" +
                "3665333031633732320a623930363062356131323866616665643531353537623966336663343562\n" +
                "3132";
        
        String expected = "$ANSIBLE_VAULT;1.1;AES256|" +
                "39376266396133623035633932363162363331386134366332636238356536373430313536613936|" +
                "3137306465373661653731626536346639366331623662300a393032633933363834643139636638|" +
                "38303230653333393361386463323033373865316433383565353036313530313231306138323236|" +
                "3665333031633732320a623930363062356131323866616665643531353537623966336663343562|" +
                "3132";
        
        Assert.assertEquals(expected, StringUtil.flattenVault(vaultBlock));
        Assert.assertEquals(vaultBlock, StringUtil.deflattenVault(expected));
    }
}

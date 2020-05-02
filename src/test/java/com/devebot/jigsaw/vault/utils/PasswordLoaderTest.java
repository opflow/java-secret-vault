package com.devebot.jigsaw.vault.utils;

import org.junit.Assert;
import org.junit.Test;

public class PasswordLoaderTest {
    PasswordLoader loader;
    
    @Test
    public void test_validatePassword() {
        TestingUtil.setEnv("JAVA_SECRET_VAULT_PASSWORD_LEVEL", "strong");
        
        loader = new PasswordLoader();
        
        Assert.assertTrue(loader.validatePassword("Hello!123"));
        Assert.assertTrue(loader.validatePassword("Hell0#123"));
        Assert.assertTrue(loader.validatePassword("Hello@123"));
        
        Assert.assertFalse(loader.validatePassword(null));
        Assert.assertFalse(loader.validatePassword("hello@123"));
        Assert.assertFalse(loader.validatePassword("Hell @123"));
    }
}
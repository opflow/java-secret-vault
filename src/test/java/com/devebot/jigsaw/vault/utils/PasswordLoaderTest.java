package com.devebot.jigsaw.vault.utils;

import org.junit.Assert;
import org.junit.Test;

public class PasswordLoaderTest {
    PasswordLoader loader = new PasswordLoader();
    
    @Test
    public void test_validatePassword() {
        Assert.assertTrue(loader.validatePassword("Hello!123"));
        Assert.assertTrue(loader.validatePassword("Hell0#123"));
        Assert.assertTrue(loader.validatePassword("Hello@123"));
        
        Assert.assertFalse(loader.validatePassword(null));
        Assert.assertFalse(loader.validatePassword("hello@123"));
        Assert.assertFalse(loader.validatePassword("Hell @123"));
    }
}
package com.devebot.jigsaw.vault.utils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PasswordLoaderTest {
    PasswordLoader loader;
    
    public static void resetEnv() {
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_LEVEL_ENV_NAME, "");
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_FILE_ENV_NAME, "");
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_SCRIPT_ENV_NAME, "");
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_TEXT_ENV_NAME, "");
    }
    
    @Before
    public void beforeEach() {
        resetEnv();
    }
    
    @AfterClass
    public static void afterEach() {
        resetEnv();
    }
    
    @Test
    public void test_validatePassword() {
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_LEVEL_ENV_NAME, "strong");
        
        loader = new PasswordLoader();
        
        Assert.assertTrue(loader.validatePassword("Hello!123"));
        Assert.assertTrue(loader.validatePassword("Hell0#123"));
        Assert.assertTrue(loader.validatePassword("Hello@123"));
        
        Assert.assertFalse(loader.validatePassword(null));
        Assert.assertFalse(loader.validatePassword("Hello1234"));
        Assert.assertFalse(loader.validatePassword("hello@123"));
        Assert.assertFalse(loader.validatePassword("Hell @123"));
    }
    
    @Test
    public void test_loadPasswordFromFile() {
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_FILE_ENV_NAME, "./src/test/resources/passwd.txt");
        loader = new PasswordLoader();
        Assert.assertEquals("p4ssw0rd", loader.loadPasswordFromFile());
    }
    
    @Test
    public void test_loadPasswordFromFile_notFound() {
        TestingUtil.setEnv(PasswordLoader.VAULT_PASSWORD_FILE_ENV_NAME, "./src/test/resources/notfound.txt");
        loader = new PasswordLoader();
        Assert.assertNull(loader.loadPasswordFromFile());
    }
}

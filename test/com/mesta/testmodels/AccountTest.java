/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.testmodels;

import com.mesta.models.Account;
import com.mesta.models.Token;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Demian
 */
public class AccountTest {
    
    private Account acc;
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        acc = new Account("123456",false,false, new Token());
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getExternalID(){
        String expected = "123456";
        Assert.assertEquals(expected, acc.getExternalID());
    }
    
    @Test
    public void setExternalID(){
        acc.setExternalID("654321");
        String expected = "654321";
        Assert.assertEquals(expected, acc.getExternalID());
    }
    
    @Test
    public void isBanned(){
        boolean expected = false;
        Assert.assertEquals(expected, acc.isBanned());
    }
    
    @Test
    public void setBanned(){
        acc.setBanned(true);
        boolean expected = true;
        Assert.assertEquals(expected, acc.isBanned());
    }
    
    @Test
    public void isAdmin(){
        boolean expected = false;
        Assert.assertEquals(expected, acc.isAdmin());
    }
    
    @Test
    public void setAdmin(){
        acc.setAdmin(true);
        boolean expected = true;
        Assert.assertEquals(expected, acc.isAdmin());
    }
    
    @Test
    public void toJson(){
        String expected = "{\"Banned\":false,\"faceBookID\":\"123456\",\"Admin\":false}";
        Assert.assertEquals(expected, acc.toString());
    }
}

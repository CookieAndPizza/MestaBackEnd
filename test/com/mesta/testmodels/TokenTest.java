/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.testmodels;

import com.mesta.models.Token;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harm
 */
public class TokenTest {
    
    private Token token;
    
    public TokenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.token = new Token();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetToken(){
        String newToken = "token";
        this.token.setToken(newToken);
        assertEquals(newToken, token.getToken());
    }
}

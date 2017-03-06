/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Random;

/**
 *
 * @author Demian
 */
public class Token {
    private String token;
    private Date expires;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
    
    public Token(){
        Random random = new SecureRandom();
        
    }
}

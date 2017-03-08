/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Demian
 */
public class Account {
    private int id;
    private String externalID;
    private boolean banned;
    private boolean admin;
    private Token token;

    public int getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public Account(String externalID, boolean banned, boolean admin, Token token){
        this.externalID = externalID;
        this.banned = banned;
        this.admin = admin;
        this.token = token;
    }
    
    public Account(int id, String externalID, boolean banned, boolean admin, Token token){
        this.id = id;
        this.externalID = externalID;
        this.banned = banned;
        this.admin = admin;
        this.token = token;
    }
    
    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("faceBookID", this.externalID);
            object.put("Banned", this.banned);
            object.put("Admin", this.admin);
            object.put("Token", this.token.getToken());
        } catch (JSONException ex) {
            System.out.println(ex);
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }

        return object.toString();
    }
    
}

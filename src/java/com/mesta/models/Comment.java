/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author harm
 */
public class Comment {

    private String faceBookID;
    private String comment;
    private int locationID;
    private String date;

    public Comment() {

    }

    public Comment(String comment, String date) {
        this.comment = comment;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setfaceBookID(String faceBookID) {
        this.faceBookID = faceBookID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getfaceBookID() {
        return faceBookID;
    }

    public String getComment() {
        return comment;
    }

    public String getLocationID() {
        return String.valueOf(locationID);
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try { 
            object.put("date", date);
            object.put("comment", comment);
            object.put("userID", faceBookID);
        } catch (JSONException ex) {
            System.out.println(ex);
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }

        return object.toString();
    }
    
    
}

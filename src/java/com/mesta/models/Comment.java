/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.models;

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

    public Comment(int locationID, String comment, String date) {
        this.locationID = locationID;
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
    
}

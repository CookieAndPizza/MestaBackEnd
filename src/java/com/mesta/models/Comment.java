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
public class Comment{
    
    private String userID;
    private String comment;
    private String title;
    private int locationID;

    public Comment(String userID, String comment, String title) {
        this.userID = userID;
        this.comment = comment;
        this.title = title;
    }

    public String getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public String getTitle() {
        return title;
    }
    
    public String getAccountID(){
        return String.valueOf(userID);
    }
    
    public String getComment(){
        return comment;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getLocationID(){
        return String.valueOf(locationID);
    }
    
    @Override
    public String toString(){
        return String.format("%s; %s; %s", userID, title, comment);
    }
}

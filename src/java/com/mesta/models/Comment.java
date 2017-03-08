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
    
    private String faceBookID;
    private String comment;
    private String title;
    private int locationID;

    public Comment(){
        
    }
    
    public Comment(String faceBookID, String comment, String title) {
        this.faceBookID = faceBookID;
        this.comment = comment;
        this.title = title;
    }

    public void setfaceBookID(String faceBookID){
        this.faceBookID = faceBookID;
    }
    
    public void setLocationID(int locationID){
        this.locationID = locationID;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
            
    public String getfaceBookID() {
        return faceBookID;
    }

    public String getComment() {
        return comment;
    }

    public String getTitle() {
        return title;
    }
    
    public String getLocationID(){
        return String.valueOf(locationID);
    }
    
    @Override
    public String toString(){
        return String.format("%s; %s; %s", faceBookID, title, comment);
    }
}

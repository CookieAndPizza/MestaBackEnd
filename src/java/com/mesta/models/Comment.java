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
    
    private int userID;
    private String comment;

    public Comment(int userID, String comment) {
        this.userID = userID;
        this.comment = comment;
    }
    
    @Override
    public String toString(){
        return String.format("%d; %s", userID, comment);
    }
}

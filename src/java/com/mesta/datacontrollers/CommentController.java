/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacontrollers;

import com.mesta.datacommunicators.CommentSetter;

/**
 *
 * @author harm
 */
public class CommentController {
    
    private static CommentController controller;
    
    private CommentSetter commentSetter;

    public static CommentController getController() {
        if (controller == null) {
            controller = new CommentController();
        }
        return controller;
    }
    
    private CommentController(){
        this.commentSetter = new CommentSetter();
    }


    public CommentSetter commentSetter(){
        return this.commentSetter;
    }
}

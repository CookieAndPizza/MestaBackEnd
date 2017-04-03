/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

/**
 *
 * @author harm
 */
public class DatabaseInfo {
    public static final String CONNECTION_STRING = "jdbc:mysql://188.166.88.118:3306/Mesta";
    public static final String LOGIN_NAME = "Mesta";
    public static final String PASSWORD = "@Glassfish1";
    
    /**
     * Responses the database can return
     */
    public enum DatabaseRepsonse{
        SUCCES,
        FAILED,
        TOKEN_NOT_VALID,
        USER_ALREADY_LIKED
    }
}

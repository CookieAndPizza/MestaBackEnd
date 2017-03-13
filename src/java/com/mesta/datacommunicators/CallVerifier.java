/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class CallVerifier {
    private static CallableStatement statement;
    
    private CallVerifier(){
        throw new IllegalAccessError("Utility class");
    }

    /**
     * method for getting all locations.
     *
     * @param login login to be verified
     * @param token token of the login
     * @param connection database connection
     * @return returns true or false
     * @throws java.sql.SQLException
     */
    public static boolean verify(String login, String token, Connection connection) throws SQLException {
        boolean succes = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "CALL checkAccount(?, ?, ?)";
            statement = connection.prepareCall(query);
            statement.setString(1, login);
            statement.setString(2, token);
            statement.registerOutParameter(3, Types.INTEGER);

            statement.executeQuery();

            int answer = statement.getInt(3);
            succes = answer != 0;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
}

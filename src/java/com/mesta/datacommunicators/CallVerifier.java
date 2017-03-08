/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.models.Account;
import com.mesta.models.Token;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class CallVerifier {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private static CallableStatement statement;

    /**
     * method for getting all locations.
     *
     * @param content representation for the resource
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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
}

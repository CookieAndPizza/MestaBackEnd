/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.models.Account;
import com.mesta.models.Comment;
import com.mesta.models.Location;
import com.mesta.models.Token;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class AccountGetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;
    private CallableStatement statement;

    /**
     * method for getting all locations.
     *
     * @param content representation for the resource
     * @throws java.sql.SQLException
     */
    public Account login(String login) throws SQLException {
        Account account = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            Token token = new Token();
            String query = "CALL select_or_insert_Account(?, ?)";
            statement = connection.prepareCall(query);
            statement.setString(1, login);
            statement.setString(2, token.getToken());

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String fbID = result.getString("ExternalID");
                boolean admin = result.getBoolean("Admin");
                boolean banned = result.getBoolean("Blocked");
                account = new Account(id, fbID, admin, banned, token);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return account;
    }
}

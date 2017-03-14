/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.models.Account;
import com.mesta.models.Token;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class AccountGetter {

    private Connection connection;
    private CallableStatement statement;

    /**
     * method for getting all locations.
     *
     * @param login representation for the resource
     * @return Account
     * @throws java.sql.SQLException
     */
    public Account login(String login, String inputtoken) throws SQLException {
        Account account = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            if (CallVerifier.facebookVerify(inputtoken)) {

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
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return account;
    }

    /**
     *
     * @param fbID Facebook ID of the logged in user
     * @param token token of the logged in user
     * @return response from database
     * @throws SQLException
     */
    public DatabaseInfo.DatabaseRepsonse logout(String fbID, String token) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            if (CallVerifier.verify(fbID, token, connection)) {
                String query = "DELETE FROM Tokens WHERE Token = ? AND AccountID = ?";
                statement = connection.prepareCall(query);
                statement.setString(1, token);
                statement.setString(2, fbID);

                int rows = statement.executeUpdate();

                if (rows > 0) {
                    return DatabaseInfo.DatabaseRepsonse.SUCCES;
                }
            } else {
                return DatabaseInfo.DatabaseRepsonse.TOKEN_NOT_VALID;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }
}

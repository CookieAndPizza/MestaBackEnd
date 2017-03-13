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

            checkFacebook("1");

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

    public DatabaseInfo.DatabaseRepsonse logout(String fbID, String token) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }

    private boolean checkFacebook(String inputtoken) {
        boolean succes = false;
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL("https://graph.facebook.com/debug_token?input_token=" + inputtoken + "&access_token=1648610375442724");
            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("GET");
            httpConnection.setUseCaches(false);
            httpConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.close();

            InputStream is = httpConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println(line);

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return succes;
    }
}

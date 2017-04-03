/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author harm
 */
public class CallVerifier {

    private static CallableStatement statement;

    private CallVerifier() {
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

    public static boolean facebookVerify(String inputtoken) {
        boolean succes = false;

        HttpURLConnection httpConnection = null;
        try {
            String accessToken = getAccessToken();
            URL url = new URL("https://graph.facebook.com/debug_token?input_token=" + inputtoken + "&access_token=" + accessToken);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoOutput(true);

            InputStream is = httpConnection.getInputStream();
            String line;
            StringBuilder response = new StringBuilder();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }
            JSONObject json = new JSONObject(response.toString());
            Boolean is_valid = json.getJSONObject("data").getBoolean("is_valid");

            if (is_valid) {
                succes = true;
            }

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(CallVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpConnection.disconnect();
        }
        return succes;
    }

    private static String getAccessToken() throws JSONException {
        String answer = "";
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL("https://graph.facebook.com/oauth/access_token?client_id=1648610375442724&client_secret=457b6d1b4d0d23b7d489277ca599dd7d&grant_type=client_credentials");

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoOutput(true);

            InputStream is = httpConnection.getInputStream();
            String line;
            StringBuilder response = new StringBuilder();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }
            JSONObject json = new JSONObject(response.toString());
            answer = json.getString("access_token");

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AccountGetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpConnection.disconnect();
        }
        return answer;
    }
}

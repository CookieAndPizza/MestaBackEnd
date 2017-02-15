/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.models.Location;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class LocationGetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;

    public Stack getAllLocations() throws SQLException {
        Stack locations = new Stack();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "SELECT ID, Name, Latitude, Longitude, Description FROM Location";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                long latitude = result.getLong("Latitude");
                long longitude = result.getLong("Longitude");
                String discription = result.getString("Description");
                Location loc = new Location(id, name, longitude, latitude, discription);

                locations.add(loc);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.FINE, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return locations;
    }
    
    public Stack getNearbyLocations() throws SQLException {
        Stack locations = new Stack();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                long latitude = result.getLong("Latitude");
                long longitude = result.getLong("Longitude");
                String discription = result.getString("Description");
                Location loc = new Location(id, name, longitude, latitude, discription);

                locations.add(loc);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.FINE, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return locations;
    }
}

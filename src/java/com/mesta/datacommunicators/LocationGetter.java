/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.datacontrollers.CommentController;
import com.mesta.models.Location;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class LocationGetter {

    private Connection connection;
    private PreparedStatement statement;
    private final String DRIVER_STRING = "com.mysql.jdbc.Driver";

    /**
     * method for getting all locations.
     */
    public Deque getAllLocations() throws SQLException {
        Deque locations = new ArrayDeque();
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT l.ID, l.Name, l.Latitude, l.Longitude, l.Description FROM Location l";
            statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                double latitude = result.getDouble("Latitude");
                double longitude = result.getDouble("Longitude");
                String discription = result.getString("Description");

                Location loc = new Location(id, name, longitude, latitude, discription);
                addImagesFromLocation(loc);
                CommentController.getController().commentGetter().getCommentsFromLocation(loc);

                locations.add(loc);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return locations;
    }

    /**
     * gets one location
     * @param ID Id of the location
     * @return location
     * @throws SQLException 
     */
    public Location getOneLocation(int ID) throws SQLException {
        Location location = null;
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT ID, Name, Latitude, Longitude, Description FROM Location WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(ID));

            ResultSet result = statement.executeQuery();
            result.next();
            location = new Location(ID, result.getString("Name"), result.getDouble("Longitude"), result.getDouble("Latitude"), result.getString("Description"));
            addImagesFromLocation(location);
            CommentController.getController().commentGetter().getCommentsFromLocation(location);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return location;
    }

    /**
     * gets 5 closes locations to point with lat and lon
     * @param lat latitude
     * @param lon longitude
     * @return array of locations
     * @throws SQLException 
     */
    public Deque getNearbyLocations(double lat, double lon) throws SQLException {
        Deque locations = new ArrayDeque<Location>();
        int count = 1;
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT l.ID, l.Name, l.Latitude, l.Longitude, l.Description FROM Location l ORDER BY ABS(l.Latitude - ?) + ABS(l.Longitude - ?) LIMIT 5";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, lat);
            statement.setDouble(2, lon);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                double latitude = result.getDouble("Latitude");
                double longitude = result.getDouble("Longitude");
                String discription = result.getString("Description");

                Location loc = new Location(id, name, longitude, latitude, discription);
                addImagesFromLocation(loc);
                CommentController.getController().commentGetter().getCommentsFromLocation(loc);

                locations.add(loc);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }           
        return locations;
    }

    /**
     * adds images to a location
     * @param loc location
     * @throws SQLException 
     */
    private void addImagesFromLocation(Location loc) throws SQLException {
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT i.Path FROM Image i, Location l WHERE l.ID = i.LocationID AND l.ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, loc.getId());

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String image = result.getString("Path");
                if (!"".equals(image)) {
                    loc.addImage(image);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }
}



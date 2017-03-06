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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class LocationGetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;
    private PreparedStatement statement;

    /**
     * method for getting all locations.
     *
     * @param content representation for the resource
     */
    public ArrayDeque getAllLocations() throws SQLException {
        ArrayDeque locations = new ArrayDeque();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return locations;
    }

    public Location getOneLocation(int ID) throws SQLException {
        Location location = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "SELECT ID, Name, Latitude, Longitude, Description FROM Location WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(ID));

            ResultSet result = statement.executeQuery();
            result.next();
            location = new Location(ID, result.getString("Name"), result.getDouble("Longitude"), result.getDouble("Latitude"), result.getString("Description"));
            addImagesFromLocation(location);
            CommentController.getController().commentGetter().getCommentsFromLocation(location);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return location;
    }

    public ArrayDeque getNearbyLocations(double lat, double lon) throws SQLException {
        ArrayDeque locations = new ArrayDeque<Location>();
        Iterator<Location> iter = null;
        int count = 1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "SELECT l.ID, l.Name, l.Latitude, l.Longitude, l.Description FROM Location l ORDER BY ABS(l.Latitude - ?) + ABS(l.Longitude - ?)";
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
            iter = locations.iterator();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        ArrayDeque<Location> nearest = new ArrayDeque<Location>();
        if (iter != null) {
            while (iter.hasNext() && count < 5) {
                nearest.add(iter.next());
                count++;
            }
        }

        return nearest;
    }

    private void addImagesFromLocation(Location loc) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }
}

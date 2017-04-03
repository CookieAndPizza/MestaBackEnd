/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.datacontrollers.CommentController;
import com.mesta.models.Comment;
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
                addLocationData(loc, connection);

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
     * method for searching locations.
     */
    public Deque search(String value) throws SQLException {
        Deque locations = new ArrayDeque();
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT l.ID, l.Name, l.Latitude, l.Longitude, l.Description FROM Location l LEFT JOIN Tag t ON (l.ID = t.LocationID) WHERE l.Name LIKE ? OR t.Tag LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + value + "%");
            statement.setString(2, "%" + value + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                double latitude = result.getDouble("Latitude");
                double longitude = result.getDouble("Longitude");
                String discription = result.getString("Description");

                Location loc = new Location(id, name, longitude, latitude, discription);
                addLocationData(loc, connection);
            

                locations.add(loc);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return locations;
    }
    
    public Deque searchByCategory(String value) throws SQLException {
        Deque locations = new ArrayDeque();
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT * FROM Location l LEFT JOIN BelongsTo b ON (b.LocationID = l.ID) WHERE CategoryID = (SELECT ID FROM Category WHERE Name LIKE ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + value + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                double latitude = result.getDouble("Latitude");
                double longitude = result.getDouble("Longitude");
                String discription = result.getString("Description");

                Location loc = new Location(id, name, longitude, latitude, discription);
                addLocationData(loc, connection);
            

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
     *
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
            addLocationData(location, connection);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return location;
    }

    public ArrayDeque getNearbyLocations(double lat, double lon, int offset) throws SQLException {
        ArrayDeque locations = new ArrayDeque<Location>();
        int count = 1;
        try {
            Class.forName(DRIVER_STRING);
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT l.ID, l.Name, l.Latitude, l.Longitude, l.Description FROM Location l ORDER BY ABS(l.Latitude - ?) + ABS(l.Longitude - ?) LIMIT 10 OFFSET ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, lat);
            statement.setDouble(2, lon);
            statement.setInt(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("Name");
                double latitude = result.getDouble("Latitude");
                double longitude = result.getDouble("Longitude");
                String discription = result.getString("Description");

                Location loc = new Location(id, name, longitude, latitude, discription);
                addLocationData(loc, connection);
               

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
     *
     * @param loc location
     * @throws SQLException
     */
    private void addImagesFromLocation(Location loc, Connection connection) throws SQLException {
        String query = "SELECT i.ID FROM Image i, Location l WHERE l.ID = i.LocationID AND l.ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, loc.getId());

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String image = result.getString("ID");
            if (!"".equals(image)) {
                loc.addImage("i.the-mesta.com/" + image);
            }
        }
    }

    private void addTagsFromLocation(Location loc, Connection connection) throws SQLException {
        String query = "SELECT * FROM Tag WHERE LocationID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, loc.getId());

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String tag = result.getString("Tag");
            if (!"".equals(tag)) {
                loc.addTag(tag);
            }
        }
    }

    private void addCategoryFromLocation(Location loc, Connection connection) throws SQLException {
        String query = "SELECT c.Name FROM Category c WHERE ID = (SELECT CategoryID FROM BelongsTo WHERE LocationID = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, loc.getId());

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            loc.setCategory(Location.Category.valueOf(result.getString("Name")));
        }
    }

    private void addCommentsFromLocation(Location loc, Connection connection) throws SQLException {
        String query = "SELECT c.Time, c.Text, c.AccountID FROM Comment c WHERE c.LocationID = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(loc.getId()));

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String id = result.getString("AccountID");
            String comment = result.getString("Text");
            String date = result.getString("Time");
            Comment com = new Comment(id, comment, date);
            loc.addComment(com);
        }
    }

    private void addLocationData(Location loc, Connection connection) throws SQLException {
        addCategoryFromLocation(loc, connection);
        addImagesFromLocation(loc, connection);
        addTagsFromLocation(loc, connection);
        addCommentsFromLocation(loc, connection);
    }
}

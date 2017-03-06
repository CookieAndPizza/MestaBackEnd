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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harm
 */
public class LocationSetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;
    private PreparedStatement statement;

    public boolean saveLocation(Location loc) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "INSERT INTO Location (Name, Latitude, Longitude, Description) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, loc.getName());
            statement.setDouble(2, loc.getLatitude());
            statement.setDouble(3, loc.getLongitude());
            statement.setString(4, loc.getDescription());

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            statement.close();
            connection.close();
        }
        return false;
    }
}

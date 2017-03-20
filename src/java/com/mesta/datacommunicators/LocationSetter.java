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
import java.sql.Statement;
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

    public DatabaseInfo.DatabaseRepsonse saveLocation(Location loc, String login, String token) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            if (CallVerifier.verify(login, token, connection)) {
                String query = "INSERT INTO Location (Name, Latitude, Longitude, Description) VALUES (?, ?, ?, ?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, loc.getName());
                statement.setDouble(2, loc.getLatitude());
                statement.setDouble(3, loc.getLongitude());
                statement.setString(4, loc.getDescription());

                int affectedRows = statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                int id = -1;
                if (keys.next()) {
                    id = keys.getInt(1);
                }
                DatabaseInfo.DatabaseRepsonse tagresponse = saveTags(loc, connection, id);
                DatabaseInfo.DatabaseRepsonse categoryResponse = saveCategory(loc, connection, id);

                if (affectedRows > 0 && tagresponse == DatabaseInfo.DatabaseRepsonse.SUCCES && categoryResponse == DatabaseInfo.DatabaseRepsonse.SUCCES) {
                    return DatabaseInfo.DatabaseRepsonse.SUCCES;
                }
            } else {
                return DatabaseInfo.DatabaseRepsonse.TOKEN_NOT_VALID;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            statement.close();
            connection.close();
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }

    private DatabaseInfo.DatabaseRepsonse saveTags(Location loc, Connection connection, int locationID) throws SQLException {

        if (locationID == -1) {
            return DatabaseInfo.DatabaseRepsonse.FAILED;
        }

        String tagQuery = "INSERT INTO Tag (LocationID, Tag) VALUES (?, ?)";
        PreparedStatement tagStatement = null;

        boolean succes = false;

        for (String tag : loc.getTags()) {
            tagStatement = connection.prepareStatement(tagQuery);
            tagStatement.setInt(1, locationID);
            tagStatement.setString(2, tag);
            int affectedRows = tagStatement.executeUpdate();

            if (!succes) {
                succes = affectedRows > 0;
            }
        }

        if (succes) {
            return DatabaseInfo.DatabaseRepsonse.SUCCES;
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }

    private DatabaseInfo.DatabaseRepsonse saveCategory(Location loc, Connection connection, int locationID) throws SQLException {
        if (locationID == -1) {
            return DatabaseInfo.DatabaseRepsonse.FAILED;
        }

        String idQuery = "SELECT ID FROM Category WHERE Name = ?";
        PreparedStatement idStatement = connection.prepareStatement(idQuery);
        
        idStatement.setString(1, loc.getCategory().toString());
        ResultSet set = idStatement.executeQuery();
        int id = -1;
        while(set.next()){
            id = set.getInt("ID");
        }

        String tagQuery = "INSERT INTO BelongsTo (LocationID, CategoryID) VALUES (?, ?)";
        PreparedStatement catStatement = connection.prepareStatement(tagQuery);
        catStatement.setInt(1, locationID);
        catStatement.setInt(2, id);
        int affectedRows = catStatement.executeUpdate();

        if (affectedRows > 0) {
            return DatabaseInfo.DatabaseRepsonse.SUCCES;
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }
}

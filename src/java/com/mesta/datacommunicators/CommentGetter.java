/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import com.mesta.models.Comment;
import com.mesta.models.Location;
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
public class CommentGetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;
    private PreparedStatement statement;

    /**
     * method for getting all locations.
     *
     * @param content representation for the resource
     */
    public ArrayDeque getCommentsFromLocation(Location loc) throws SQLException {
        ArrayDeque locations = new ArrayDeque();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            String query = "SELECT c.Title, c.Text, c.AccountID FROM Comment c WHERE c.LocationID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(loc.getId()));

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String id = result.getString("AccountID");
                String comment = result.getString("Text");
                String title = result.getString("Title");
                Comment com = new Comment(id, comment, title);
                loc.addComment(com);
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
}

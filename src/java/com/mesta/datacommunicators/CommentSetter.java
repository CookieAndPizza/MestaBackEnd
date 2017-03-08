/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import com.mesta.models.Comment;
import java.util.logging.Level;

/**
 *
 * @author Vernoxius
 */
public class CommentSetter {

    private static final Logger LOGGER = Logger.getLogger(LocationSetter.class.getName());
    private Connection connection;
    private PreparedStatement statement;

    public DatabaseInfo.DatabaseRepsonse saveComment(Comment comment, String login, String token) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.ConnectionString, DatabaseInfo.LoginName, DatabaseInfo.Password);

            if (CallVerifier.verify(login, token, connection)) {
                String query = "INSERT INTO Comment (LocationID, AccountID, Title, Text) VALUES (?, ?, ?, ?)";
                statement = connection.prepareStatement(query);

                statement.setString(1, comment.getLocationID());
                statement.setString(2, comment.getfaceBookID());
                statement.setString(3, comment.getTitle());
                statement.setString(4, comment.getComment());

                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    return DatabaseInfo.DatabaseRepsonse.SUCCES;
                }
            }else{
                return DatabaseInfo.DatabaseRepsonse.TOKEN_NOT_VALID;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LocationSetter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return DatabaseInfo.DatabaseRepsonse.FAILED;
    }
}

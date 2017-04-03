/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mesta.datacommunicators;

import java.io.Console;
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
 * @author Vernoxius
 */
public class CategoryGetter {

    private Connection connection;
    private PreparedStatement statement;

    public Deque getCategories() throws SQLException {
        Deque categories = new ArrayDeque();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseInfo.CONNECTION_STRING, DatabaseInfo.LOGIN_NAME, DatabaseInfo.PASSWORD);

            String query = "SELECT Name FROM Category";
            statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                categories.add('"' + result.getString("Name") + '"');
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoryGetter.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connection.close();
        }
        return categories;
    }
}

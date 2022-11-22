package org.example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class ConnectionManager {
    @Value("${jdbc.url}")
    private String url ;
    @Value("${jdbc.driverName}")
    private String driverName ;
    @Value("${jdbc.user}")
    private String username;
    @Value("${jdbc.pass}")
    private String password;
    private volatile Connection con;

    public Connection getConnection() {
        if(con==null) {
            synchronized (ConnectionManager.class) {
                if(con!=null)
                    return con;
                try {
                    Class.forName(driverName);
                    try {
                        con = DriverManager.getConnection(url, username, password);
                        System.out.println("Connected Successfully");
                    } catch (SQLException ex) {
                        // log an exception. fro example:
                        System.err.println("Failed to create the database connection.");
                    }
                } catch (ClassNotFoundException ex) {
                    // log an exception. for example:
                    System.err.println("Driver not found.");
                }
                return con;
            }
        }
        return con;
    }
    public void closeConnection(){
        try {
            con.close();
            con=null;
            System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            System.err.println("Couldn't close connection");
        }
    }
}

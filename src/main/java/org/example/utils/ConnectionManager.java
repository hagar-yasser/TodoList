package org.example.utils;

import java.sql.*;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/TodoList";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "P@ssw0rd";
    private static volatile Connection con;
    private static String urlstring;

    public static Connection getConnection() {
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
                        System.out.println("Failed to create the database connection.");
                    }
                } catch (ClassNotFoundException ex) {
                    // log an exception. for example:
                    System.out.println("Driver not found.");
                }
                return con;
            }
        }
        return con;
    }
    public static void closeConnection(){
        try {
            con.close();
            con=null;
            System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            System.err.println("Couldn't close connection");
        }
    }
}

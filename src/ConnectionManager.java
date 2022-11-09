import java.sql.*;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/TodoList";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "P@ssw0rd";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlstring, username, password);
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

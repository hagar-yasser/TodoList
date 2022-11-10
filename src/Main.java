import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Connection conn=ConnectionManager.getConnection();
        CommandMenu commandMenu=new CommandMenu(new TodoList(conn));
        commandMenu.executeFunctionFromTheCommandLine();
        ConnectionManager.closeConnection(conn);
    }
}

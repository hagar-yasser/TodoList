import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Connection conn=ConnectionManager.getConnection();
        CommandMenu commandMenu=new CommandMenu(new TodoList(conn));
        commandMenu.executeFunctionFromTheCommandLine();
        ConnectionManager.closeConnection(conn);
    }
}

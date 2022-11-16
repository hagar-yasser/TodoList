import repository.TodoList;
import utils.CommandMenu;
import utils.ConnectionManager;

import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args){
        Connection conn= ConnectionManager.getConnection();
        System.err.println(conn==null);
        CommandMenu commandMenu=new CommandMenu(new TodoList(conn));
        commandMenu.executeFunctionFromTheCommandLine();
        ConnectionManager.closeConnection(conn);
    }
}

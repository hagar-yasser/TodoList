import java.io.*;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {


        CommandMenu commandMenu=new CommandMenu(new TodoList());
        commandMenu.executeFunctionFromTheCommandLine();

    }
}

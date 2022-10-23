import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TodoList todoList;
        BufferedReader br = new BufferedReader(new FileReader("myTodoList.txt"));
        if (br.readLine() == null) {
            todoList=new TodoList();
        }
        else {
            FileInputStream fileInputStream
                    = new FileInputStream("myTodoList.txt");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(new BufferedInputStream(fileInputStream));
            todoList = (TodoList) objectInputStream.readObject();
            objectInputStream.close();
        }
        CommandMenu commandMenu=new CommandMenu(todoList);
        commandMenu.executeFunctionFromTheCommandLine();
        FileOutputStream fileOutputStream
                = new FileOutputStream("myTodoList.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream));
        objectOutputStream.writeObject(todoList);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}

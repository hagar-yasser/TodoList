import java.time.LocalDate;
import java.util.Scanner;

public class CommandMenu {
    private TodoList todoList = new TodoList();
    public void printAddItemOptions(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the title of the new todo item");
        String title=sc.nextLine();
        System.out.println("Enter the description of the new todo item");
        String description=sc.nextLine();
        System.out.println("Enter the category of the new todo item");
        String category=sc.nextLine();
        System.out.println("Enter the priority of the new todo item (the highest the input the lowest the priority. Highest priority is 1");
        int priority=Integer.parseInt(sc.nextLine());
        System.out.println("Enter the start date of the todo item in the following format dd-mm-yy");
        LocalDate startDate=LocalDate.parse(sc.nextLine());
        System.out.println("Enter the end date of the todo item in the following format dd-mm-yy");
        LocalDate endDate=LocalDate.parse(sc.nextLine());
        TodoItem newItem=new TodoItem();
        newItem.setTitle(title);
        newItem.setCategory(category);
        newItem.setDescription(description);
        newItem.setPriority(priority);
        newItem.setStartDate(startDate);
        newItem.setEndDate(endDate);
        todoList.addItem(newItem);
    }
    public void printDeleteItemOptions(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the title of the todo item you want to delete");
        String title=sc.nextLine();
        todoList.deleteItem(title);
    }

}

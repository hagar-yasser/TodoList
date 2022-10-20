import java.time.LocalDate;
import java.util.Scanner;

public class CommandMenu {
    private TodoList todoList;

    public CommandMenu(TodoList todoList) {
        this.todoList = todoList;
    }

    public void executeFunctionFromTheCommandLine() {
        while (true) {
            printMainMenu();
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            switch (command) {
                case "1":
                    printAddItemOptions();
                    break;
                case "2":
                    //print update item options
                    break;
                case "3":
                    printDeleteItemOptions();
                    break;
                case "4":
                    //show all items
                    break;
                case "5":
                    printShowTopFiveByStartDate();
                    break;
                case "6":
                    printShowTopFiveByEndDate();
                    break;
                case "7":
                    printTodoItemsByTitleOptions();
                    break;
                case "8":
                    printTodoItemsByStartDateOptions();
                    break;
                case "9":
                    printTodoItemsByEndDateOptions();
                    break;
                case "10":
                    printTodoItemsByPriorityOptions();
                    break;
                case "11":
                    //print add item to category
                    break;
                case "12":
                    //print add item to favourite
                    break;
                case "0":
                    //exit
                    return;

                default:
                    System.out.println("Wrong input. Please enter a valid input");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("Type the number of the command you want to perform and press enter.");
        System.out.println("1- Add Item to the todo List");
        System.out.println("2- Update an Item in the todo List");
        System.out.println("3 - Delete Item from the todo List");
        System.out.println("4- Show all Items in the Todo List");
        System.out.println("5- Show Top Five Items in the Todo List by Start Date");
        System.out.println("6- Show Top Five Items in the Todo List by End Date");
        System.out.println("7- Search for Items in the Todo List by Title");
        System.out.println("8- Search for Items in the Todo List by Start Date");
        System.out.println("9- Search for Items in the Todo List by End Date");
        System.out.println("10- Search for Items in the Todo List by Priority");
        System.out.println("11- Add Item to a Category");
        System.out.println("12- Add Item to Favourites");
        System.out.println("0- exit");


    }

    private void printAddItemOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the title of the new todo item");
        String title = sc.nextLine();
        System.out.println("Enter the description of the new todo item");
        String description = sc.nextLine();
        System.out.println("Enter the category of the new todo item");
        String category = sc.nextLine();
        //try and catch on priority
        boolean missingPriority = true;
        int priority =-1;
        while(missingPriority) {
            System.out.println("Enter the priority of the new todo item (the highest the input the lowest the priority. Highest priority is 1)");
            try {
                priority = Integer.parseInt(sc.nextLine());
                if (priority < 1)
                    throw new Exception();
                missingPriority = false;
            } catch (Exception e) {

               

                System.out.println("Try again. Enter a correct priority number");

            }
        }
        //try and catch startDate
        LocalDate startDate=null ;
        boolean missingStartDate=true;
        while (missingStartDate){
            System.out.println("Enter the start date of the todo item in the following format yyyy-mm-dd");
            try {
                startDate = LocalDate.parse(sc.nextLine());
                missingStartDate=false;
            }
            catch (Exception e){
                System.out.println("Wrong Start Date Format. Please enter the required start date format");
            }
        }
        //try and catch endDate
        LocalDate endDate=null;
        boolean missingEndDate=true;
        while (missingEndDate){
            System.out.println("Enter the end date of the todo item in the following format yyyy-mm-dd");
            try {
                endDate = LocalDate.parse(sc.nextLine());
                missingEndDate=false;
            }
            catch (Exception e){
                System.out.println("Wrong End Date Format. Please enter the required end date format");
            }
        }
        TodoItem newItem = new TodoItem();
        newItem.setTitle(title);
        newItem.setCategory(category);
        newItem.setDescription(description);
        newItem.setPriority(priority);
        newItem.setStartDate(startDate);
        newItem.setEndDate(endDate);
        todoList.addItem(newItem);
        System.out.println("Addition Operation Done");
    }

    private void printDeleteItemOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the title of the todo item you want to delete");
        String title = sc.nextLine();
        todoList.deleteItem(title);
        System.out.println("Delete Operation Done");
    }

    private void printShowTopFiveByStartDate() {
        TodoItem[] todoListSortedAscendinglyByStartDate = todoList.sortAscendinglyByStartDate();

        for (int i = 0; i < 5 && i < todoListSortedAscendinglyByStartDate.length; i++) {
            if (todoListSortedAscendinglyByStartDate[i] == null) {
                System.out.println("The above items are the only items in the todoList");
                break;
            }
            System.out.println(todoListSortedAscendinglyByStartDate[i]);

        }
    }

    private void printShowTopFiveByEndDate() {
        TodoItem[] todoListSortedAscendinglyByEndDate = todoList.sortAscendinglyByEndDate();
        ;
        for (int i = 0; i < 5 && i < todoListSortedAscendinglyByEndDate.length; i++) {
            if (todoListSortedAscendinglyByEndDate[i] == null) {
                System.out.println("The above items are the only items in the todoList");
                break;
            }
            System.out.println(todoListSortedAscendinglyByEndDate[i]);

        }
    }
    private void printTodoItemsByTitleOptions(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The title you want to search about: ");
        String title = sc.nextLine();
        System.out.println(todoList.searchByTitle(title));
    }
    private void printTodoItemsByStartDateOptions(){
        Scanner sc = new Scanner(System.in);
        LocalDate startDate=null ;
        boolean missingStartDate=true;
        while (missingStartDate){
            System.out.println("Enter the start date of the todo item you want to search about in the following format yyyy-mm-dd");
            try {
                startDate = LocalDate.parse(sc.nextLine());
                missingStartDate=false;
            }
            catch (Exception e){
                System.out.println("Wrong Start Date Format. Please enter the required start date format");
            }
        }
        if(todoList.searchByStartDate(startDate) == null){
            System.out.println("The item you search about doesn't exist in the list !!");
        }else{
            TodoItem[] listOfResult = todoList.searchByStartDate(startDate);
            System.out.println("The result after searching: ");
            for (int i= 0; i < listOfResult.length; i++){
                System.out.println(listOfResult[i]);
            }
        }
    }
    private void printTodoItemsByEndDateOptions(){
        Scanner sc = new Scanner(System.in);
        LocalDate endDate=null;
        boolean missingEndDate=true;
        while (missingEndDate){
            System.out.println("Enter the end date of the todo item you want to search about in the following format yyyy-mm-dd");
            try {
                endDate = LocalDate.parse(sc.nextLine());
                missingEndDate=false;
            }
            catch (Exception e){
                System.out.println("Wrong End Date Format. Please enter the required end date format");
            }
        }
        if(todoList.searchByEndDate(endDate) == null){
            System.out.println("The item you search about doesn't exist in the list !!");
        }else{
            TodoItem[] listOfResult = todoList.searchByEndDate(endDate);
            System.out.println("The result after searching: ");
            for (int i= 0; i < listOfResult.length; i++){
                System.out.println(listOfResult[i]);
            }
        }
    }
    private void printTodoItemsByPriorityOptions(){
        Scanner sc = new Scanner(System.in);
        boolean missingPriority = true;
        int priority =-1;
        while(missingPriority) {
            System.out.println("Enter the priority you want to search about:");
            try {
                priority = sc.nextInt();
                if (priority < 1)
                    throw new Exception();
                missingPriority = false;
            } catch (Exception e) {
                System.out.println("Try again. Enter a correct priority number");
            }
        }
        if(todoList.searchByPriority(priority) == null){
            System.out.println("The item you search about doesn't exist in the list !!");
        }else{
            TodoItem[] listOfResult = todoList.searchByPriority(priority);
            System.out.println("The result after searching: ");
            for (int i= 0; i < listOfResult.length; i++){
                System.out.println(listOfResult[i]);
            }
        }
    }

}

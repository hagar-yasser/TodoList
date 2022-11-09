import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class TodoList implements Serializable {
    private int currentSizeOfTodoItemsList = 100;
    private TodoItem[] todoItemsList = new TodoItem[currentSizeOfTodoItemsList];
    private int indexOfLastItemInList = -1;
    private static String tableName = "TodoItem";
    private static String titleColumnName="Title";
    private static String startDateColumnName="StartDate";
    private static String endDateColumnName="EndDate";
    private static String priorityColumnName="Priority";

    public boolean addItem(TodoItem todoItem) {
        //Check if title of new item already exists in array
        if (getIndexOfTodoItemWithTitle(todoItem.getTitle()) == -1) {
            //If the array is full duplicate its size
            if (indexOfLastItemInList == currentSizeOfTodoItemsList - 1) {
                duplicateArraySize();
            }
            todoItemsList[indexOfLastItemInList + 1] = todoItem;
            indexOfLastItemInList++;
            return true;
        }
        return false;
    }

    public boolean deleteItem(String title) {
        int indexOfItemWithTitle = getIndexOfTodoItemWithTitle(title);
        if (indexOfItemWithTitle == -1)
            return false;
        todoItemsList[indexOfItemWithTitle] = null;
        //Shift items to the right of the deleted item to the left one step
        for (int i = indexOfItemWithTitle + 1; i <= indexOfLastItemInList; i++) {
            todoItemsList[i - 1] = todoItemsList[i];
        }
        todoItemsList[indexOfLastItemInList]=null;
        indexOfLastItemInList -= 1;
        return true;

    }

    public int getIndexOfTodoItemWithTitle(String title) {
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getTitle().equals(title))
                return i;
        }
        //element doesn't exist
        return -1;
    }

    public void duplicateArraySize() {
        TodoItem[] duplicateSizeTodoItemsList = new TodoItem[currentSizeOfTodoItemsList * 2];
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            duplicateSizeTodoItemsList[i] = todoItemsList[i];
        }
        currentSizeOfTodoItemsList *= 2;
        todoItemsList = duplicateSizeTodoItemsList;
    }

    public TodoItem[] sortAscendinglyByStartDate() {

        Arrays.sort(todoItemsList, new Comparator<TodoItem>() {
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        //if both are null or having the same reference then they are equal
                        if (o1 == o2)
                            return 0;
                        //Move nulls to the right make them largest when compared to any other object
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        //compare start dates
                        return o1.getStartDate().compareTo(o2.getStartDate());

                    }
                }
        );

        return todoItemsList;
    }

    public TodoItem[] sortAscendinglyByEndDate() {
        Arrays.sort(todoItemsList, new Comparator<TodoItem>() {
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        //if both are null or having the same reference then they are equal
                        if (o1 == o2)
                            return 0;
                        //Move nulls to the right make them largest when compared to any other object
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        //compare start dates
                        return o1.getEndDate().compareTo(o2.getEndDate());

                    }
                }
        );

        return todoItemsList;
    }

    public TodoItem searchByTitle(String title) {
        TodoItem todoItem = new TodoItem();
        Connection conn = ConnectionManager.getConnection();
        try {
            String sql = "select * from " + tableName + " where " + titleColumnName + "=" + title;
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next() == false){
                return null;
            }else{
                while(rs.next()){
                    todoItem.setTitle(rs.getString(1));
                    todoItem.setDescription(rs.getString(2));
                    todoItem.setCategory(rs.getString(3));
                    todoItem.setPriority(Integer.parseInt(rs.getString(4)));
                    todoItem.setStartDate(rs.getDate(5).toLocalDate());
                    todoItem.setEndDate(rs.getDate(6).toLocalDate());
                    todoItem.setFavourite(rs.getBoolean(7));
            }
            }
        }
        catch (SQLException s){
            throw new RuntimeException(s);
        }
        finally {
            ConnectionManager.closeConnection(conn);
        }
        return todoItem;
    }

    public TodoItem[] searchByStartDate(LocalDate startDate) {
        TodoItem[] listOfResult;
        Connection conn = ConnectionManager.getConnection();
        try {
            String sql = "select * from " + tableName + " where " + startDateColumnName + "=" + startDate;
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = new TodoItem[rs.getInt("count(*)")];
                int indexOfListOfResult = 0;
                while(rs.next()){
                    listOfResult[indexOfListOfResult].setTitle(rs.getString(1));
                    listOfResult[indexOfListOfResult].setDescription(rs.getString(2));
                    listOfResult[indexOfListOfResult].setCategory(rs.getString(3));
                    listOfResult[indexOfListOfResult].setPriority(Integer.parseInt(rs.getString(4)));
                    listOfResult[indexOfListOfResult].setStartDate(rs.getDate(5).toLocalDate());
                    listOfResult[indexOfListOfResult].setEndDate(rs.getDate(6).toLocalDate());
                    listOfResult[indexOfListOfResult].setFavourite(rs.getBoolean(7));
                    indexOfListOfResult++;
                }
            }
        }
        catch (SQLException s){
            throw new RuntimeException(s);
        }
        finally {
            ConnectionManager.closeConnection(conn);
        }
        return listOfResult;
        }

    public TodoItem[] searchByEndDate(LocalDate endDate) {
        TodoItem[] listOfResult;
        Connection conn = ConnectionManager.getConnection();
        try {
            String sql = "select * from " + tableName + " where " + endDateColumnName + "=" + endDate;
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = new TodoItem[rs.getInt("count(*)")];
                int indexOfListOfResult = 0;
                while(rs.next()){
                    listOfResult[indexOfListOfResult].setTitle(rs.getString(1));
                    listOfResult[indexOfListOfResult].setDescription(rs.getString(2));
                    listOfResult[indexOfListOfResult].setCategory(rs.getString(3));
                    listOfResult[indexOfListOfResult].setPriority(Integer.parseInt(rs.getString(4)));
                    listOfResult[indexOfListOfResult].setStartDate(rs.getDate(5).toLocalDate());
                    listOfResult[indexOfListOfResult].setEndDate(rs.getDate(6).toLocalDate());
                    listOfResult[indexOfListOfResult].setFavourite(rs.getBoolean(7));
                    indexOfListOfResult++;
                }
            }
        }
        catch (SQLException s){
            throw new RuntimeException(s);
        }
        finally {
            ConnectionManager.closeConnection(conn);
        }
        return listOfResult;
    }

    public TodoItem[] searchByPriority(int priority) {
        TodoItem[] listOfResult;
        Connection conn = ConnectionManager.getConnection();
        try {
            String sql = "select * from " + tableName + " where " + priorityColumnName + "=" + priority;
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = new TodoItem[rs.getInt("count(*)")];
                int indexOfListOfResult = 0;
                while(rs.next()){
                    listOfResult[indexOfListOfResult].setTitle(rs.getString(1));
                    listOfResult[indexOfListOfResult].setDescription(rs.getString(2));
                    listOfResult[indexOfListOfResult].setCategory(rs.getString(3));
                    listOfResult[indexOfListOfResult].setPriority(Integer.parseInt(rs.getString(4)));
                    listOfResult[indexOfListOfResult].setStartDate(rs.getDate(5).toLocalDate());
                    listOfResult[indexOfListOfResult].setEndDate(rs.getDate(6).toLocalDate());
                    listOfResult[indexOfListOfResult].setFavourite(rs.getBoolean(7));
                    indexOfListOfResult++;
                }
            }
        }
        catch (SQLException s){
            throw new RuntimeException(s);
        }
        finally {
            ConnectionManager.closeConnection(conn);
        }
        return listOfResult;
    }


    public void updateItem(String title, TodoItem updatedTodoItem){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == indexOfTodoItemWithTitle ||
                getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == -1){
            todoItemsList[indexOfTodoItemWithTitle].setTitle(updatedTodoItem.getTitle());
            todoItemsList[indexOfTodoItemWithTitle].setDescription(updatedTodoItem.getDescription());
            todoItemsList[indexOfTodoItemWithTitle].setPriority(updatedTodoItem.getPriority());
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(updatedTodoItem.getFavourite());
            todoItemsList[indexOfTodoItemWithTitle].setCategory(updatedTodoItem.getCategory());
            todoItemsList[indexOfTodoItemWithTitle].setStartDate(updatedTodoItem.getStartDate());
            todoItemsList[indexOfTodoItemWithTitle].setEndDate(updatedTodoItem.getEndDate());
            System.out.println("Mission is completed successfully");
        }
        else{
            System.out.println("Mission is failed\nThe updated title is already exist");
        }
    }
    public TodoItem[] showAllItems() {
        if(indexOfLastItemInList==-1)
            return null;

        TodoItem[] actualListOfTodoItemsWithoutNulls = new TodoItem[indexOfLastItemInList+1];
        for(int counter=0; counter<=indexOfLastItemInList;counter++)
            actualListOfTodoItemsWithoutNulls[counter] = todoItemsList[counter];
        return actualListOfTodoItemsWithoutNulls;
    }
    public void addTodoItemToCategory( String title, String category){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(indexOfTodoItemWithTitle==-1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setCategory(category);
            System.out.println("Mission is completed successfully ");
        }
    }

    public void addTodoItemToFavorite(String title){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(indexOfTodoItemWithTitle==-1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(true);
            System.out.println("Mission is completed successfully ");
        }
    }
}


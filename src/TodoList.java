import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class TodoList implements Serializable {
    private int currentSizeOfTodoItemsList = 100;
    private TodoItem[] todoItemsList = new TodoItem[currentSizeOfTodoItemsList];
    private int indexOfLastItemInList = -1;
    private Connection conn;

    public TodoList(Connection conn) {
        this.conn = conn;
    }

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
        try {
            String sql = "select * from " + TodoItem.tableName + " where " + TodoItem.titleColumnName + "=" + "'"+title+"'";
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next() == false){
                return null;
            }else{
                    todoItem.setTitle(rs.getString(1));
                    todoItem.setDescription(rs.getString(2));
                    todoItem.setCategory(rs.getString(3));
                    todoItem.setPriority(Integer.parseInt(rs.getString(4)));
                    todoItem.setStartDate(rs.getDate(5).toLocalDate());
                    todoItem.setEndDate(rs.getDate(6).toLocalDate());
                    todoItem.setFavourite(rs.getBoolean(7));
            }
        }
        catch (SQLException s){
            s.printStackTrace();
        }
        return todoItem;
    }

    public TodoItem[] searchByStartDate(LocalDate startDate) {
        TodoItem[] listOfResult;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TodoItem.tableName, TodoItem.startDateColumnName,startDate));
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = getArrayOfTodosFromResultSet(rs);
            }
        }
        catch (SQLException s){
            s.printStackTrace();
            return new TodoItem[0];
        }
        return listOfResult;
        }

    public TodoItem[] searchByEndDate(LocalDate endDate) {
        TodoItem[] listOfResult;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TodoItem.tableName, TodoItem.endDateColumnName,endDate));
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = getArrayOfTodosFromResultSet(rs);
            }
        }
        catch (SQLException s){
            s.printStackTrace();
            return new TodoItem[0];
        }
        return listOfResult;
    }
    public TodoItem[] getArrayOfTodosFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            return new TodoItem[0];
        }

        int size = 0;
        rs.last();    // moves cursor to the last row
        size = rs.getRow();// get row id
        rs.beforeFirst();
        TodoItem[]TodoItemsInResult = new TodoItem[size];
        int index=0;
        while (rs.next()) {
            TodoItem row=new TodoItem();
            row.setTitle(rs.getString(TodoItem.titleColumnName));
            row.setDescription(rs.getString(TodoItem.descriptionColumnName));
            row.setCategory(rs.getString(TodoItem.categoryColumnName));
            row.setPriority(rs.getInt(TodoItem.priorityColumnName));
            row.setStartDate(LocalDate.parse(rs.getString(TodoItem.startDateColumnName)));
            row.setEndDate(LocalDate.parse(rs.getString(TodoItem.endDateColumnName)));
            row.setFavourite(rs.getInt(TodoItem.isFavoriteColumnName) != 0);
            TodoItemsInResult[index++]=row;
        }
        return TodoItemsInResult;
    }

    public TodoItem[] searchByPriority(int priority) {
        TodoItem[] listOfResult;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TodoItem.tableName, TodoItem.priorityColumnName,priority));
            if (rs.next() == false){
                return null;
            }else{
                listOfResult = getArrayOfTodosFromResultSet(rs);
            }
        }
        catch (SQLException s){
            s.printStackTrace();
            return new TodoItem[0];
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


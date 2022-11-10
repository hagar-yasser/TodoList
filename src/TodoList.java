import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(String.format("INSERT INTO %s VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                    TodoItem.tableName, todoItem.getTitle(), todoItem.getDescription(),
                    todoItem.getCategory(), todoItem.getPriority(),
                    todoItem.getStartDate(), todoItem.getEndDate(), todoItem.getFavourite() ? 1 : 0));
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean deleteItem(String title) {
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(String.format("DELETE FROM %s WHERE %s = '%s';", TodoItem.tableName, TodoItem.titleColumnName, title));
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
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
    public TodoItem[] getArrayOfTodosFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            return new TodoItem[0];
        }

        int size = 0;
        rs.last();    // moves cursor to the last row
        size = rs.getRow();// get row id
        rs.beforeFirst();
        TodoItem[]TodoItemsInResult = new TodoItem[size];
        int indexInTopFive=0;
        while (rs.next()) {
            TodoItem row=new TodoItem();
            row.setTitle(rs.getString(TodoItem.titleColumnName));
            row.setDescription(rs.getString(TodoItem.descriptionColumnName));
            row.setCategory(rs.getString(TodoItem.categoryColumnName));
            row.setPriority(rs.getInt(TodoItem.priorityColumnName));
            row.setStartDate(LocalDate.parse(rs.getString(TodoItem.startDateColumnName)));
            row.setEndDate(LocalDate.parse(rs.getString(TodoItem.endDateColumnName)));
            row.setFavourite(rs.getInt(TodoItem.isFavoriteColumnName) != 0);
            TodoItemsInResult[indexInTopFive++]=row;
        }
        return TodoItemsInResult;
    }

    public TodoItem[] topFiveAscendinglyByStartDate() {
        TodoItem[] topFiveItems;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s ORDER BY %s LIMIT 5;",
                    TodoItem.tableName, TodoItem.startDateColumnName));
           topFiveItems=getArrayOfTodosFromResultSet(rs);

        } catch (SQLException s) {
            s.printStackTrace();
            return new TodoItem[0];
        }
        return topFiveItems;
    }


    public TodoItem[] topFiveAscendinglyByEndDate() {
        TodoItem[] topFiveItems;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s ORDER BY %s LIMIT 5;",
                    TodoItem.tableName, TodoItem.endDateColumnName));
            topFiveItems=getArrayOfTodosFromResultSet(rs);

        } catch (SQLException s) {
            s.printStackTrace();
            return new TodoItem[0];
        }
        return topFiveItems;

    }

    public TodoItem searchByTitle(String title) {
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getTitle().equals(title))
                return todoItemsList[i];
        }
        return null;
    }

    public TodoItem[] searchByStartDate(LocalDate startDate) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getStartDate().compareTo(startDate) == 0) {
                sizeOfListOfResult++;
            }
        }
        if (sizeOfListOfResult == 0) {
            return null;
        } else {
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getStartDate().compareTo(startDate) == 0) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }

    public TodoItem[] searchByEndDate(LocalDate endDate) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getEndDate().compareTo(endDate) == 0) {
                sizeOfListOfResult++;
            }
        }
        if (sizeOfListOfResult == 0) {
            return null;
        } else {
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getEndDate().compareTo(endDate) == 0) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }

    public TodoItem[] searchByPriority(int priority) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getPriority() == priority) {
                sizeOfListOfResult++;
            }
        }
        if (sizeOfListOfResult == 0) {
            return null;
        } else {
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getPriority() == priority) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }


    public void updateItem(String title, TodoItem updatedTodoItem) {
        int indexOfTodoItemWithTitle = getIndexOfTodoItemWithTitle(title);
        if (getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == indexOfTodoItemWithTitle ||
                getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == -1) {
            todoItemsList[indexOfTodoItemWithTitle].setTitle(updatedTodoItem.getTitle());
            todoItemsList[indexOfTodoItemWithTitle].setDescription(updatedTodoItem.getDescription());
            todoItemsList[indexOfTodoItemWithTitle].setPriority(updatedTodoItem.getPriority());
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(updatedTodoItem.getFavourite());
            todoItemsList[indexOfTodoItemWithTitle].setCategory(updatedTodoItem.getCategory());
            todoItemsList[indexOfTodoItemWithTitle].setStartDate(updatedTodoItem.getStartDate());
            todoItemsList[indexOfTodoItemWithTitle].setEndDate(updatedTodoItem.getEndDate());
            System.out.println("Mission is completed successfully");
        } else {
            System.out.println("Mission is failed\nThe updated title is already exist");
        }
    }

    public TodoItem[] showAllItems() {
        if (indexOfLastItemInList == -1)
            return null;

        TodoItem[] actualListOfTodoItemsWithoutNulls = new TodoItem[indexOfLastItemInList + 1];
        for (int counter = 0; counter <= indexOfLastItemInList; counter++)
            actualListOfTodoItemsWithoutNulls[counter] = todoItemsList[counter];
        return actualListOfTodoItemsWithoutNulls;
    }

    public void addTodoItemToCategory(String title, String category) {
        int indexOfTodoItemWithTitle = getIndexOfTodoItemWithTitle(title);
        if (indexOfTodoItemWithTitle == -1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setCategory(category);
            System.out.println("Mission is completed successfully ");
        }
    }

    public void addTodoItemToFavorite(String title) {
        int indexOfTodoItemWithTitle = getIndexOfTodoItemWithTitle(title);
        if (indexOfTodoItemWithTitle == -1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(true);
            System.out.println("Mission is completed successfully ");
        }
    }
}
//

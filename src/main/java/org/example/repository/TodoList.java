package org.example.repository;

import org.example.DTO.TodoItem;
import org.example.utils.ConnectionManager;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;


public class TodoList implements Serializable {
    private int currentSizeOfTodoItemsList = 100;
    private TodoItem[] todoItemsList = new TodoItem[currentSizeOfTodoItemsList];
    private int indexOfLastItemInList = -1;

    private Connection conn;

    public TodoList() {

    }

    public boolean addItem(TodoItem todoItem) {
        conn= ConnectionManager.getConnection();
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
        finally {
            ConnectionManager.closeConnection();
        }
        return true;

    }


    public boolean deleteItem(String title) {
        conn=ConnectionManager.getConnection();
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(String.format("DELETE FROM %s WHERE %s = '%s';", TodoItem.tableName, TodoItem.titleColumnName, title));
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
        finally {
            ConnectionManager.closeConnection();
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

    public TodoItem[] topFiveAscendinglyByStartDate() {
        TodoItem[] topFiveItems;
        conn=ConnectionManager.getConnection();
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s ORDER BY %s LIMIT 5;",
                    TodoItem.tableName, TodoItem.startDateColumnName));
           topFiveItems=getArrayOfTodosFromResultSet(rs);

        } catch (SQLException s) {
            s.printStackTrace();
            return new TodoItem[0];
        }
        finally {
            ConnectionManager.closeConnection();
        }
        return topFiveItems;
    }


    public TodoItem[] topFiveAscendinglyByEndDate() {
        TodoItem[] topFiveItems;
        conn=ConnectionManager.getConnection();
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s ORDER BY %s LIMIT 5;",
                    TodoItem.tableName, TodoItem.endDateColumnName));
            topFiveItems=getArrayOfTodosFromResultSet(rs);

        } catch (SQLException s) {
            s.printStackTrace();
            return new TodoItem[0];
        }
        finally {
            ConnectionManager.closeConnection();
        }
        return topFiveItems;

    }

    public TodoItem searchByTitle(String title) {
        TodoItem todoItem = new TodoItem();
        conn=ConnectionManager.getConnection();
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
        }finally {
            ConnectionManager.closeConnection();
        }
        return todoItem;
    }

    public TodoItem[] searchByStartDate(LocalDate startDate) {
        TodoItem[] listOfResult;
        conn=ConnectionManager.getConnection();
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
        }finally {
            ConnectionManager.closeConnection();
        }
        return listOfResult;
        }

    public TodoItem[] searchByEndDate(LocalDate endDate) {
        TodoItem[] listOfResult;
        conn= ConnectionManager.getConnection();
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
        }finally {
            ConnectionManager.closeConnection();
        }
        return listOfResult;
    }


    public TodoItem[] searchByPriority(int priority) {
        TodoItem[] listOfResult;
        conn = ConnectionManager.getConnection();
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
        }finally {
            ConnectionManager.closeConnection();
        }
        return listOfResult;
    }


    public void updateItem(String title, TodoItem updatedTodoItem) {
        TodoItem todoItem = searchByTitle(title);
        if(todoItem==null) {
            System.out.println("Todo's not found");
        }
        else{
            try {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String query = "UPDATE TodoItem SET  Description =\""+ updatedTodoItem.getDescription()+"\" , Category =\""+updatedTodoItem.getCategory()+"\" , Priority = \""+updatedTodoItem.getPriority()+"\" , StartDate = \""+updatedTodoItem.getStartDate()+"\" , EndDate = \""+updatedTodoItem.getEndDate()+"\" WHERE Title LIKE \""+title+"%\" ;";
                statement.executeUpdate(query);
                System.out.println("Todo's updated successfully");
            }
            catch  (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public TodoItem[] showAllItems() {
        TodoItem[] todoList;
        Connection connection = ConnectionManager.getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query ="SELECT * FROM TodoItem ;";
            ResultSet resultSet=statement.executeQuery(query);
            todoList = getArrayOfTodosFromResultSet(resultSet);
            return todoList;

        }
        catch  (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionManager.closeConnection();
        }


        return null;
    }

    public void addTodoItemToCategory(String title, String category) {
        TodoItem todoItem = searchByTitle(title);
        if(todoItem==null) {
            System.out.println("Todo's not found");
        }
        else{
            try {
                Statement statement = conn.createStatement();
                String query = "UPDATE TodoItem SET   Category =\""+category+"\"  WHERE Title LIKE \""+title+"%\" ;";
                statement.executeUpdate(query);
                System.out.println("Todo's updated successfully");
            }
            catch  (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTodoItemToFavorite(String title) {
        TodoItem todoItem = searchByTitle(title);
        if(todoItem==null) {
            System.out.println("Todo's not found");
        }
        else{
            try {
                Statement statement = conn.createStatement();
                String query = "UPDATE TodoItem SET   IsFavorite = 1  WHERE Title LIKE \""+title+"%\" ;";
                statement.executeUpdate(query);
                System.out.println("Todo's updated successfully");
            }
            catch  (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
//

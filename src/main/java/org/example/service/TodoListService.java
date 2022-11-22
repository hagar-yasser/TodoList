package org.example.service;

import jakarta.ws.rs.core.Response;
import org.example.dto.TodoItem;
import org.example.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class TodoListService implements MyService {
    private TodoListRepository todoListRepository;
    private static final int highPriority = 1;
    private static final int lowPriority = 3;
    @Autowired
    public TodoListService(TodoListRepository todoListRepository){
        this.todoListRepository=todoListRepository;
    }

    private Response isValidTodoItem(TodoItem todoItem) {
        if (todoItem.getTitle() == null) {
            return Response.status(400, "title cannot be empty").build();
        }
        if (todoItem.getPriority() < highPriority || todoItem.getPriority() > lowPriority) {
            return Response.status(400, "priority has to be >=1 and <=3").build();
        }
        if (todoItem.getStartDate() == null) {
            return Response.status(400, "startDate cannot be empty").build();
        }
        if (todoItem.getEndDate() == null) {
            return Response.status(400, "endDate cannot be empty").build();
        }
        if (todoItem.getEndDate().compareTo(todoItem.getStartDate()) < 0)
            return Response.status(400, "endDate has to be >= startDate").build();
        return Response.status(200).build();

    }

    public Response addItem(TodoItem newItem) {
        Response validTodoItem = isValidTodoItem(newItem);
        if (validTodoItem.getStatus() != 200) {
            return validTodoItem;
        }
        try {
            boolean added = todoListRepository.addItem(newItem);
            if (added) {
                System.out.println("Addition Operation Done");
                return Response.status(200, "Addition Operation Done").build();
            } else
                return Response.status(400, "Couldn't add the todo item. Please try another title!").build();
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }

    }

    public Response deleteItem(String title) {
        try {
            boolean deleted = todoListRepository.deleteItem(title);
            if (deleted)
                return Response.status(200, "Delete Operation Done").build();
            else
                return Response.status(400, "Couldn't Delete the todo item. Please make sure this title exists!").build();
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }

    public Response getTopFiveByStartDate() {
        try {
            TodoItem[] topFive = todoListRepository.topFiveAscendinglyByStartDate();
            return Response.ok(topFive).build();
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }

    public Response getTopFiveByEndDate() {
        try {
            TodoItem[] topFive = todoListRepository.topFiveAscendinglyByEndDate();
            return Response.ok(topFive).build();
        } catch (Exception e) {
            return Response.status(500, e.getMessage()).build();
        }
    }
    public Response searchByTitle(String title){
        try {
            if (todoListRepository.searchByTitle(title) == null) {
                return Response.status(400, "Couldn't search in the todo item. Please make sure that this title exists!").build();
            } else {
                return Response.ok(todoListRepository.searchByTitle(title)).build();
            }
        }
        catch (Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response searchByStartDate(LocalDate startDate) {
        try{
            if (todoListRepository.searchByStartDate(startDate) == null) {
                return Response.status(400,"Couldn't search in the todo item. Please make sure that this start date exists!").build();
            } else {
                return Response.ok(todoListRepository.searchByStartDate(startDate)).build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response searchByEndDate(LocalDate endDate) {
        try{
            if (todoListRepository.searchByEndDate(endDate) == null) {
                return Response.status(400,"Couldn't search in the todo item. Please make sure that this end date exists!").build();
            } else {
                return Response.ok(todoListRepository.searchByEndDate(endDate)).build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response searchByPriority(int priority) {
        try{
            if (todoListRepository.searchByPriority(priority) == null) {
                return Response.status(400,"Couldn't search in the todo item. Please make sure that this priority exists!").build();
            } else {
                return Response.ok(todoListRepository.searchByPriority(priority)).build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response updateItem(String title, TodoItem updatedTodo) {
        try {
            if (todoListRepository.updateItem(title, updatedTodo)) {
                return Response.status(200, "Updated successfully").build();
            } else {
                return Response.status(400, "Not updated").build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response showAllItems() {
       try {
           TodoItem[]todoItems=todoListRepository.showAllItems();
           if(todoItems==null) {
               return Response.status(400,"There is no todo!").build();
           }else {
               return Response.ok(todoItems).build();
           }

       }catch(Exception e){
           return Response.status(500,e.getMessage()).build();
       }
    }

    public Response addTodoItemToCategory(String title, String category) {
        try {
            if(todoListRepository.addTodoItemToCategory(title,category)){
                return Response.status(200,"Updated successfully").build();
            }else {
                return Response.status(400,"Not updated").build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }

    public Response addTodoItemToFavorite(String title){
        try {
            if(todoListRepository.addTodoItemToFavorite(title)){
                return Response.status(200,"Updated successfully").build();
            }else {
                return Response.status(400,"Not updated").build();
            }
        }catch(Exception e){
            return Response.status(500,e.getMessage()).build();
        }
    }
}

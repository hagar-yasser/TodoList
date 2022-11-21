package org.example.service;

import jakarta.ws.rs.core.Response;
import org.example.DTO.TodoItem;
import org.example.repository.TodoList;

public class TodoListService implements MyService{
    private TodoList todoList=new TodoList();
    private static final int highPriority=1;
    private static final int lowPriority=3;
    private Response isValidTodoItem(TodoItem todoItem){
        if(todoItem.getTitle()==null) {
            return Response.status(400,"title cannot be empty").build();
        }
        if (todoItem.getPriority() < highPriority||todoItem.getPriority()>lowPriority){
            return  Response.status(400,"priority has to be >=1 and <=3").build();
        }
        if(todoItem.getStartDate()==null){
            return  Response.status(400,"startDate cannot be empty").build();
        }
        if(todoItem.getEndDate()==null){
            return  Response.status(400,"endDate cannot be empty").build();
        }
        if(todoItem.getEndDate().compareTo(todoItem.getStartDate())<0)
            return  Response.status(400,"endDate has to be >= startDate").build();
        return Response.status(200).build();

    }
    public Response addItem(TodoItem newItem){
        Response validTodoItem=isValidTodoItem(newItem);
        if(validTodoItem.getStatus()!=200){
            return validTodoItem;
        }
        boolean added = todoList.addItem(newItem);
        if (added) {
            System.out.println("Addition Operation Done");
            return Response.status(200,"Addition Operation Done").build();
        }
        else
            return Response.status(400,"Couldn't add the todo item. Please try another title!").build();

    }
    public Response deleteItem(String title){
        boolean deleted = todoList.deleteItem(title);
        if (deleted)
            return Response.status(200,"Delete Operation Done").build();
        else return Response.status(400,"Couldn't Delete the todo item. Please make sure this title exists!").build();
    }
    public Response getTopFiveByStartDate(){
        TodoItem[]topFive=todoList.topFiveAscendinglyByStartDate();
        return Response.ok(topFive).build();
    }
    public Response getTopFiveByEndDate(){
        TodoItem[]topFive=todoList.topFiveAscendinglyByEndDate();
        return Response.ok(topFive).build();
    }
}

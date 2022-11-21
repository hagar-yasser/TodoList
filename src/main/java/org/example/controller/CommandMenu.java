package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.example.DTO.TodoItem;
import org.example.repository.TodoList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
@Path("/")
public class CommandMenu {
    private TodoList todoList=new TodoList();
    private static final int highPriority=1;
    private static final int lowPriority=3;

    //empty constructor so that the jersey servlet can initiate the CommandMenu Object
    public CommandMenu(){}

    private TodoItem printTodoItemMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the title of todo item");
        String title = sc.nextLine();
        System.out.println("Enter the description of todo item");
        String description = sc.nextLine();
        System.out.println("Enter the category of todo item");
        String category = sc.nextLine();
        //try and catch on priority
        boolean missingPriority = true;
        int priority = -1;
        while (missingPriority) {
            System.out.println("Enter the priority of todo item \n"+
                    "1- HIGH PRIORITY\n"+
                    "2- MEDIUM PRIORITY\n"+
                    "3- LOW PRIORITY");

            try {
                priority = Integer.parseInt(sc.nextLine());
                if (priority < highPriority||priority>lowPriority)
                    throw new NumberFormatException();
                missingPriority = false;
            } catch (NumberFormatException e) {
                System.out.println("Try again. Enter a correct priority number");
            }
        }
        //try and catch startDate
        LocalDate startDate = null;
        boolean missingStartDate = true;
        while (missingStartDate) {
            System.out.println("Enter the start date of the todo item in the following format yyyy-mm-dd");
            try {
                startDate = LocalDate.parse(sc.nextLine());
                missingStartDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Wrong Start Date Format. Please enter the required start date format");
            }
        }
        //try and catch endDate
        LocalDate endDate = null;
        boolean missingEndDate = true;
        while (missingEndDate) {
            System.out.println("Enter the end date of the todo item in the following format yyyy-mm-dd");
            try {
                endDate = LocalDate.parse(sc.nextLine());
                if (endDate.compareTo(startDate) < 0) {
                    System.out.println("End Date cannot be before the start Date");
                } else
                    missingEndDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Wrong End Date Format. Please enter the required end date format");
            }
        }
        TodoItem todo = new TodoItem();
        todo.setTitle(title);
        todo.setCategory(category);
        todo.setDescription(description);
        todo.setPriority(priority);
        todo.setStartDate(startDate);
        todo.setEndDate(endDate);
        return todo;
    }
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todoItem")
    public Response printAddItemOptions(TodoItem newItem) {
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
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateTodoItem/{title}")
    public Response printUpdateItemOptions(@PathParam("title") String title,TodoItem updatedTodo){
        if( todoList.updateItem(title, updatedTodo)){
            return Response.status(200,"Updated successfully").build();
        }else {
            return Response.status(400,"Not updated").build();
        }



    }
    @DELETE
    @Path("/todoItem/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printDeleteItemOptions(@PathParam("title")String title) {
        boolean deleted = todoList.deleteItem(title);
        if (deleted)
            return Response.status(200,"Delete Operation Done").build();
        else return Response.status(400,"Couldn't Delete the todo item. Please make sure this title exists!").build();
    }

    @GET
    @Path("/showAllTodoItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printShowAllItemsOptions(){
        if(todoList.showAllItems()==null) {
            return Response.status(400,"There is no todo!").build();
        }else{
        return Response.ok(todoList.showAllItems()).build();
    }

    }
    @GET
    @Path("/topFiveItemsByStartDate")
    @Produces(MediaType.APPLICATION_JSON)
    public TodoItem[] printShowTopFiveByStartDate() {
        return todoList.topFiveAscendinglyByStartDate();
    }
    @GET
    @Path("/topFiveItemsByEndDate")
    @Produces(MediaType.APPLICATION_JSON)
    public TodoItem[] printShowTopFiveByEndDate() {
        return todoList.topFiveAscendinglyByEndDate();
    }

    @GET
    @Path("/searchByTitle/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByTitle(@PathParam("title")String title) {
        if(todoList.searchByTitle(title) == null){
            return Response.status(400,"Couldn't search in the todo item. Please make sure that this title exists!").build();
        }else{
            return Response.ok(todoList.searchByTitle(title)).build();
        }
    }

    @GET
    @Path("/searchByStartDate/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByStartDate(@PathParam("startDate")String startDateString) {
        LocalDate startDate=LocalDate.parse(startDateString);
        if (todoList.searchByStartDate(startDate) == null) {
           return Response.status(400,"Couldn't search in the todo item. Please make sure that this start date exists!").build();
        } else {
            return Response.ok(todoList.searchByStartDate(startDate)).build();
        }
    }

    @GET
    @Path("/searchByEndDate/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByEndDate(@PathParam("endDate")String endDateString) {
        LocalDate endDate=LocalDate.parse(endDateString);
        if (todoList.searchByEndDate(endDate) == null) {
            return Response.status(400,"Couldn't search in the todo item. Please make sure that this end date exists!").build();
        } else {
            return Response.ok(todoList.searchByEndDate(endDate)).build();
        }
    }

    @GET
    @Path("/searchPriority/{priority}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByPriority(@PathParam("priority")int priority) {
        if (todoList.searchByPriority(priority) == null) {
            return Response.status(400,"Couldn't search in the todo item. Please make sure that this priority exists!").build();
        } else {
            return Response.ok(todoList.searchByPriority(priority)).build();
        }
    }


    @PUT
    @Path("/updateCategory/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response printAddTodoItemToCategoryOptions( @PathParam("title") String title,String category){
        if(todoList.addTodoItemToCategory(title,category)){
            return Response.status(200,"Updated successfully").build();
        }else {
            return Response.status(400,"Not updated").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateIsFavourite/{title}")
    public Response printAddTodoItemToFavoriteOptions(@PathParam("title") String title){
        if(todoList.addTodoItemToFavorite(title)){
            return Response.status(200,"Updated successfully").build();
        }else {
            return Response.status(400,"Not updated").build();
        }

    }

}

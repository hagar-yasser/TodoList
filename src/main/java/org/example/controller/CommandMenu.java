package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.TodoItem;
import org.example.repository.TodoList;
import org.example.service.TodoListService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
@Path("/")
public class CommandMenu {
    //To be removed
    private TodoList todoList=new TodoList();
    private static final int highPriority=1;
    private static final int lowPriority=3;
    TodoListService todoListService=new TodoListService();

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todoItem")
    public Response printAddItemOptions(TodoItem newItem) {
        return todoListService.addItem(newItem);
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateTodoItem/{title}")
    public Response printUpdateItemOptions(@PathParam("title") String title,TodoItem updatedTodo){
        return todoListService.updateItem(title, updatedTodo);

    }
    @DELETE
    @Path("/todoItem/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printDeleteItemOptions(@PathParam("title")String title) {
        return todoListService.deleteItem(title);
    }

    @GET
    @Path("/showAllTodoItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printShowAllItemsOptions(){
        return  todoListService.showAllItems();
    }
    @GET
    @Path("/topFiveItemsByStartDate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printShowTopFiveByStartDate() {
        return todoListService.getTopFiveByStartDate();
    }
    @GET
    @Path("/topFiveItemsByEndDate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printShowTopFiveByEndDate() {

        return todoListService.getTopFiveByEndDate();
    }

    @GET
    @Path("/searchByTitle/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByTitle(@PathParam("title")String title) {

        return todoListService.searchByTitle(title);
    }

    @GET
    @Path("/searchByStartDate/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByStartDate(@PathParam("startDate")String startDateString) {
        LocalDate startDate=LocalDate.parse(startDateString);
        return todoListService.searchByStartDate(startDate);
    }

    @GET
    @Path("/searchByEndDate/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByEndDate(@PathParam("endDate")String endDateString) {
        LocalDate endDate=LocalDate.parse(endDateString);
        return todoListService.searchByEndDate(endDate);
    }

    @GET
    @Path("/searchPriority/{priority}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printTodoItemsByPriority(@PathParam("priority")int priority) {
        return todoListService.searchByPriority(priority);
    }


    @PUT
    @Path("/updateCategory/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response printAddTodoItemToCategoryOptions( @PathParam("title") String title,String category){
        return todoListService.addTodoItemToCategory(title,category);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateIsFavourite/{title}")
    public Response printAddTodoItemToFavoriteOptions(@PathParam("title") String title){
        return  todoListService.addTodoItemToFavorite(title);
    }

}


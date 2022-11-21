package org.example.service;

import jakarta.ws.rs.core.Response;
import org.example.dto.TodoItem;

import java.time.LocalDate;

public interface MyService {
    Response addItem(TodoItem newItem);
    Response deleteItem(String title);
    Response getTopFiveByStartDate();
    Response getTopFiveByEndDate();
    Response searchByTitle(String title);
    Response searchByStartDate(LocalDate startDate);
    Response searchByEndDate(LocalDate endDate);
    Response searchByPriority(int priority);
    Response updateItem(String title, TodoItem updatedTodo);
}

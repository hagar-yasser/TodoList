package org.example.service;

import jakarta.ws.rs.core.Response;
import org.example.dto.TodoItem;

public interface MyService {
    Response addItem(TodoItem newItem);
    Response deleteItem(String title);
    Response getTopFiveByStartDate();
    Response getTopFiveByEndDate();
    Response searchByTitle(String title);
}

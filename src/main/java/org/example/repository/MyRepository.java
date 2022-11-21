package org.example.repository;

import org.example.dto.TodoItem;

public interface MyRepository {
    boolean addItem(TodoItem todoItem) throws Exception;
    boolean deleteItem(String title) throws Exception;
    TodoItem[]topFiveAscendinglyByStartDate() throws Exception;
    TodoItem[]topFiveAscendinglyByEndDate() throws Exception;
    TodoItem searchByTitle(String title) throws Exception;
}

package org.example.repository;

import org.example.dto.TodoItem;

import java.time.LocalDate;

public interface MyRepository {
    boolean addItem(TodoItem todoItem) throws Exception;
    boolean deleteItem(String title) throws Exception;
    TodoItem[]topFiveAscendinglyByStartDate() throws Exception;
    TodoItem[]topFiveAscendinglyByEndDate() throws Exception;
    TodoItem searchByTitle(String title) throws Exception;
    TodoItem[] searchByStartDate(LocalDate startDate) throws Exception;
    TodoItem[] searchByEndDate(LocalDate endDate) throws Exception;
    TodoItem[] searchByPriority(int priority) throws Exception;
    boolean updateItem(String title, TodoItem updatedTodoItem) throws Exception;
    TodoItem[] showAllItems() throws Exception;
    boolean addTodoItemToCategory(String title, String category) throws Exception;
    boolean addTodoItemToFavorite(String title) throws Exception;
}

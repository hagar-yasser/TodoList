package DTO;

import java.io.Serializable;
import java.time.LocalDate;

public class TodoItem implements Serializable {
    private String title;
    private String description;
    private String category;
    private int priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean favourite=false;
    public static String tableName = "TodoItem";
    public static String titleColumnName = "Title";
    public static String startDateColumnName = "StartDate";
    public static String endDateColumnName = "EndDate";
    public static String descriptionColumnName="Description";
    public static String categoryColumnName="Category";
    public static String priorityColumnName="Priority";
    public static String isFavoriteColumnName="IsFavorite";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", priority=" + priority +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", favourite=" + favourite +
                '}';
    }
}

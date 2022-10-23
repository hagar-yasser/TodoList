import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class TodoList implements Serializable {
    private int currentSizeOfTodoItemsList = 100;
    private TodoItem[] todoItemsList = new TodoItem[currentSizeOfTodoItemsList];
    private int indexOfLastItemInList = -1;

    public boolean addItem(TodoItem todoItem) {
        //Check if title of new item already exists in array
        if (getIndexOfTodoItemWithTitle(todoItem.getTitle()) == -1) {
            //If the array is full duplicate its size
            if (indexOfLastItemInList == currentSizeOfTodoItemsList - 1) {
                duplicateArraySize();
            }
            todoItemsList[indexOfLastItemInList + 1] = todoItem;
            indexOfLastItemInList++;
            return true;
        }
        return false;
    }

    public boolean deleteItem(String title) {
        int indexOfItemWithTitle = getIndexOfTodoItemWithTitle(title);
        if (indexOfItemWithTitle == -1)
            return false;
        todoItemsList[indexOfItemWithTitle] = null;
        //Shift items to the right of the deleted item to the left one step
        for (int i = indexOfItemWithTitle + 1; i <= indexOfLastItemInList; i++) {
            todoItemsList[i - 1] = todoItemsList[i];
        }
        todoItemsList[indexOfLastItemInList]=null;
        indexOfLastItemInList -= 1;
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

    public TodoItem[] sortAscendinglyByStartDate() {

        Arrays.sort(todoItemsList, new Comparator<TodoItem>() {
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        //if both are null or having the same reference then they are equal
                        if (o1 == o2)
                            return 0;
                        //Move nulls to the right make them largest when compared to any other object
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        //compare start dates
                        return o1.getStartDate().compareTo(o2.getStartDate());

                    }
                }
        );

        return todoItemsList;
    }

    public TodoItem[] sortAscendinglyByEndDate() {
        Arrays.sort(todoItemsList, new Comparator<TodoItem>() {
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        //if both are null or having the same reference then they are equal
                        if (o1 == o2)
                            return 0;
                        //Move nulls to the right make them largest when compared to any other object
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        //compare start dates
                        return o1.getEndDate().compareTo(o2.getEndDate());

                    }
                }
        );

        return todoItemsList;
    }

    public TodoItem searchByTitle(String title) {
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getTitle().equals(title))
                return todoItemsList[i];
        }
        return null;
    }

    public TodoItem[] searchByStartDate(LocalDate startDate) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getStartDate().compareTo(startDate) == 0) {
                sizeOfListOfResult++;
            }
        }
        if(sizeOfListOfResult == 0){
            return null;
        }else{
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getStartDate().compareTo(startDate) == 0) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }

    public TodoItem[] searchByEndDate(LocalDate endDate) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getEndDate().compareTo(endDate) == 0) {
                sizeOfListOfResult++;
            }
        }
        if(sizeOfListOfResult == 0){
            return null;
        }else{
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getEndDate().compareTo(endDate) == 0) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }

    public TodoItem[] searchByPriority(int priority) {
        int sizeOfListOfResult = 0;
        for (int i = 0; i <= indexOfLastItemInList; i++) {
            if (todoItemsList[i].getPriority() == priority) {
                sizeOfListOfResult++;
            }
        }
        if(sizeOfListOfResult == 0){
            return null;
        }else{
            TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
            int indexOfListOfResult = 0;
            for (int j = 0; j <= indexOfLastItemInList; j++) {
                if (todoItemsList[j].getPriority() == priority) {
                    listOfResult[indexOfListOfResult] = todoItemsList[j];
                    indexOfListOfResult++;
                }
            }
            return listOfResult;
        }
    }


    public void updateItem(String title, TodoItem updatedTodoItem){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == indexOfTodoItemWithTitle ||
                getIndexOfTodoItemWithTitle(updatedTodoItem.getTitle()) == -1){
            todoItemsList[indexOfTodoItemWithTitle].setTitle(updatedTodoItem.getTitle());
            todoItemsList[indexOfTodoItemWithTitle].setDescription(updatedTodoItem.getDescription());
            todoItemsList[indexOfTodoItemWithTitle].setPriority(updatedTodoItem.getPriority());
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(updatedTodoItem.getFavourite());
            todoItemsList[indexOfTodoItemWithTitle].setCategory(updatedTodoItem.getCategory());
            todoItemsList[indexOfTodoItemWithTitle].setStartDate(updatedTodoItem.getStartDate());
            todoItemsList[indexOfTodoItemWithTitle].setEndDate(updatedTodoItem.getEndDate());
            System.out.println("Mission is completed successfully");
        }
        else{
            System.out.println("Mission is failed\nThe updated title is already exist");
        }
    }
    public TodoItem[] showAllItems() {
        if(indexOfLastItemInList==-1)
            return null;

        TodoItem[] actualListOfTodoItemsWithoutNulls = new TodoItem[indexOfLastItemInList+1];
        for(int counter=0; counter<=indexOfLastItemInList;counter++)
            actualListOfTodoItemsWithoutNulls[counter] = todoItemsList[counter];
        return actualListOfTodoItemsWithoutNulls;
    }
    public void addTodoItemToCategory( String title, String category){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(indexOfTodoItemWithTitle==-1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setCategory(category);
            System.out.println("Mission is completed successfully ");
        }
    }

    public void addTodoItemToFavorite(String title){
        int indexOfTodoItemWithTitle= getIndexOfTodoItemWithTitle(title);
        if(indexOfTodoItemWithTitle==-1)
            System.out.println("The todo with this title is not found\nThe mission is failed!!!!");
        else {
            todoItemsList[indexOfTodoItemWithTitle].setFavourite(true);
            System.out.println("Mission is completed successfully ");
        }
    }
}


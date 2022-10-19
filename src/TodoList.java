import java.time.LocalDate;

public class TodoList {
    private int currentSizeOfTodoItemsList=100;
    private TodoItem[]todoItemsList=new TodoItem[currentSizeOfTodoItemsList];
    private int indexOfLastItemInList=-1;
    public void addItem(TodoItem todoItem){
        //Check if title of new item already exists in array
        if(getIndexOfTodoItemWithTitle(todoItem.getTitle())==-1) {
            //If the array is full duplicate its size
            if (indexOfLastItemInList == currentSizeOfTodoItemsList - 1) {
                duplicateArraySize();
            }
            todoItemsList[indexOfLastItemInList + 1] = todoItem;
            indexOfLastItemInList++;
        }
    }
    public void deleteItem(String title){
        int indexOfItemWithTitle=getIndexOfTodoItemWithTitle(title);
        if(indexOfItemWithTitle==-1)
            return;

        //Shift items to the right of the deleted item to the left one step
        for (int i = indexOfItemWithTitle+1; i <=indexOfLastItemInList ; i++) {
            todoItemsList[i-1]=todoItemsList[i];
        }
        indexOfLastItemInList-=1;

    }
    public int getIndexOfTodoItemWithTitle(String title){
        for (int i = 0; i <=indexOfLastItemInList ; i++) {
            if(todoItemsList[i].getTitle().equals(title))
                return i;
        }
        //element doesn't exist
        return -1;
    }
    public void duplicateArraySize(){
        TodoItem[]duplicateSizeTodoItemsList=new TodoItem[currentSizeOfTodoItemsList*2];
        for (int i=0;i<=indexOfLastItemInList;i++) {
            duplicateSizeTodoItemsList[i]=todoItemsList[i];
        }
        currentSizeOfTodoItemsList*=2;
        todoItemsList=duplicateSizeTodoItemsList;
    }
    public TodoItem searchByTitle(String title){
        for (int i = 0; i <=indexOfLastItemInList ; i++) {
            if(todoItemsList[i].getTitle().equals(title))
                return todoItemsList[i];
        }
        return null;
    }
    public TodoItem[] searchByStartDate(LocalDate startDate){
        int sizeOfListOfResult = 0;
        for (int i = 0; i <=indexOfLastItemInList ; i++) {
            if(todoItemsList[i].getStartDate().compareTo(startDate) == 0){
                sizeOfListOfResult ++;
            }
        }
        TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
        int indexOfListOfResult = 0;
        for (int j = 0; j <=indexOfLastItemInList ; j++) {
            if(todoItemsList[j].getStartDate().compareTo(startDate) == 0){
                listOfResult[indexOfListOfResult] = todoItemsList[j];
                indexOfListOfResult ++;
            }
        }
        return listOfResult;
    }
    public TodoItem[] searchByEnDate(LocalDate endDate){
        int sizeOfListOfResult = 0;
        for (int i = 0; i <=indexOfLastItemInList ; i++) {
            if(todoItemsList[i].getEndDate().compareTo(endDate) == 0){
                sizeOfListOfResult ++;
            }
        }
        TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
        int indexOfListOfResult = 0;
        for (int j = 0; j <=indexOfLastItemInList ; j++) {
            if(todoItemsList[j].getEndDate().compareTo(endDate) == 0){
                listOfResult[indexOfListOfResult] = todoItemsList[j];
                indexOfListOfResult ++;
            }
        }
        return listOfResult;
    }
    public TodoItem[] searchByPriority(int priority){
        int sizeOfListOfResult = 0;
        for (int i = 0; i <=indexOfLastItemInList ; i++) {
            if(todoItemsList[i].getPriority() == priority){
                sizeOfListOfResult ++;
            }
        }
        TodoItem[] listOfResult = new TodoItem[sizeOfListOfResult];
        int indexOfListOfResult = 0;
        for (int j = 0; j <=indexOfLastItemInList ; j++) {
            if(todoItemsList[j].getPriority() == priority){
                listOfResult[indexOfListOfResult] = todoItemsList[j];
                indexOfListOfResult ++;
            }
        }
        return listOfResult;
    }
}

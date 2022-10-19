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
}

# TodoList
## How to run the task
* Write the next commands in terminal 
  ```
  git clone -b Dev https://github.com/hagar-yasser/TodoList.git

  ```
* Open Workbench then 
  * Connect to server localhost:3306
  * From 'File' choose 'Open SQL Script' then choose 'TodoList-DB.sql' from 'TodoList' project
  * Type this query
     ```
     
      USE TodoList;
     
     ```
  
* Open TodoList folder in Intellij Idea
* Write in terminal 
  ```
    mvn clean install
 
  ```
* Open tomcate server version 10 
* Deploy war file that you will find in todoList folder in tomcat 



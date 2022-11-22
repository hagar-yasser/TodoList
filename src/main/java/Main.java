import org.example.config.ProjectConfig;
import org.example.dto.TodoItem;
import org.example.service.MyService;
import org.example.service.TodoListService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    private String username;
    public static void main(String[] args){
        ApplicationContext applicationContext=
                new AnnotationConfigApplicationContext(ProjectConfig.class);
        MyService service=applicationContext.getBean(TodoListService.class);
//        System.out.println(service.addItem(new TodoItem("f","fdksf",null,1, LocalDate.parse("2020-02-02"),LocalDate.parse("2020-02-02"),false)).getStatus());
        System.out.println(Arrays.toString((TodoItem[])service.searchByPriority(1).getEntity()));
        System.out.println(Arrays.toString((TodoItem[])service.showAllItems().getEntity()));

    }
}

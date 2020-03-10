package service;



import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import models.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TasksService {
    public List<Project> pullTasks(Tasks tasksAppService){
        if(tasksAppService == null){
            return new ArrayList<>();
        }

        TaskLists result = null;
        try {
            result = tasksAppService.tasklists().list()
                    .setMaxResults(10L)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<TaskList> taskLists = result.getItems();
        if (taskLists == null || taskLists.isEmpty()) {
            System.out.println("No task lists found.");
        } else {
            System.out.println("Task lists:");
            for (TaskList tasklist : taskLists) {
                System.out.printf("%s (%s)\n", tasklist.getTitle(), tasklist.getId());
            }
        }
        return new ArrayList<>();
    }



    public List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3*60));
        result.add(new Project("Leet code", 0, 3*60));
        result.add(new Project("Call HDFC", 0, 1*60));
        return result;
    }
}

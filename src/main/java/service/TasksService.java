package service;


import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import models.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TasksService {
    public List<Project> pullTasks(Tasks tasksAppService) {
        if (tasksAppService == null) {
            return new ArrayList<>();
        }

        com.google.api.services.tasks.model.Tasks result = null;
        try {
            result = tasksAppService.tasks().list("@default")
                    .setShowCompleted(false)
                    .setShowDeleted(false)
                    .setShowHidden(false)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Task> tasks = result.getItems();
        return convertGoogleTasksToProjects(tasks);
    }

    private List<Project> convertGoogleTasksToProjects(List<Task> tasks) {
        List<Project> projects = new ArrayList<>();
        if (tasks == null) {
            return new ArrayList<>();
        }
        for (Task task : tasks) {
            if(task.getNotes() != null){
                String note = task.getNotes();
                final String[] split = note.split("\n", 2);
                int completed = Integer.parseInt(split[0].split("=",2)[1]);
                int remaining = Integer.parseInt(split[1].split("=",2)[1]);
                projects.add(new Project(task.getTitle(), completed, remaining*60));
            }

        }
        return projects;
    }

}

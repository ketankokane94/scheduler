package Controller;

import java.io.IOException;

import com.google.api.services.calendar.Calendar;
import models.Project;
import models.Task;
import java.security.GeneralSecurityException;

import service.ConnectionService;
import service.GetProjectService;
import service.GetTaskService;
import service.SchedulerService;

import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        ConnectionService connectionService = new ConnectionService();
        GetTaskService taskService = new GetTaskService();
        GetProjectService projectService = new GetProjectService();

        Calendar calendar = connectionService.getCalendar();

        final List<Task> tasks = taskService.pullTasks(calendar);
        final List<Project> projects = projectService.getProjects();


        final List<Task> eventList = new SchedulerService().run(tasks,projects, true);
        taskService.pushTasks(eventList, calendar);
    }

}

package java.Controller;

import test.java.models.models.Project;
import test.java.models.models.Task;
import service.GetProjectService;
import service.GetTaskService;
import service.PutTaskInCalendarService;
import service.SchedulerService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final List<Task> tasks = new GetTaskService().pullFromCalendar();
        List<Project> projects = new GetProjectService().getProjects();
        final List<Task> eventList = new SchedulerService().run(tasks,projects, true);
        new PutTaskInCalendarService().pushToCalendar(eventList);


    }

}

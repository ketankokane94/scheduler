package java.Controller;

import java.io.IOException;
import java.models.Project;
import java.models.Task;
import java.security.GeneralSecurityException;
import java.service.GetProjectService;
import java.service.GetTaskService;
import java.service.PutTaskInCalendarService;
import java.service.SchedulerService;
import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final List<Task> tasks = new GetTaskService().pullFromCalendar();
        List<Project> projects = new GetProjectService().getProjects();
        final List<Task> eventList = new SchedulerService().run(tasks,projects, true);
        new PutTaskInCalendarService().pushToCalendar(eventList);


    }

}

package Controller;

import java.io.IOException;
import models.Project;
import models.Task;
import java.security.GeneralSecurityException;
import service.GetProjectService;
import service.GetTaskService;
import service.PutTaskInCalendarService;
import service.SchedulerService;
import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // TODO: puth them in threads
        final List<Task> tasks = new GetTaskService().pullFromCalendar();
        List<Project> projects = new GetProjectService().getProjects();
//        final List<Task> eventList = new SchedulerService().run(tasks,projects, true);
//        new PutTaskInCalendarService().pushToCalendar(eventList);


    }

}

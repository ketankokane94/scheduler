package Controller;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.tasks.Tasks;
import models.Interval;
import models.Project;
import org.junit.Test;
import service.ConnectionService;
import service.TasksService;
import service.CalendarService;
import service.SchedulerService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SchedulerControllerTest {

    @Test
    public void main() throws GeneralSecurityException, IOException {
        ConnectionService connectionService = new ConnectionService();
        CalendarService taskService = new CalendarService();
        TasksService projectService = new TasksService();

        Calendar calendarApp = connectionService.getCalendarApp();
        Tasks tasksApp = connectionService.getTaskApp();

        final List<Interval> intervals = taskService.pullTasks(calendarApp);
        final List<Project> projects = projectService.pullTasks(tasksApp);


        final List<Interval> eventList = new SchedulerService().run(intervals,projects, true);
        taskService.pushTasks(eventList, calendarApp);
}
}
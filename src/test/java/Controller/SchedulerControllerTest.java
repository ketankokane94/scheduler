package Controller;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.tasks.Tasks;
import models.Interval;
import models.Project;
import org.junit.Test;
import service.ConnectionService;
import service.CalendarService;
import service.TasksService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SchedulerControllerTest {

    @Test
    public void mainTest() {
        ConnectionService connectionService = new ConnectionService();
        CalendarService taskService = new CalendarService();
        TasksService projectService = new TasksService();
//
        Calendar calendarApp = null;
        try {
            calendarApp = connectionService.getCalendarApp();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tasks tasksApp = null;
        try {
            tasksApp = connectionService.getTaskApp();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        final List<Interval> intervals = taskService.pullIntervals(calendarApp);
//        System.out.println(intervals);
        final List<Project> projects = projectService.pullTasks(tasksApp);
        System.out.println(projects);
        //
//        final List<Interval> eventList = new SchedulerService().run(intervals,projects, true);
//        taskService.pushTasks(eventList, calendarApp);
    }
}
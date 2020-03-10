package Controller;

import java.io.IOException;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.tasks.Tasks;
import models.Interval;
import models.Project;

import java.security.GeneralSecurityException;

import service.ConnectionService;
import service.TasksService;
import service.CalendarService;
import service.SchedulerService;

import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        ConnectionService connectionService = new ConnectionService();
        CalendarService calendarService = new CalendarService();
        TasksService tasksService = new TasksService();

        Calendar calendarApp = connectionService.getCalendarApp();
        Tasks tasksApp = connectionService.getTaskApp();

        final List<Interval> intervals = calendarService.pullIntervals(calendarApp);
        final List<Project> projects = tasksService.pullTasks(tasksApp);


        final List<Interval> eventList = new SchedulerService().run(intervals, projects, true);
        System.out.println(eventList);
        calendarService.pushTasks(eventList, calendarApp);
    }

}

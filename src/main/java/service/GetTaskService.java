package service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import models.Constant;
import org.joda.time.DateTime;

import java.io.IOException;

import models.Task;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GetTaskService {

    public List<Task> pullTasks(Calendar calendarService) {

        Events events = null;
        try {
            events = calendarService.events().list(Constant.CALENDAR_TO_ACCESS_PRIMARY).
                    setTimeMin(Constant.TOMORROW())
                    .setTimeMax(Constant.TOMORROWPLUSONE())
                    .setOrderBy(Constant.START_TIME)
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Event> items = events.getItems();
        return convertGoogleCalendarEventsToTasks(items);
    }


    private Calendar getCalendar() {
        ConnectionService connectionService = new ConnectionService();
        Calendar calendarService = null;
        try {
            calendarService = connectionService.getCalendar();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calendarService;
    }


    private List<Task> convertGoogleCalendarEventsToTasks(List<Event> items) {
        List<Task> tasks = new ArrayList<>();
        for (Event event : items) {
            Task task = new Task(event.getSummary(),
                    Constant.convertToDateTime(event.getStart()),
                    Constant.convertToDateTime(event.getEnd()));
            tasks.add(task);
        }
        return tasks;
    }


    public void pushTasks(List<Task> intervals, Calendar calendarService) {

        for (Task task : intervals) {
            if (task.getSummary().equals("wake up") || task.getSummary().equals("Sleep")) {
                continue;
            }

            Event event = new Event()
                    .setSummary(task.getSummary());

            EventDateTime start = new EventDateTime()
                    .setDateTime(Constant.convertToGoogleDateTime(task.getStart()));
            event.setStart(start);


            EventDateTime end = new EventDateTime()
                    .setDateTime(Constant.convertToGoogleDateTime(task.getEnd()));
            event.setEnd(end);

            try {
                event = calendarService.events().insert(Constant.CALENDAR_TO_ACCESS_PRIMARY, event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("test.models.models.Event created: %s\n", event.getHtmlLink());
        }
    }

    public List<Task> getTask() {
        String today_date = "2019-07-20T";
        List<Task> tasks = new LinkedList<>();
        tasks.add(new Task("Sleep",
                DateTime.parse(today_date + "00:00:00"),
                DateTime.parse(today_date + "09:40:00")));

        tasks.add(new Task("Lunch", DateTime.parse(today_date + "13:00:00"),
                DateTime.parse(today_date + "13:45:00")));

        tasks.add(new Task("Dinner", DateTime.parse(today_date + "20:30:00"),
                DateTime.parse(today_date + "21:00:00")));

        tasks.add(new Task("Gym", DateTime.parse(today_date + "16:30:00"),
                DateTime.parse(today_date + "17:00:00")));


        return tasks;
    }

}

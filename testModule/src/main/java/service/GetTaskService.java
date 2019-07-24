package service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import models.Constant;
import models.Task;
import org.joda.time.DateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GetTaskService {
    public List<Task> pullFromCalendar() {
        ConnectionService c = new ConnectionService();
        Calendar service = null;
        try {
            service = c.getCalendar();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Events events = null;
        try {
            events = service.events().list("primary").
                    setTimeMin(Constant.TOMORROW())
                    .setTimeMax(Constant.TOMORROWPLUSONE())
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Event> items = events.getItems();
        List<Task> tasks = new ArrayList<>();
        for (Event event : items) {
            System.out.println(event.getSummary() + event.getStart() + event.getEnd());
            Task task = new Task(event.getSummary(),
                    Constant.convertToDateTime(event.getStart()),
                    Constant.convertToDateTime(event.getEnd()));

            tasks.add(task);
        }
        return tasks;
    }

    public static String today_date = "2019-07-20T";



    public List<Task> getTask() {
        List<Task> tasks = new LinkedList<>();
//        tasks.add(new Task("wake up",
//                DateTime.parse(today_date + "00:00:00"),
//                DateTime.parse(today_date + "09:40:00")));
//
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

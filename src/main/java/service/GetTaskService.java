package service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import models.Constant;
import models.Interval;
import org.joda.time.DateTime;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GetTaskService {

    public List<Interval> pullTasks(Calendar calendarService) {

        if (calendarService == null) {
            return new ArrayList<>();
        }

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


    private List<Interval> convertGoogleCalendarEventsToTasks(List<Event> items) {
        List<Interval> intervals = new ArrayList<>();
        for (Event event : items) {
            Interval interval = new Interval(event.getSummary(),
                    Constant.convertToDateTime(event.getStart()),
                    Constant.convertToDateTime(event.getEnd()));
            intervals.add(interval);
        }
        return intervals;
    }


    public void pushTasks(List<Interval> intervals, Calendar calendarService) {

        for (Interval interval : intervals) {
            if (interval.getSummary().equals("wake up") || interval.getSummary().equals("Sleep")) {
                continue;
            }

            Event event = new Event()
                    .setSummary(interval.getSummary());

            EventDateTime start = new EventDateTime()
                    .setDateTime(Constant.convertToGoogleDateTime(interval.getStart()));
            event.setStart(start);


            EventDateTime end = new EventDateTime()
                    .setDateTime(Constant.convertToGoogleDateTime(interval.getEnd()));
            event.setEnd(end);

            try {
                event = calendarService.events().insert(Constant.CALENDAR_TO_ACCESS_PRIMARY, event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("test.models.models.Event created: %s\n", event.getHtmlLink());
        }
    }

    public List<Interval> getTask() {
        String today_date = "2019-07-20T";
        List<Interval> intervals = new LinkedList<>();
        intervals.add(new Interval("Sleep",
                DateTime.parse(today_date + "00:00:00"),
                DateTime.parse(today_date + "09:40:00")));

        intervals.add(new Interval("Lunch", DateTime.parse(today_date + "13:00:00"),
                DateTime.parse(today_date + "13:45:00")));

        intervals.add(new Interval("Dinner", DateTime.parse(today_date + "20:30:00"),
                DateTime.parse(today_date + "21:00:00")));

        intervals.add(new Interval("Gym", DateTime.parse(today_date + "16:30:00"),
                DateTime.parse(today_date + "17:00:00")));


        return intervals;
    }

}

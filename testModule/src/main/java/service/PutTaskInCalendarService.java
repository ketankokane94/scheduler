package service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import models.Constant;
import models.Task;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class PutTaskInCalendarService {

    public void pushToCalendar(List<Task> intervals) {
        Calendar service = null;
        try {
            service = new ConnectionService().getCalendar();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ensure the authenticated user has write access to the calendar with
        // the calendarId you provided (for example by calling calendarList.get()
        // for the calendarId and checking the accessRole).

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

            String calendarId = "primary";
            try {
                event = service.events().insert(calendarId, event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("models.Event created: %s\n", event.getHtmlLink());
        }
    }
}

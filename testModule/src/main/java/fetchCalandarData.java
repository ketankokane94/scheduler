import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import models.Constant;
import models.Task;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class fetchCalandarData {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        CalendarQuickstart c = new CalendarQuickstart();
        Calendar service = c.getCalendar();
        final DateTime now = new DateTime(System.currentTimeMillis());
        final Events events = service.events().list("primary").
                setTimeMin(now)
                //.setTimeMax()
                .execute();
        List<Event> items = events.getItems();
        List<Task> tasks = new ArrayList<>();
        for (Event event : items){
            Task task = new Task(event.getSummary(),
                    Constant.convertToDateTime(event.getStart()),
                    Constant.convertToDateTime(event.getEnd()));
        tasks.add(task);
        }

    }
}

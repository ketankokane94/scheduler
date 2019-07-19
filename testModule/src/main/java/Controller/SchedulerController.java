package Controller;

import models.Task;
import service.SchedulerService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {

        final List<Task> intervalList = new SchedulerService().run(true);
        //new CalendarQuickstart().pushToGoogleCalandar(intervalList);
    }

}

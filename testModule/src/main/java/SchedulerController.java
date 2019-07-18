import models.Interval;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SchedulerController {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final List<Interval> intervalList = new Scheduler().run(true);
        //new CalendarQuickstart().pushToGoogleCalandar(intervalList);
    }

}

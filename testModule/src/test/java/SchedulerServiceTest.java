import models.Task;
import org.joda.time.DateTime;
import org.junit.Test;
import service.SchedulerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchedulerServiceTest {

    @Test
    public void getIntervalsToSchedule() {

        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime later = new DateTime(System.currentTimeMillis() + 10000000);
        Task early = new Task("tes", now, later);
        Task late = new Task("tes", later, now);
        List<Task> tasks = new ArrayList<>();
        tasks.add(late);
        tasks.add(early);
        Collections.sort(tasks);
        SchedulerService.getIntervalsToSchedule(tasks);
    }

    @Test
    public void run() {
        SchedulerService schedulerService = new SchedulerService();
        schedulerService.run(null,null,true);
    }
}
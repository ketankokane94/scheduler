import models.Task;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SchedulerTest {

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
        Scheduler.getIntervalsToSchedule(tasks);
    }

    @Test
    public void run() {
        Scheduler scheduler = new Scheduler();
        scheduler.run(true);
    }
}
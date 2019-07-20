package models;


import org.joda.time.DateTime;
import org.joda.time.*;

import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskTest {

    DateTime now = DateTime.parse("2019-07-19T09:40:00");
    DateTime later = DateTime.parse("2019-07-19T10:40:00");


    @Test
    public void getTimeIntervalBetweenStartAndEnd() {
        Assert.assertEquals(60, Minutes.minutesBetween(now,later).getMinutes());
    }

    @Test
    public void checkIfSorts() {
        Task early = new Task("tes", now, later);
        Task late = new Task("tes", later, now);
        List<Task> tasks = new ArrayList<>();
        tasks.add(late);
        tasks.add(early);
        Collections.sort(tasks);

        Assert.assertEquals(tasks.get(0), early);
        Assert.assertEquals(tasks.get(1), late);
    }

    @Test
    public void compareTo() {
        Task early = new Task("tes", now, later);
        Task late = new Task("tes", later, now);
        // both same
        Assert.assertEquals(0, early.compareTo(early));
        // start is early is less than late
        Assert.assertEquals(-1, early.compareTo(late));
        // star of late if greater than early
        Assert.assertEquals(1, late.compareTo(early));

    }
}
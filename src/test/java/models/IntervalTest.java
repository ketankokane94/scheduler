package models;


import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalTest {

    DateTime now = DateTime.parse("2019-07-19T09:40:00");
    DateTime later = DateTime.parse("2019-07-19T10:40:00");


    @Test
    public void getTimeIntervalBetweenStartAndEnd() {
        Assert.assertEquals(60, Minutes.minutesBetween(now,later).getMinutes());
    }

    @Test
    public void checkIfSorts() {
        Interval early = new Interval("tes", now, later);
        Interval late = new Interval("tes", later, now);
        List<Interval> intervals = new ArrayList<>();
        intervals.add(late);
        intervals.add(early);
        Collections.sort(intervals);

        Assert.assertEquals(intervals.get(0), early);
        Assert.assertEquals(intervals.get(1), late);
    }

    @Test
    public void compareTo() {
        Interval early = new Interval("tes", now, later);
        Interval late = new Interval("tes", later, now);
        // both same
        Assert.assertEquals(0, early.compareTo(early));
        // start is early is less than late
        Assert.assertEquals(-1, early.compareTo(late));
        // star of late if greater than early
        Assert.assertEquals(1, late.compareTo(early));

    }
}
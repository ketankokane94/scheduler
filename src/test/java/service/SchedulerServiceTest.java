package service;

import models.Constant;
import models.Interval;
import models.Project;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SchedulerServiceTest {

    @Test
    public void getIntervalsToSchedule() {

        DateTime JanFirst2PM = new DateTime(2020, 01, 01, 14, 0);
        DateTime JanFirst2_50PM = new DateTime(2020, 01, 01, 14, 50);

        DateTime JanFirst5PM = new DateTime(2020, 01, 01, 17, 0);
        DateTime JanFirst5_50PM = new DateTime(2020, 01, 01, 17, 50);

        Interval Gym = new Interval("Gym", JanFirst2PM, JanFirst2_50PM);
        Interval Cooking = new Interval("Cooking", JanFirst5PM, JanFirst5_50PM);

        List<Interval> intervals = new ArrayList<>();
        intervals.add(Gym);
        intervals.add(Cooking);

        final List<Interval> freeIntervals = SchedulerService.getFreeIntervals(intervals);
        Assert.assertNotNull(freeIntervals);
        Assert.assertEquals(1, freeIntervals.size());
        Interval interval = freeIntervals.get(0);
        Assert.assertEquals(110, interval.getDuration());
        // 10 minutes break
        Assert.assertEquals(JanFirst2_50PM.minusMinutes(-10), interval.getStart());
        Assert.assertEquals(Constant.FREE_INTERVAL_NAME, freeIntervals.get(0).getSummary());

    }

    @Test
    public void SplitIntervalsShouldNotSplitIntervalWhenDurationOfAnIntervalIsLessThanMaxAllowedDuration() {

        DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);

        DateTime JanFirst4_50PM = JanFirst4PM.plusMinutes(50); // make an interval of 50 minutes.

        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, JanFirst4_50PM);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        Assert.assertNotNull(freeIntervals);
        final List<Interval> intervals = SchedulerService.splitIntervals(freeIntervals);
        Assert.assertNotNull(intervals);
        Assert.assertEquals(1, intervals.size());

    }

    @Test
    public void SplitIntervalsShouldSplitIntervalWhenDurationOfAnIntervalIsMoreThanMaxAllowedDuration() {

        DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval + Constant.min_interval);

        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        Assert.assertNotNull(freeIntervals);
        final List<Interval> intervals = SchedulerService.splitIntervals(freeIntervals);
        Assert.assertNotNull(intervals);
        Assert.assertEquals(2, intervals.size());

    }

    @Test
    public void ScheduleShoyldReturnEmptyListWhenThereAreNoProjects() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval + Constant.min_interval);
        final List<Project> projects  = new ArrayList<>();
        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        final List<Interval> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
    }

    @Test
    public void ScheduleShoyldReturnEmptyListWhenThereAreNoFreeIntervals() {
        final List<Project> projects  = new ArrayList<>();
        final List<Interval> freeIntervals = new ArrayList<>();
        final List<Interval> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
    }

    @Test
    public void ScheduleShouldReturnAssignedIntervalWithProjectWhenTaskDurationIsLessThanProjectRemaininTime() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.min_interval);

        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);

        final List<Project> projects  = new ArrayList<>();
        Project project = new Project("Read on OOP", 0, 16 * 60);
        projects.add(project);


        final List<Interval> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
        Assert.assertEquals(1, schedule.size());
        Assert.assertEquals(1, projects.size());
        Assert.assertEquals(0, freeIntervals.size());
    }

    @Test
    public void ScheduleShouldReturnAssignedIntervalWithProjectWhenTaskDurationIsMoreThanProjectRemainingTime() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval);

        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);

        final List<Project> projects  = new ArrayList<>();
        Project project = new Project("Read on OOP", 0, Constant.min_interval);
        projects.add(project);

        final List<Interval> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
        Assert.assertEquals(1, schedule.size());
        Assert.assertEquals(0, projects.size());

    }

    @Test
    public void run() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval);

        Interval freeInterval = new Interval(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);

        final List<Project> projects  = new ArrayList<>();
        Project project = new Project("Read on OOP", 0, Constant.min_interval);
        projects.add(project);

        SchedulerService schedulerService = new SchedulerService();
        final List<Interval> intervalList = schedulerService.run(freeIntervals, projects, true);
        Assert.assertNotNull(intervalList);

    }
}
package service;

import models.Constant;
import models.Project;
import models.Task;
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

        Task Gym = new Task("Gym", JanFirst2PM, JanFirst2_50PM);
        Task Cooking = new Task("Cooking", JanFirst5PM, JanFirst5_50PM);

        List<Task> tasks = new ArrayList<>();
        tasks.add(Gym);
        tasks.add(Cooking);

        final List<Task> freeIntervals = SchedulerService.getFreeIntervals(tasks);
        Assert.assertNotNull(freeIntervals);
        Assert.assertEquals(1, freeIntervals.size());
        Task task = freeIntervals.get(0);
        Assert.assertEquals(110, task.getDuration());
        // 10 minutes break
        Assert.assertEquals(JanFirst2_50PM.minusMinutes(-10), task.getStart());
        Assert.assertEquals(Constant.FREE_INTERVAL_NAME, freeIntervals.get(0).getSummary());

    }

    @Test
    public void SplitIntervalsShouldNotSplitIntervalWhenDurationOfAnIntervalIsLessThanMaxAllowedDuration() {

        DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);

        DateTime JanFirst4_50PM = JanFirst4PM.plusMinutes(50); // make an interval of 50 minutes.

        Task freeInterval = new Task(Constant.FREE_INTERVAL_NAME, JanFirst4PM, JanFirst4_50PM);
        final List<Task> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        Assert.assertNotNull(freeIntervals);
        final List<Task> tasks = SchedulerService.splitIntervals(freeIntervals);
        Assert.assertNotNull(tasks);
        Assert.assertEquals(1, tasks.size());

    }

    @Test
    public void SplitIntervalsShouldSplitIntervalWhenDurationOfAnIntervalIsMoreThanMaxAllowedDuration() {

        DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval + Constant.min_interval);

        Task freeInterval = new Task(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Task> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        Assert.assertNotNull(freeIntervals);
        final List<Task> tasks = SchedulerService.splitIntervals(freeIntervals);
        Assert.assertNotNull(tasks);
        Assert.assertEquals(2, tasks.size());

    }

    @Test
    public void ScheduleShoyldReturnEmptyListWhenThereAreNoProjects() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval + Constant.min_interval);
        final List<Project> projects  = new ArrayList<>();
        Task freeInterval = new Task(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Task> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);
        final List<Task> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
    }

    @Test
    public void ScheduleShoyldReturnEmptyListWhenThereAreNoFreeIntervals() {
        final List<Project> projects  = new ArrayList<>();
        final List<Task> freeIntervals = new ArrayList<>();
        final List<Task> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
    }

    @Test
    public void ScheduleShouldReturnAssignedIntervalWithProjectWhenTaskDurationIsLessThanProjectRemaininTime() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.min_interval);

        Task freeInterval = new Task(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Task> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);

        final List<Project> projects  = new ArrayList<>();
        Project project = new Project("Read on OOP", 0, 16 * 60);
        projects.add(project);


        final List<Task> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
        Assert.assertEquals(1, schedule.size());
        Assert.assertEquals(1, projects.size());
        Assert.assertEquals(0, freeIntervals.size());
    }

    @Test
    public void ScheduleShouldReturnAssignedIntervalWithProjectWhenTaskDurationIsMoreThanProjectRemaininTime() {
        final DateTime JanFirst4PM = new DateTime(2020, 01, 01, 15, 0);
        final DateTime plusMinutes = JanFirst4PM.plusMinutes(Constant.max_interval);

        Task freeInterval = new Task(Constant.FREE_INTERVAL_NAME, JanFirst4PM, plusMinutes);
        final List<Task> freeIntervals = new ArrayList<>();
        freeIntervals.add(freeInterval);

        final List<Project> projects  = new ArrayList<>();
        Project project = new Project("Read on OOP", 0, Constant.min_interval);
        projects.add(project);

        final List<Task> schedule = SchedulerService.schedule(projects, freeIntervals);
        Assert.assertNotNull(schedule);
        Assert.assertEquals(1, schedule.size());
        Assert.assertEquals(0, projects.size());
    }

    @Test
    public void run() {
        SchedulerService schedulerService = new SchedulerService();
//        schedulerService.run(null, null, true);
    }
}
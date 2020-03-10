package service;

import models.Constant;
import models.Project;
import models.Task;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.*;

public class SchedulerService {

    public static List<Task> schedule(List<Project> projects, List<Task> unassignedTasks) {
        // while something to schedule and any empty intervals remaining r
        List<Task> result = new ArrayList<>();
        int index = 0;

        while (!projects.isEmpty() && !unassignedTasks.isEmpty()) {
            // check if the project can be completed assigned ??
            Task task = unassignedTasks.remove(0);
            if (task.getDuration() >= projects.get(index).getRequired_minutes()) {
                // means can be assigned and project can be removed and new interval can be created
                task.setSummary(projects.get(index).getName());
                task.setEnd(task.getStart().plusMinutes(projects.get(index).getRequired_minutes()));
                result.add(task);
                projects.remove(index);
            } else {
                task.setSummary(projects.get(index).getName());
                result.add(task);
                projects.get(index).setRequired_minutes(projects.get(index).getRequired_minutes() - task.getDuration());
            }

            if (projects.size() > 0)
                index = (index + 1) % projects.size();
        }
        return result;
    }

    /**
     *
     * @param freeIntervals iin sorted order
     * @return
     */
    public static List<Task> splitIntervals(List<Task> freeIntervals) {
        // Comparator to compare two tasks based on Length of Duration

        Comparator<Task> comparator = (o1, o2) -> o2.getDuration() - o1.getDuration();

        List<Task> result = new ArrayList<>();

        for (Task interval : freeIntervals) {
            DateTime newStartDateTime;
            if (interval.getDuration() > Constant.max_interval) {
                int i = 0;
                do {
                    newStartDateTime = interval.getStart().plusMinutes(i);
                    result.add(new Task(Constant.FREE_INTERVAL_NAME,
                            newStartDateTime,
                            newStartDateTime.plusMinutes(Constant.max_interval - 15)));
                    interval.setDuration(interval.getDuration() - Constant.max_interval);
                    i += Constant.max_interval;
                }
                while (interval.getDuration() > Constant.max_interval);
                interval.setStart(interval.getStart().plusMinutes(i));
                interval.setEnd(interval.getStart().plusMinutes(interval.getDuration()));
            }
            if (interval.getDuration() > 30)
                result.add(interval);
        }
        Collections.sort(result, comparator);
        return result;
    }

    /**
     * @param assignedTask Need to be sorted, cannot deal with overlapping intervals
     * @return
     */
    // TODO: remove magic number and test case for that
    public static List<Task> getFreeIntervals(List<Task> assignedTask) {
        List<Task> freeIntervalsList = new ArrayList<>();
        for (int i = 1; i < assignedTask.size(); i++) {
            Task prev = assignedTask.get(i - 1);
            Task curr = assignedTask.get(i);
            // find the minutes between two intervals, this is why the tasks should be sorted
            int interval = Minutes.minutesBetween(prev.getEnd(), curr.getStart()).getMinutes();
            if (interval >= Constant.min_interval) {
                freeIntervalsList.add(new Task(Constant.FREE_INTERVAL_NAME, prev.getEnd().plusMinutes(10), curr.getStart().minusMinutes(10)));
            }
        }
        return freeIntervalsList;
    }

    public List<Task> run(List<Task> tasks, List<Project> projects, boolean verbose) {
        Collections.sort(tasks); //sort them based on their start time
        // get gaps in the already assigned Intervals
        List<Task> unassignedIntervals = getFreeIntervals(tasks);
        // fill the gaps with the projects
        List<Task> assignedIntervals = schedule(projects, unassignedIntervals);
        return assignedIntervals;
    }

}

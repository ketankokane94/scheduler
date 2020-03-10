package service;

import models.Constant;
import models.Interval;
import models.Project;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.*;

public class SchedulerService {

    public static List<Interval> schedule(List<Project> projects, List<Interval> unassignedIntervals) {
        // while something to schedule and any empty intervals remaining r
        List<Interval> result = new ArrayList<>();
        int index = 0;

        while (!projects.isEmpty() && !unassignedIntervals.isEmpty()) {
            // check if the project can be completed assigned ??
            Interval interval = unassignedIntervals.remove(0);
            if (interval.getDuration() >= projects.get(index).getRequired_minutes()) {
                // means can be assigned and project can be removed and new interval can be created
                interval.setSummary(projects.get(index).getName());
                interval.setEnd(interval.getStart().plusMinutes(projects.get(index).getRequired_minutes()));
                result.add(interval);
                projects.remove(index);
            } else {
                interval.setSummary(projects.get(index).getName());
                result.add(interval);
                projects.get(index).setRequired_minutes(projects.get(index).getRequired_minutes() - interval.getDuration());
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
    public static List<Interval> splitIntervals(List<Interval> freeIntervals) {
        // Comparator to compare two tasks based on Length of Duration

        Comparator<Interval> comparator = (o1, o2) -> o2.getDuration() - o1.getDuration();

        List<Interval> result = new ArrayList<>();

        for (Interval interval : freeIntervals) {
            DateTime newStartDateTime;
            if (interval.getDuration() > Constant.max_interval) {
                int i = 0;
                do {
                    newStartDateTime = interval.getStart().plusMinutes(i);
                    result.add(new Interval(Constant.FREE_INTERVAL_NAME,
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
     * @param assignedInterval Need to be sorted, cannot deal with overlapping intervals
     * @return
     */
    // TODO: remove magic number and test case for that
    public static List<Interval> getFreeIntervals(List<Interval> assignedInterval) {
        List<Interval> freeIntervalsList = new ArrayList<>();
        for (int i = 1; i < assignedInterval.size(); i++) {
            Interval prev = assignedInterval.get(i - 1);
            Interval curr = assignedInterval.get(i);
            // find the minutes between two intervals, this is why the tasks should be sorted
            int interval = Minutes.minutesBetween(prev.getEnd(), curr.getStart()).getMinutes();
            if (interval >= Constant.min_interval) {
                freeIntervalsList.add(new Interval(Constant.FREE_INTERVAL_NAME, prev.getEnd().plusMinutes(10), curr.getStart().minusMinutes(10)));
            }
        }
        return freeIntervalsList;
    }

    public List<Interval> run(List<Interval> intervals, List<Project> projects, boolean verbose) {
        Collections.sort(intervals); //sort them based on their start time
        // get gaps in the already assigned Intervals
        List<Interval> unassignedIntervals = getFreeIntervals(intervals);
        // fill the gaps with the projects
        List<Interval> assignedIntervals = schedule(projects, unassignedIntervals);
        return assignedIntervals;
    }

}

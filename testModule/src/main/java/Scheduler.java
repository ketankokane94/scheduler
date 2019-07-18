import models.*;

import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Scheduler {

    public static List<Interval> main() {

        final List<Task> tasks = new PopulateTask().getTask();
        List<Project> projects = new PopulateTask().getProjects();
        Collections.sort(tasks);
        PriorityQueue<Interval>  unassignedInterval = getIntervalsToSchedule(tasks);
        List<Interval> assignedInterval = schedule(projects, unassignedInterval);
        syncIntervals(assignedInterval, tasks);
        //printToConsole(projects, assignedInterval);

        return assignedInterval;
    }

    private static void printToConsole(List<Project> projects, List<Interval> assignedInterval) {
        for (Interval item : assignedInterval)
            System.out.println(item);

        for (Project proj : projects){
            System.out.println(proj);
        }
    }

    private static void syncIntervals(List<Interval> assignedInterval, List<Task> thinsgToDo) {
        for (Task thingToDo : thinsgToDo) {
            assignedInterval.add(new Interval(thingToDo.Name, thingToDo.from, thingToDo.to));
        }
        Collections.sort(assignedInterval);
    }

    private static List<Interval> schedule(List<Project> projects, PriorityQueue<Interval> unassignedIntervalQueue) {
        // while something to schedule and any empty intervals remaining r
        List<Interval> result = new ArrayList<>();
        int index = 0;
        while (!projects.isEmpty() && !unassignedIntervalQueue.isEmpty()) {
            // check if the project can be completed assigned ??
            Interval temp = unassignedIntervalQueue.poll();
            if (temp.intervalLength >= projects.get(index).required_minutes) {
                // means can be assigned and project can be removed and new interval can be created
                LocalTime end = temp.to;
                temp.Name = projects.get(index).name;
                temp.to = temp.from.plusMinutes(projects.get(index).required_minutes);
                result.add(temp);
                projects.remove(index);

//            if(item.intervalLength > 30)
              unassignedIntervalQueue.add(new Interval("NA", temp.to, end));
            } else {
                temp.Name = projects.get(index).name;
                result.add(temp);
                projects.get(index).required_minutes -= temp.intervalLength;
            }
            if(projects.size() > 0)
            index = (index + 1) % projects.size();
        }
        return result;
    }


    private static PriorityQueue<Interval> splitIntevals(List<Interval> list) {
        Comparator<Interval> comparator = (o1, o2) -> o2.intervalLength - o1.intervalLength;
        PriorityQueue<Interval> result = new PriorityQueue<>(comparator);
        int max_interval = 120;
        for (Interval item : list) {
            LocalTime newStartTime;
            if (item.intervalLength > max_interval) {
                int i = 0;
                do {
                    newStartTime = item.from.plusMinutes(i);
                    result.add(new Interval(item.Name, newStartTime, newStartTime.plusMinutes(max_interval - 15)));
                    item.intervalLength -= max_interval;
                    i += max_interval;
                }
                while (item.intervalLength > max_interval);
               // item.from += i;
                item.from = item.from.plusMinutes(i);
                item.to  = item.from.plusMinutes(item.intervalLength);
            }
            if(item.intervalLength > 30)
                result.add(item);
        }
        return result;
    }

    private static PriorityQueue<Interval> getIntervalsToSchedule(List<Task> thinsgToDo) {
        List<Interval> result = new ArrayList<>();
        int min_interval = 55;

        for (int i = 1; i < thinsgToDo.size(); i++) {
            int interval = (int) MINUTES.between(thinsgToDo.get(i - 1).to, thinsgToDo.get(i).from);
            if (interval >= min_interval) {
                result.add(new Interval("NA", thinsgToDo.get(i - 1).to.plusMinutes(10), thinsgToDo.get(i).from.minusMinutes(10)));
            }
        }
        return splitIntevals(result);

    }

}

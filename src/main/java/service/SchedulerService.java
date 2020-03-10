package service;

import models.Constant;
import models.Project;
import models.Task;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class SchedulerService {

    public static void printToConsole(List<Project> projects, List<Task> assignedInterval) {
        for (Task item : assignedInterval)
            System.out.println(item);

        for (Project proj : projects) {
            System.out.println(proj);
        }
    }

    public static void mergeTasks(List<Task> assignedInterval, List<Task> thinsgToDo) {
        for (Task thingToDo : thinsgToDo) {
            assignedInterval.add(new Task(thingToDo.getSummary(), thingToDo.getStart(), thingToDo.getEnd()));
        }
        Collections.sort(assignedInterval);
    }

    public static List<Task> schedule(List<Project> projects, PriorityQueue<Task> unassignedTasks) {
        // while something to schedule and any empty intervals remaining r
        List<Task> result = new ArrayList<>();
        int index = 0;
        while (!projects.isEmpty() && !unassignedTasks.isEmpty()) {
            // check if the project can be completed assigned ??
            Task task = unassignedTasks.poll();
            if (task.getDuration() >= projects.get(index).getRequired_minutes()) {
                // means can be assigned and project can be removed and new interval can be created
                task.setSummary(projects.get(index).getName());
                task.setEnd(task.getStart().plusMinutes(projects.get(index).getRequired_minutes()));
                result.add(task);
                projects.remove(index);

//            if(item.intervalLength > 30)
                //  unassignedTasks.add(new Task("NA", task.to, end));
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

    public static PriorityQueue<Task> splitIntervals(List<Task> freeIntervals) {
        // Comparator to compare two tasks based on Length of Duration
        PriorityQueue<Task> result = new PriorityQueue<>((o1, o2) -> o2.getDuration() - o1.getDuration());

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
        return result;
    }

    /**
     * @param assignedTask
     * @return
     */
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

//    public List<Task> run(List<Task> tasks, List<Project> projects, boolean verbose) {
//
//        Collections.sort(tasks);
//        PriorityQueue<Task> unassignedInterval = getFreeIntervals(tasks);
//        List<Task> assignedInterval = schedule(projects, unassignedInterval);
//        printToConsole(projects, assignedInterval);
////        if (verbose){
////            mergeTasks(assignedInterval, tasks);
////            printToConsole(projects, assignedInterval);
////        }
//        return assignedInterval;
//    }

}

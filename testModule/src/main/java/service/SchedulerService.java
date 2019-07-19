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

    private static void printToConsole(List<Project> projects, List<Task> assignedInterval) {
        for (Task item : assignedInterval)
            System.out.println(item);

        for (Project proj : projects) {
            System.out.println(proj);
        }
    }

    private static void mergeTasks(List<Task> assignedInterval, List<Task> thinsgToDo) {
        for (Task thingToDo : thinsgToDo) {
            assignedInterval.add(new Task(thingToDo.getSummary(), thingToDo.getStart(), thingToDo.getEnd()));
        }
        Collections.sort(assignedInterval);
    }

    private static List<Task> schedule(List<Project> projects, PriorityQueue<Task> unassignedTasks) {
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

    private static PriorityQueue<Task> splitIntevals(List<Task> unAssignedTasks) {

        PriorityQueue<Task> result = new PriorityQueue<>((o1, o2) -> o2.getDuration() - o1.getDuration());
        for (Task task : unAssignedTasks) {

            DateTime newStartDateTime;

            if (task.getDuration() > Constant.max_interval) {
                int i = 0;
                do {
                    newStartDateTime = task.getStart().plusMinutes(i);
                    result.add(new Task("",
                            newStartDateTime,
                            newStartDateTime.plusMinutes(Constant.max_interval - 15)));
                    task.setDuration(task.getDuration() - Constant.max_interval);
                    i += Constant.max_interval;
                }
                while (task.getDuration() > Constant.max_interval);
                task.setStart(task.getStart().plusMinutes(i));
                task.setEnd(task.getStart().plusMinutes(task.getDuration()));
            }
            if (task.getDuration() > 30)
                result.add(task);
        }
        return result;
    }

    public static PriorityQueue<Task> getIntervalsToSchedule(List<Task> assignedTask) {
        List<Task> unAssignedTask = new ArrayList<>();
        for (int i = 1; i < assignedTask.size(); i++) {
            Task prev = assignedTask.get(i - 1);
            Task curr = assignedTask.get(i);
            int interval = Minutes.minutesBetween(prev.getEnd(), curr.getStart()).getMinutes();
            if (interval >= Constant.min_interval) {
                unAssignedTask.add(new Task("", prev.getEnd().plusMinutes(10), curr.getStart().minusMinutes(10)));
            }
        }
        return splitIntevals(unAssignedTask);

    }

    public List<Task> run(boolean verbose) {

        final List<Task> tasks = new GetTaskService().getTask();
        List<Project> projects = new GetProjectService().getProjects();
        Collections.sort(tasks);
        PriorityQueue<Task> unassignedInterval = getIntervalsToSchedule(tasks);
        List<Task> assignedInterval = schedule(projects, unassignedInterval);
        mergeTasks(assignedInterval, tasks);
        if (verbose)
            printToConsole(projects, assignedInterval);

        return assignedInterval;
    }

}

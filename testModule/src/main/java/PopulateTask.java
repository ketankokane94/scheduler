import models.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PopulateTask {

    public List<Task> getTask() {
        List<Task> tasks = new LinkedList<>();
        tasks.add(new Activity("wake up", "12:00 AM", Constant.WAKE_UP_AT));
        tasks.add(new Activity("Sleep", Constant.SLEEP_AT, Constant.WAKE_UP_AT));
        getActivities(tasks);
        getEvents(tasks);
        return tasks;
    }

    public List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3));
        result.add(new Project("Leet code", 0, 3));
        result.add(new Project("Call HDFC", 0, 1));

        //result.add(new models.Project("work on Scheduler Issue", 0, 18));
        //result.add(new models.Project("packing for ROC", 0, 5));
        //result.add(new models.Project("Shopping for ROC", 0, 8));
        return result;
    }

    private static void getActivities(List<Task> tasks) {
        tasks.add(new Activity("Lunch", "03:00 PM", "03:45 PM"));
        tasks.add(new Activity("Dinner", "08:00 PM", "08:30 PM"));
        tasks.add(new Activity("Relax", "08:30 PM", "11:59 PM"));
    }

    private static void getEvents(List<Task> tasks) {
        tasks.add(new Event("geeta visit", "01:00 PM", "03:00 PM"));
        //thinsgToDo.add(new models.Event("Meet Kavya", "04:00 PM", "05:30 PM"));
    }

}

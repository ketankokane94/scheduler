import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class main {
    private  static String WAKE_UP_AT = "09:30 AM";
    private  static String SLEEP_AT = "11:59 PM";

    private static List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3));
        result.add(new Project("Leet code", 0, 3));
        result.add(new Project("Call HDFC", 0, 1));

        //result.add(new Project("work on Scheduler Issue", 0, 18));
        //result.add(new Project("packing for ROC", 0, 5));
        //result.add(new Project("Shopping for ROC", 0, 8));
        return result;
    }

    private static void getActivities(List<base> thinsgToDo) {
        thinsgToDo.add(new Activities("Lunch", "03:00 PM", "03:45 PM"));
        thinsgToDo.add(new Activities("Dinner", "08:00 PM", "08:30 PM"));
        thinsgToDo.add(new Activities("Relax", "08:30 PM", "11:59 PM"));
    }

    private static void getEvents(List<base> thinsgToDo) {
        thinsgToDo.add(new Event("geeta visit", "01:00 PM", "03:00 PM"));
        //thinsgToDo.add(new Event("Meet Kavya", "04:00 PM", "05:30 PM"));
    }

    public static List<Interval> main() {

        List<base> tasks = new ArrayList<>();
        getEvents(tasks);
        getActivities(tasks);
        tasks.add(1, new Activities("wake up", "12:00 AM", WAKE_UP_AT));
        tasks.add(new Activities("Sleep", SLEEP_AT, WAKE_UP_AT));

        Collections.sort(tasks);
        List<Interval> unassignedInterval = getUnPlannedIntervals(tasks);
        PriorityQueue<Interval> unassignedIntervalQueue = splitIntevals(unassignedInterval);

        List<Project> projects = getProjects();
        List<Interval> assignedInterval = assign(projects, unassignedIntervalQueue);

        addPlannedIntervals(assignedInterval, tasks);

        for (Interval item : assignedInterval)
            System.out.println(item);

        for (Project proj : projects){
            System.out.println(proj);
        }
        return assignedInterval;
    }

    private static void addPlannedIntervals(List<Interval> assignedInterval, List<base> thinsgToDo) {
        for (base thingToDo : thinsgToDo) {
            assignedInterval.add(new Interval(thingToDo.Name, thingToDo.from, thingToDo.to));
        }
        Collections.sort(assignedInterval);
    }

    private static List<Interval> assign(List<Project> projects, PriorityQueue<Interval> unassignedIntervalQueue) {
        // while something to assign and any empty intervals remaining r
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

    private static List<Interval> getUnPlannedIntervals(List<base> thinsgToDo) {
        List<Interval> result = new ArrayList<>();
        int min_interval = 55;

        for (int i = 1; i < thinsgToDo.size(); i++) {
            int interval = (int) MINUTES.between(thinsgToDo.get(i - 1).to, thinsgToDo.get(i).from);
            if (interval >= min_interval) {
                result.add(new Interval("NA", thinsgToDo.get(i - 1).to.plusMinutes(10), thinsgToDo.get(i).from.minusMinutes(10)));
            }
        }
        return result;
    }

}

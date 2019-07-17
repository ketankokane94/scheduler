import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class main {
    private  static String WAKE_UP_AT = "09:30";
    private  static String SLEEP_AT = "23:59";
    private static List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Leet code", 0, 3));
        result.add(new Project("work on ", 0, 18));
        result.add(new Project("packing for ROC", 0, 5));
        result.add(new Project("Shopping for ROC", 0, 8));
        return result;
    }

    private static void getActivities(List<base> thinsgToDo) {
        thinsgToDo.add(new Activities("Lunch", "12:00", "12:45"));
        thinsgToDo.add(new Activities("Dinner", "20:00", "20:30"));
       // thinsgToDo.add(new Activities("Gym", 1500, 1600));

    }

    private static void getEvents(List<base> thinsgToDo) {
        thinsgToDo.add(new Event("Gym", "15:00", "16:00"));
        thinsgToDo.add(new Event("Meet Kavya", "16:00", "17:30"));

//        [[1400, 1515,"Lect CV"], [930, 1045,"Lect FIS"],[1530, 1645,"SS"]]
    }

    public static void main(String[] args) {

        List<base> thinsgToDo = new ArrayList<>();
        getEvents(thinsgToDo);
        getActivities(thinsgToDo);
        thinsgToDo.add(1, new Activities("wake up ", "00:00", WAKE_UP_AT));
        thinsgToDo.add(new Activities("Sleep", SLEEP_AT, "09:30"));

        Collections.sort(thinsgToDo);
        List<Interval> unassignedInterval = getUnPlannedIntervals(thinsgToDo);
        PriorityQueue<Interval> unassignedIntervalQueue = splitIntevals(unassignedInterval);

        List<Project> projects = getProjects();
        List<Interval> assignedInterval = assign(projects, unassignedIntervalQueue);

        addPlannedIntervals(assignedInterval, thinsgToDo);

        for (Interval item : assignedInterval)
            System.out.println(item);
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
            if (temp.intervalLength >= projects.get(index).required_hours) {
                // means can be assigned and project can be removed and new interval can be created
                temp.Name = projects.get(index).name;
                result.add(temp);
                projects.remove(index);
            } else {
                temp.Name = projects.get(index).name;
                result.add(temp);
                projects.get(index).required_hours -= temp.intervalLength;
            }
            index = (index + 1) % projects.size();
        }
        return result;
    }


    private static PriorityQueue<Interval> splitIntevals(List<Interval> list) {
        Comparator<Interval> comparator = (o1, o2) -> o2.intervalLength - o1.intervalLength;
        PriorityQueue<Interval> result = new PriorityQueue<>(comparator);
        int max_interval = 120;
        for (Interval item : list) {
            if (item.intervalLength > max_interval) {
                int i = 0;
                do {
                    result.add(new Interval(item.Name, item.from.plusMinutes(i), item.from.plusMinutes(max_interval)));
                    item.intervalLength -= max_interval;
                    i += max_interval;
                }
                while (item.intervalLength > max_interval);
               // item.from += i;
                item.from = item.from.plusMinutes(i);
                //item.to  = item.from + item.intervalLength;
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

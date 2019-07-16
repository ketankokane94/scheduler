import java.util.*;

public class main {
    public static void main(String[] args) {
        List<base> thinsgToDo = new ArrayList<>();
        getEvents(thinsgToDo);
        getActivities(thinsgToDo);
        thinsgToDo.add(1, new Activities("wake up ", 0, 530));
        thinsgToDo.add(new Activities("Sleep", 2230, 2359));


        List<Interval> unassignedInterval = getUnPlannedIntervals(thinsgToDo);
        PriorityQueue<Interval> unassignedIntervalQueue = splitIntevals(unassignedInterval);

        List<Project> projects = getProjects();
        List<Interval> assignedInterval = assign(projects, unassignedIntervalQueue);
        // merge assigned and unassigned intervals
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

        for (Interval item : list) {
            if (item.intervalLength > 200) {
                int i = 0;
                do {
                    result.add(new Interval(item.Name, item.from + i, item.from + 200));
                    item.intervalLength -= 200;
                    i += 200;
                }
                while (item.intervalLength > 200);
                item.from += i;
                //item.to  = item.from + item.intervalLength;
            }
            result.add(item);
        }
        return result;
    }

    private static List<Interval> getUnPlannedIntervals(List<base> thinsgToDo) {
        Collections.sort(thinsgToDo);
        List<Interval> result = new ArrayList<>();
        int min_interval = 55;
        //   int max_interval = 200;
        for (int i = 1; i < thinsgToDo.size(); i++) {
            if (thinsgToDo.get(i).from - thinsgToDo.get(i - 1).to >= min_interval) {
                result.add(new Interval("Not assigned", thinsgToDo.get(i - 1).to, thinsgToDo.get(i).from));
            }
        }
        return result;
    }

    private static List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Leet code", 0, 3));
        result.add(new Project("code a project", 0, 18));
        result.add(new Project("Interview Prep", 0, 180));
        result.add(new Project("packing for ROC", 0, 5));
        result.add(new Project("Shopping for ROC", 0, 8));
        return result;
    }

    private static void getActivities(List<base> thinsgToDo) {
        thinsgToDo.add(new Activities("Lunch", 1300, 1330));
        thinsgToDo.add(new Activities("Dinner", 2000, 2030));
//        [1300, 1330,"Lunch"],[2000, 2030,"Dinner"]
    }

    private static void getEvents(List<base> thinsgToDo) {
        thinsgToDo.add(new Event("Lect CV ", 1400, 1515));
        thinsgToDo.add(new Event("Lect FIS ", 930, 1045));
        thinsgToDo.add(new Event("Lect SS ", 1530, 1645));
//        [[1400, 1515,"Lect CV"], [930, 1045,"Lect FIS"],[1530, 1645,"SS"]]
    }


}

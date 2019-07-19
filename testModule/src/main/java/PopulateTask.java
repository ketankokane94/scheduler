import models.Project;
import models.Task;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PopulateTask {
    public static String today_date = "2019-07-19T";

    public List<Task> getTask() {
        List<Task> tasks = new LinkedList<>();
        tasks.add(new Task("wake up",
                DateTime.parse(today_date + "00:00:00"),
                DateTime.parse(today_date + "09:40:00")));

        tasks.add(new Task("Sleep",
                DateTime.parse(today_date + "23:59:58"),
                DateTime.parse(today_date + "23:59:59")));

        tasks.add(new Task("Lunch", DateTime.parse(today_date + "15:00:00"),
                DateTime.parse(today_date + "15:45:00")));

        tasks.add(new Task("Dinner", DateTime.parse(today_date + "20:00:00"),
                DateTime.parse(today_date + "20:30:00")));

        tasks.add(new Task("Relax", DateTime.parse(today_date + "20:30:00"),
                DateTime.parse(today_date + "23:59:58")));


        return tasks;
    }

    public List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3*60));
        result.add(new Project("Leet code", 0, 3*60));
        result.add(new Project("Call HDFC", 0, 1*60));
        return result;
    }


}

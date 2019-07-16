import java.util.Date;

/**
 *  {"name":"Solve 3 problems on leetcode","required_hours":3,"deadline":"07/17/2018","completed_hours":0},
 */
public class Project {
    //Date deadline;
    int completedTime, required_hours;
    String name;

    public Project(String name,  int completedTime, int remainingTime) {
        //this.deadline = deadline;
        this.name = name;
        this.completedTime = completedTime * 100;
        this.required_hours  = remainingTime * 100;
    }
}

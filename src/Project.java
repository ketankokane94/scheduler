/**
 *  {"name":"Solve 3 problems on leetcode","required_minutes":3,"deadline":"07/17/2018","completed_hours":0},
 */
public class Project {
    //Date deadline;
    int completedTime, required_minutes;
    String name;

    public Project(String name,  int completedTime, int remainingTime) {
        //this.deadline = deadline;
        this.name = name;
        this.completedTime = completedTime ;
        this.required_minutes = remainingTime * 60;
    }

    @Override
    public String toString() {
        return "Project{" +
                "completedTime=" + completedTime +
                ", required_minutes=" + required_minutes +
                ", name='" + name + '\'' +
                '}';
    }
}

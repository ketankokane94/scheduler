package models;

public class Project {

    private int completedTime;
    private int required_minutes;
    private String name;

    public Project(String name, int completedTime, int remainingTime) {
        this.name = name;
        this.completedTime = completedTime;
        this.required_minutes = remainingTime;
    }

    public int getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(int completedTime) {
        this.completedTime = completedTime;
    }

    public int getRequired_minutes() {
        return required_minutes;
    }

    public void setRequired_minutes(int required_minutes) {
        this.required_minutes = required_minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

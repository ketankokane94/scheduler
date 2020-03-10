package models;

import org.joda.time.DateTime;
import org.joda.time.Minutes;


public class Task implements Comparable<Task> {

    private String summary;
    private int duration;
    private String description;
    private DateTime start;
    private DateTime end;

    public Task(String summary, DateTime start, DateTime end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
        duration = Minutes.minutesBetween(start, end).getMinutes();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    @Override
    public int compareTo(Task otherTask) {
        // compare based on the start time
        return start.compareTo(otherTask.start);
    }

    @Override
    public String toString() {
        return "Task{" +
                "summary='" + summary + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

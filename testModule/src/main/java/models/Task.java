package models;

import com.google.api.client.util.DateTime;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Task implements Comparable{

    public String summary;
    public LocalTime from;
    public LocalTime to;
    private String description;
    private DateTime start;
    private DateTime end;

    public Task(String summary, DateTime start, DateTime end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
    }

    public Task(String taskName, LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
        this.summary = taskName;
    }

    public Task(String name, String from, String to) {
        summary = name;
        this.from = LocalTime.parse(from, Constant.format);
        this.to = LocalTime.parse(to, Constant.format);;
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
    public int compareTo(Object o) {
        return (int)MINUTES.between(((Task) o).from, this.from );
    }

    // later on remove all the to string methods
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("summary= '" + summary + '\'');
        sb.append(" From = " + from.format(Constant.format));
        sb.append(" to = " + to.format(Constant.format));
        return sb.toString();
    }
}

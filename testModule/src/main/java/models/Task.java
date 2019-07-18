package models;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Task implements Comparable{
    public String Name;
    public LocalTime from, to;


    public Task(String taskName, LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
        this.Name = taskName;
    }

    public Task(String name, String from, String to) {
        Name = name;
        this.from = LocalTime.parse(from, Constant.format);
        this.to = LocalTime.parse(to, Constant.format);;
    }

    @Override
    public int compareTo(Object o) {
        return (int)MINUTES.between(((Task) o).from, this.from );
    }

    // later on remove all the to string methods
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name= '" + Name + '\'');
        sb.append(" From = " + from.format(Constant.format));
        sb.append(" to = " + to.format(Constant.format));
        return sb.toString();
    }
}

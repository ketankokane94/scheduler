package models;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class base implements Comparable{
    public String Name;
    public LocalTime from, to;


    public base(String taskName, LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
        this.Name = taskName;
    }

    public base(String name, String from, String to) {
        Name = name;
        this.from = LocalTime.parse(from, Constant.format);
        this.to = LocalTime.parse(to, Constant.format);;
    }

    @Override
    public int compareTo(Object o) {
        return (int)MINUTES.between(((base) o).from, this.from );
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name= '" + Name + '\'');
        sb.append(" From = " + from.format(Constant.format));
        sb.append(" to = " + to.format(Constant.format));
        return sb.toString();
    }
}

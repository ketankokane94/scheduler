package models;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Interval extends Task {

    public int intervalLength;

    public Interval(String taskName, String from, String to) {
        super(taskName, from, to);
        intervalLength = (int)MINUTES.between(this.to , this.from);
    }

    public Interval(String taskName, LocalTime from, LocalTime to) {
        super(taskName,from,to);
        intervalLength = (int)MINUTES.between(this.from, this.to);
    }

    @Override
    public String toString() {
        int hours = 0;
        if (intervalLength < 0)
            intervalLength = -intervalLength;
        while (intervalLength > 60){
            hours += 1;
            intervalLength -= 60;
        }

        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" Duration= "+ hours + "hr " + intervalLength + " mins");
        return sb.toString();
    }
}

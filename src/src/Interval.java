import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Interval extends  base{

    int intervalLength;

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
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" Duration= "+intervalLength);
        return sb.toString();
    }
}

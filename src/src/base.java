import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;

public class base implements Comparable{
    String Name;
    LocalTime from, to;


    public base(String taskName, LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
        this.Name = taskName;
    }

    public base(String name, String from, String to) {
        Name = name;
        this.from = LocalTime.parse(from, DateTimeFormatter.ofPattern("HH:mm"));
        this.to = LocalTime.parse(to, DateTimeFormatter.ofPattern("HH:mm"));;
    }

    @Override
    public int compareTo(Object o) {
        return (int)MINUTES.between(((base) o).from, this.from );
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name= '" + Name + '\'');
        sb.append(" From = " + from.toString());
        sb.append(" to = " + to.toString());
        return sb.toString();
    }
}

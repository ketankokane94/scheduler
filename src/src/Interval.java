public class Interval extends  base{

    int intervalLength;

    public Interval(String taskName, int from, int to) {
        super(taskName, from, to);
        intervalLength = to - from;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name= '" + Name + '\'');
        sb.append(" From= ");
        sb.append(from < 1200 ? from + " AM " : (from - 1200) + " PM ");
        sb.append(" To= ");
        sb.append(to < 1200 ? to + " AM " : (to - 1200) + " PM ");
        return sb.toString();
    }
}

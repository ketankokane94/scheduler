public class base implements Comparable{
    String Name;
    int from, to;

    public base(String name, int from, int to) {
        Name = name;
        this.from = from;
        this.to = to;
    }

    @Override
    public int compareTo(Object o) {
        return this.from - ((base) o).from;
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

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
}

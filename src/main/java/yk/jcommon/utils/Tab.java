package yk.jcommon.utils;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 20:29
 */
public class Tab {
    private String incer;
    private String tab = "";

    public Tab() {
        this("    ");
    }

    public static void main(String[] args) {
        Tab t = new Tab("--");
        System.out.println(t);
        t.inc();
        System.out.println(t);
        t.inc();
        System.out.println(t);
        t.dec();
        System.out.println(t);
        t.dec();
        System.out.println(t);
    }

    public Tab(String incer) {
        this.incer = incer;
    }

    public void inc() {
        tab += incer;
    }

    public void dec() {
        tab = tab.substring(0, tab.length() - incer.length());
    }

    @Override
    public String toString() {
        return tab;
    }
}

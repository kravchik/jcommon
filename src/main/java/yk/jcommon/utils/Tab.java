package yk.jcommon.utils;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 20:29
 */
public class Tab {
    public String incer;
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

    public String preInc() {
        String result = tab;
        inc();
        return result;
    }

    public String postDec() {
        dec();
        return tab;
    }

    public Tab inc() {
        tab += incer;
        return this;
    }

    public Tab dec() {
        tab = tab.substring(0, tab.length() - incer.length());
        return this;
    }

    @Override
    public String toString() {
        return tab;
    }
}

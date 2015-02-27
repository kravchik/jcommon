package yk.lang.yads;

import yk.jcommon.collections.YList;

import static yk.jcommon.collections.YArrayList.al;

public class Namespaces {

    public YList<NamespaceEntry> entries = al();

    public static Namespaces packages(String... ss) {
        Namespaces result = new Namespaces();
        result.enterScope();
        for (String s : ss) result.addPackage(s);
        return result;
    }

    public void enterScope() {
        entries.add(new NamespaceEntry());
    }

    public void exitScope() {
        entries.remove(entries.size() - 1);
    }

    public void addPackage(String p) {
        entries.get(entries.size() - 1).packages.add(p);
    }

    public Class findClass(String className) {
        for (int i = entries.size() - 1; i >= 0; i--) {
            NamespaceEntry entry = entries.get(i);
            if (entry.classes.containsKey(className)) try {
                String p = entry.classes.get(className);
                return Class.forName((p.length() > 0 ? p + "." : "") + className);
            } catch (ClassNotFoundException ignore) { }

            for (String p : entry.packages) try { return Class.forName((p.length() > 0 ? p + "." : "") + className); } catch (ClassNotFoundException ignore) { }
        }
        return null;
    }

}

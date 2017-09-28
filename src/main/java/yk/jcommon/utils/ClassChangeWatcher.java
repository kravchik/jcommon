package yk.jcommon.utils;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YSet;

import java.io.File;
import java.net.URL;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 16/11/15
 * Time: 20:27
 */
public class ClassChangeWatcher<T> {
    public YList<FileWatcher2> fileWatchers = al();
    public T dst;
    public String path;
    private YSet<String> classes;

    public static <T> ClassChangeWatcher<T> watchTargetClasses(T o) {
        return watch("target/classes/", o);
    }

    public static <T> ClassChangeWatcher<T> watch(String path, T o, Class... otherClasses) {
        ClassChangeWatcher<T> result = new ClassChangeWatcher<>();
        result.classes = hs(o.getClass().getName());
        result.classes.addAll(al(otherClasses).map(Class::getName));
        for (String c : result.classes) result.fileWatchers.add(new FileWatcher2(path + c.replace(".", "/") + ".class"));
        result.path = path;
        result.dst = o;
        return result;
    }

    public boolean reload() {
        File oldJar = new File(path);
        boolean changed = fileWatchers.reduce(false, (a, b) -> b.isJustChanged() || a);
        //TODO wait timeout if many files?
        if (changed) {
            try {
                MyClassLoader newClassLoader = new MyClassLoader(new URL[]{oldJar.toURI().toURL()}, true, classes);
                dst = (T) newClassLoader.loadClass(dst.getClass().getName()).newInstance();
                if (Reflector.findMethod(dst.getClass(), "onClassReloaded") != null) Reflector.invokeMethod(dst, "onClassReloaded");
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;


    }
}

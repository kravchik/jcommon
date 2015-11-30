package yk.jcommon.utils;

import yk.jcommon.collections.YSet;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 16/11/15
 * Time: 20:24
 */
public class MyClassLoader extends URLClassLoader {
    private YSet<String> names;
    private boolean statedForReload;
    private ClassLoader parent = MyClassLoader.class.getClassLoader();

    public MyClassLoader(URL[] urls, boolean statedForReload, YSet<String> exclusives) {
        super(urls, null);
        this.statedForReload = statedForReload;
        this.names = exclusives;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return (names.contains(name) == statedForReload) ? super.loadClass(name) : parent.loadClass(name);
    }
}

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
        if (statedForReload) {
            if (names.contains(name)) return super.loadClass(name);
            if (names.any(n -> name.startsWith(n + "$"))) return super.loadClass(name);//include child classes implicitly
        }
        return parent.loadClass(name);
    }
}

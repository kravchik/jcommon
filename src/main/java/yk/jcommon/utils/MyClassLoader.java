package yk.jcommon.utils;

import yk.jcommon.collections.YSet;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 16/11/15
 * Time: 20:24
 */
public class MyClassLoader extends URLClassLoader {
    private Function<String, Boolean> needReload;
    private YSet<String> names;
    private boolean statedForReload;
    private ClassLoader parent = MyClassLoader.class.getClassLoader();

    public MyClassLoader(URL[] urls, boolean statedForReload, YSet<String> exclusives) {
        super(urls, null);
        this.statedForReload = statedForReload;
        this.names = exclusives;
    }

    public MyClassLoader(URL[] urls, Function<String, Boolean> needReload) {
        super(urls, null);
        this.needReload = needReload;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (needReload != null) {
            if (needReload.apply(name)) super.loadClass(name);
            return parent.loadClass(name);
        }
        if (statedForReload) {
            if (names.contains(name)) return super.loadClass(name);
            if (names.any(n -> name.startsWith(n + "$"))) return super.loadClass(name);//include child classes implicitly
        }
        return parent.loadClass(name);
    }
}

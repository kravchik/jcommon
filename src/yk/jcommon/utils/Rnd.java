package yk.jcommon.utils;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/28/14
 * Time: 10:53 PM
 */
public class Rnd {
    public Random rnd = new Random();

    public <T> T from(List<T> f) {
        return f.get(rnd.nextInt(f.size()));
    }

}

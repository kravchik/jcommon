package yk.jcommon.tree;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 10/2/12
 * Time: 10:03 PM
 */
public class BaseIdentifiable implements Serializable {
    private static int newID = 1;
    public int id = newID++;
}

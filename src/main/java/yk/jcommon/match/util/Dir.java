package yk.jcommon.match.util;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 04/05/16
 * Time: 17:50
 */
public enum Dir {
    L(-1, 0),
    R(1, 0),
    U(0, 1),
    D(0, -1);
    int dx, dy;

    Dir(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

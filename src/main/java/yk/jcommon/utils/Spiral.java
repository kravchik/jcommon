package yk.jcommon.utils;

import yk.jcommon.fastgeom.Vec2i;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 01.09.2010
 * Time: 23:51:14
 */
public class Spiral implements Iterator<Vec2i>, Iterable<Vec2i> {
    private int state = 3;
    private Vec2i currentPos;
    private int counter = -1;
    private int curR;
    private int maxR;

    public Spiral(Vec2i start, int maxR) {
        currentPos = start.addY(1);
        curR = 0;
        this.maxR = maxR;
    }


    public boolean hasNext() {
        return curR < maxR;
    }

    public Vec2i next() {
        counter++;
        switch(state) {
            case 0:
                currentPos = currentPos.addX(1);
                break;
            case 1:
                currentPos = currentPos.addY(1);
                break;
            case 2:
                currentPos = currentPos.addX(-1);
                break;
            case 3:
                currentPos = currentPos.addY(-1);
                break;
        }

        if (counter == curR) {
            state = (state + 1) % 4;
            if (state == 0) curR++;
            if (state == 2) curR++;
            counter = 0;
        }
        return currentPos;
    }

    public void remove() {
        throw new Error();
    }

    public static void main(String[] args) {
        for (Vec2i vec2i : new Spiral(new Vec2i(0, 0), 10)) {
            System.out.println(vec2i);
        }
    }

    public Iterator<Vec2i> iterator() {
        return this;
    }

//    @Override
//    public void forEach(Block<? super Vec2i> block) {
//    }
}

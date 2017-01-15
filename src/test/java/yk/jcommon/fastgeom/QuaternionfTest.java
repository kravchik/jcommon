package yk.jcommon.fastgeom;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static yk.jcommon.fastgeom.Vec3f.v3;

/**
 * Created by Yuri Kravchik on 18/12/16.
 */
public class QuaternionfTest {
    @Test
    public void fromTwoVectors() throws Exception {
        Quaternionf q1 = Quaternionf.fromTwoVectors(v3(1, 0, 0), v3(0, 1, 0));
        assertTrue(q1.rotate(v3(1, 1, 0)).dist(v3(-1, 1, 0)) < 0.0001f);
    }

    @Test
    public void fromAxes() throws Exception {
        Quaternionf q1 = Quaternionf.fromAxes(
                v3( 1, 1, 0).normalized(),
                v3(-1, 1, 0).normalized(),
                v3( 0, 0, 1).normalized()
        );

        System.out.println(q1.conjug().rotate(v3(1, 0, 0)));

    }
}
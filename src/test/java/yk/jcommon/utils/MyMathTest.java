package yk.jcommon.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.utils.MyMath.*;

/**
 * Created by Yuri Kravchik on 15/12/16.
 */
public class MyMathTest {
    @Test
    public void testAngles() throws Exception {
        float a = PI * 1.5f;
        assertEquals(a, angleNormalize02PI(-PI / 2), 0);
        assertEquals(a, angleNormalize02PI(PI * 1.5f), 0);
        assertEquals(a, angleNormalize02PI(PI * 1.5f + PI * 8), 0);
        assertEquals(a, angleNormalize02PI(-PI / 2f - PI * 8), 0.0001);

        assertEquals(PI * 0.75f, mixAngle02PI(PI / 2, PI, 0.5f), 0);
        assertEquals(PI * 1.25f, mixAngle02PI(-PI / 2, PI, 0.5f), 0);
        assertEquals(PI * 0.5f * 0.25f, mixAngle02PI(angleNormalize02PI(-PI / 4), PI / 4, 0.75f), 0.0001);
        assertEquals(2 * PI - PI * 0.5f * 0.25f, mixAngle02PI(PI / 4, angleNormalize02PI(-PI / 4), 0.75f), 0.0001);

        assertEquals(PI / 2, angleDif02PI(PI / 2, PI), 0);
        assertEquals(PI / 2, angleDif02PI(-PI / 2, PI), 0.0001f);
        assertEquals(PI / 2, angleDif02PI(angleNormalize02PI(-PI / 4), PI / 4), 0.0001);
        assertEquals(PI / 2, angleDif02PI(PI / 4, angleNormalize02PI(-PI / 4)), 0.0001);
        
    }

}
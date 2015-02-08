/**
 * File Tester.java
 * @author Yuri Kravchik
 * Created 16.10.2008
 */
package yk.jcommon.fastgeom;

/**
 * Tester
 * 
 * @author Yuri Kravchik Created 16.10.2008
 */
public class Tester {

    public static void main(final String[] args) {
	final Vec3f s = new Vec3f(1, 2, 3);
	@SuppressWarnings("unused")
	final float d = s.crossProduct(new Vec3f(2, 3, 4))
			 .add(new Vec3f(5, 4, 3))
			 .mul(3)
			 .scalarProduct(new Vec3f(1, 1, 1));

    }
}

/**
 * File Quaternion.java
 * @author Yuri Kravchik
 * Created 15.02.2009
 */
package yk.jcommon.fastgeom;

import java.io.Serializable;

import static yk.jcommon.utils.Util.sqr;

/**
 * Quaternion
 *
 * @author Yuri Kravchik Created 15.02.2009
 */
public class Quaternionf implements Serializable {

//    public static final Quaternionf ZERO = new Quaternionf(0, 0, 0, 0);
    public static final Quaternionf ZERO_ROT = new Quaternionf(1, 0, 0, 0);

    /**
     *
     * @param angle radians
     * @param axis of length == 1
     * @return
     */
    public static Quaternionf fromAngleAxisFast(final float angle, final Vec3f axis) {
        return new Quaternionf((float) Math.cos(angle / 2), axis.mul((float) Math.sin(angle / 2)));
    }

    public final float a, i, j, k;

    public Quaternionf(final float a, final float i, final float j, final float k) {
        this.a = a;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Quaternionf() {
        this.a = 1;
        this.i = 0;
        this.j = 0;
        this.k = 0;
    }

    public Quaternionf(final float a, final Vec3f axis) {
        this.a = a;
        this.i = axis.getX();
        this.j = axis.getY();
        this.k = axis.getZ();
    }

    public Quaternionf(final Vec3f vector) {
        a = 0;
        i = vector.getX();
        j = vector.getY();
        k = vector.getZ();
    }

    public Quaternionf(
            float m00, float m01, float m02, 
            float m10, float m11, float m12, 
            float m20, float m21, float m22) {
        a = (float) (Math.sqrt(1.0 + m00 + m11 + m22) / 2.0);
        double w4 = (4.0 * a);
        i = (float) ((m21 - m12) / w4);
        j = (float) ((m02 - m20) / w4);
        k = (float) ((m10 - m01) / w4);
    }

    public Quaternionf conjug() {
        return new Quaternionf(a, -i, -j, -k);
    }

    public float getA() {
        return a;
    }

    public float getI() {
        return i;
    }

    public float getJ() {
        return j;
    }

    public float getK() {
        return k;
    }

    public Vec3f getXYZ() {
        return new Vec3f(i, j, k);
    }

    public float magnitude() {
        return (float) Math.sqrt(norm());
    }

    public Quaternionf mul(final Quaternionf q) {
        return new Quaternionf(a * q.a - i * q.i - j * q.j - k * q.k,//
                a * q.i + i * q.a + j * q.k - k * q.j,//
                a * q.j + j * q.a + k * q.i - i * q.k,//
                a * q.k + k * q.a + i * q.j - j * q.i);
    }

    public float dot(final Quaternionf q) {
        return a * q.a + i * q.i + j * q.j + k * q.k;
    }

    public Quaternionf div(final Quaternionf r) {
        float rr = (sqr(r.a) + sqr(r.i) + sqr(r.j) + sqr(r.k));
        float t0 = (r.a*a+r.i*i+r.j*j+r.k*k)/rr;
        float t1 = (r.a*a-r.i*i-r.j*j+r.k*k)/rr;
        float t2 = (r.a*a+r.i*i-r.j*j-r.k*k)/rr;
        float t3 = (r.a*a-r.i*i+r.j*j-r.k*k)/rr;
        return new Quaternionf(t0, t1, t2, t3);
    }

    public float norm() {
        return a * a + i * i + j * j + k * k;
    }

    public Quaternionf normalized() {
        final float mag = 1.0f / magnitude();
        return new Quaternionf(a * mag, i * mag, j * mag, k * mag);
    }

    /**
     * this 0 -> 1 to
     * @param to
     * @param blend
     * @return
     */
    public Quaternionf nlerp(Quaternionf to, float blend) {
        float dot = dot(to);
        float blendI = 1.0f - blend;

        if (dot < 0.0f) {
            return new Quaternionf(a * blendI - to.a * blend, i * blendI - to.i * blend, j * blendI - to.j * blend, k * blendI - to.k * blend).normalized();
        } else {
            return new Quaternionf(a * blendI + to.a * blend, i * blendI + to.i * blend, j * blendI + to.j * blend, k * blendI + to.k * blend).normalized();
        }
    }

    /**
     * This quaternion must be of 1 length
     * @param vector
     * @return
     */
    public Vec3f rotateFast(final Vec3f vector) {
        return conjug().mul(new Quaternionf(vector)).mul(this).getXYZ();
    }

    public Quaternionf rotSub(Quaternionf from) {
        return from.conjug().mul(this);
    }

    @Deprecated
    public Quaternionf sub(Quaternionf other) {
        return new Quaternionf(a - other.a, i - other.i, j - other.j, k - other.k);
    }

    @Deprecated
    public float length() {
        return (float) Math.sqrt(a*a+i*i+j*j + k*k);
    }

    @Deprecated
    public float dist(Quaternionf other) {
        return sub(other).length();
    }

    public Matrix4 toMatrix4() {
        float x2 = i * i;
        float y2 = j * j;
        float z2 = k * k;
        float xy = i * j;
        float xz = i * k;
        float yz = j * k;
        float wx = a * i;
        float wy = a * j;
        float wz = a * k;
        Matrix4 m = new Matrix4();
        m.set(0, 0, 1.0f - 2.0f * (y2 + z2));
        m.set(0, 1, 2.0f * (xy - wz));
        m.set(0, 2, 2.0f * (xz + wy));
        m.set(1, 0, 2.0f * (xy + wz));
        m.set(1, 1, 1.0f - 2.0f * (x2 + z2));
        m.set(1, 2, 2.0f * (yz - wx));
        m.set(2, 0, 2.0f * (xz - wy));
        m.set(2, 1, 2.0f * (yz + wx));
        m.set(2, 2, 1.0f - 2.0f * (x2 + y2));
        m.set(3, 3, 1);
        return m;
    }

    @Override
    public String toString() {
        return "Quaternionf{" +
                "a=" + a +
                ", i=" + i +
                ", j=" + j +
                ", k=" + k +
                '}';
    }
}

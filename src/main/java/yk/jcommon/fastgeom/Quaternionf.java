/**
 * File Quaternion.java
 * @author Yuri Kravchik
 * Created 15.02.2009
 */
package yk.jcommon.fastgeom;

import yk.jcommon.utils.MyMath;

import java.io.Serializable;

import static yk.jcommon.utils.MyMath.sqrt;
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
    //TODO rename or get rid
    public static Quaternionf fromAngleAxisFast(final float angle, final Vec3f axis) {
        float forPare_12 = angle / 2;
        float as = (float)(Math.sin(forPare_12));
        return new Quaternionf((float)(Math.cos(forPare_12)), (axis.x * as), (axis.y * as), (axis.z * as));
//        return new Quaternionf((float) Math.cos(angle / 2), axis.mul((float) Math.sin(angle / 2)));
    }


    public static Quaternionf fromAxes(Vec3f X, Vec3f Y, Vec3f Z) {
        return fromAxes(X.x, X.y, X.z, Y.x, Y.y, Y.z, Z.x, Z.y, Z.z);
    }
    //works like rotation FROM this basis, not TO this basis
    //use conjug() after this to get TO this basis
    //TODO rename TO AXES
    public static Quaternionf fromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        final float trace = xx + yy + zz;
        if (trace >= 0) {
            float s1 = sqrt(trace + 1);
            float s2 = 0.5f / s1;
            return new Quaternionf(0.5f * s1, (zy - yz) * s2, (xz - zx) * s2, (yx - xy) * s2);
        } else if (xx > yy && xx > zz) {
            float s1 = sqrt(1 + xx - yy - zz);
            float s2 = 0.5f / s1;
            return new Quaternionf((zy - yz) * s2, s1 * 0.5f, (yx + xy) * s2, (xz + zx) * s2);
        } else if (yy > zz) {
            float s1 = sqrt(1 + yy - xx - zz);
            float s2 = 0.5f / s1;
            return new Quaternionf((xz - zx) * s2, (yx + xy) * s2, s1 * 0.5f, (zy + yz) * s2);
        } else {
            float s1 = sqrt(1 + zz - xx - yy);
            float s2 = 0.5f / s1;
            return new Quaternionf((yx - xy) * s2, (xz + zx) * s2, (zy + yz) * s2, s1 * 0.5f);
        }
    }

    public static Quaternionf qToAxes(Vec3f X, Vec3f Y, Vec3f Z) {
        return qToAxes(X.x, X.y, X.z, Y.x, Y.y, Y.z, Z.x, Z.y, Z.z);
    }

    public static Quaternionf qToAxes(Vec3f X, Vec3f Y) {
        return qToAxesXY(X, Y);
    }

    public static Quaternionf qToAxesXY(Vec3f X, Vec3f Y) {
        X = X.normalized();
        Vec3f Z = X.crossProduct(Y).normalized();
        Y = Z.crossProduct(X).normalized();
        return qToAxes(X.x, X.y, X.z, Y.x, Y.y, Y.z, Z.x, Z.y, Z.z);
    }

    public static Quaternionf qToAxesZY(Vec3f Z, Vec3f Y) {
        Z = Z.normalized();
        Vec3f X = Y.crossProduct(Z).normalized();
        Y = Z.crossProduct(X).normalized();
        return qToAxes(X.x, X.y, X.z, Y.x, Y.y, Y.z, Z.x, Z.y, Z.z);
    }

    public static Quaternionf qToAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
            float trace = xx + yy + zz;
            if (trace >= 0) {
                float s1 = MyMath.sqrt(trace + 1);
                float s2 = 0.5f / s1;
                return ijka(-(zy - yz) * s2, -(xz - zx) * s2, -(yx - xy) * s2, 0.5f * s1);
            }
            else if (xx > yy && xx > zz) {
                float s1 = MyMath.sqrt(1 + xx - yy - zz);
                float s2 = 0.5f / s1;
                return ijka(-s1 * 0.5f, -(yx + xy) * s2, -(xz + zx) * s2, (zy - yz) * s2);
            }
            else if (yy > zz) {
                float s1 = MyMath.sqrt(1 + yy - xx - zz);
                float s2 = 0.5f / s1;
                return ijka(-(yx + xy) * s2, -s1 * 0.5f, -(zy + yz) * s2, (xz - zx) * s2);
            }
            else {
                float s1 = MyMath.sqrt(1 + zz - xx - yy);
                float s2 = 0.5f / s1;
                return ijka(-(xz + zx) * s2, -(zy + yz) * s2, -s1 * 0.5f, (yx - xy) * s2);
//                return new Quaternion((yx - xy) * s2, (xz + zx) * s2, (zy + yz) * s2, s1 * 0.5f);
            }


//            Quaternion q = new Quaternion();
//            q.w = Mathf.Sqrt( Mathf.Max( 0, 1 + xx + yy + zz ) ) / 2;
//            q.x = Mathf.Sqrt( Mathf.Max( 0, 1 + xx - yy - zz ) ) / 2;
//            q.y = Mathf.Sqrt( Mathf.Max( 0, 1 - xx + yy - zz ) ) / 2;
//            q.z = Mathf.Sqrt( Mathf.Max( 0, 1 - xx - yy + zz ) ) / 2;
//            q.x *= -Mathf.Sign( q.x * ( zy - yz ) );
//            q.y *= -Mathf.Sign( q.y * ( xz - zx ) );
//            q.z *= -Mathf.Sign( q.z * ( yx - xy ) );
//            return q;


//        return Quaternion.LookRotation(new Vector3(zx, zy, zz), new Vector3(yx, yy, yz));
    }

    public static Quaternionf toAngleAxis(float angle, Vec3f axis) {
        return Quaternionf.fromAngleAxisFast(angle, axis);
    }

    //http://lolengine.net/blog/2014/02/24/quaternion-from-two-vectors-final
    public static Quaternionf fromTwoVectors(Vec3f u, Vec3f v) {
        Vec3f w = u.crossProduct(v);
        Quaternionf result = new Quaternionf(u.dot(v), w.x, w.y, w.z);
        result.a += result.length();
        return result.normalized();
    }

    public static Quaternionf fromTwoUnitVectors(Vec3f u, Vec3f v) {
        float m = sqrt(2.f + 2.f * u.dot(v));
        Vec3f w = u.crossProduct(v).mul(1f / m);
        return new Quaternionf(0.5f * m, w.x, w.y, w.z);
    }

    public float a, i, j, k;

    @Deprecated //use ijka
    public Quaternionf(final float a, final float i, final float j, final float k) {
        this.a = a;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public static Quaternionf ijka(float i, float j, float k, float a) {
        Quaternionf result = new Quaternionf();
        result.i = i;
        result.j = j;
        result.k = k;
        result.a = a;
        return result;
    }

    public static Quaternionf aijk(float a, float i, float j, float k) {
        return ijka(i, j, k, a);
    }

    public Quaternionf() {
        this.a = 1;
        this.i = 0;
        this.j = 0;
        this.k = 0;
    }

    public Quaternionf(final float a, final Vec3f axis) {
        this.a = a;
        this.i = axis.x;
        this.j = axis.y;
        this.k = axis.z;
    }

    public Quaternionf(final Vec3f vector) {
        a = 0;
        i = vector.x;
        j = vector.y;
        k = vector.z;
    }

    public Quaternionf(final Quaternionf other) {
        a = other.a;
        i = other.i;
        j = other.j;
        k = other.k;
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

    public void mulSe(final Quaternionf q) {
        float newA = a * q.a - i * q.i - j * q.j - k * q.k;
        float newI = a * q.i + i * q.a + j * q.k - k * q.j;
        float newJ = a * q.j + j * q.a + k * q.i - i * q.k;
        float newK = a * q.k + k * q.a + i * q.j - j * q.i;
        this.a = newA;
        this.i = newI;
        this.j = newJ;
        this.k = newK;
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

        Quaternionf result = new Quaternionf();
        if (dot < 0.0f) {
            result.setAijk(a * blendI - to.a * blend, i * blendI - to.i * blend, j * blendI - to.j * blend, k * blendI - to.k * blend);
        } else {
            result.setAijk(a * blendI + to.a * blend, i * blendI + to.i * blend, j * blendI + to.j * blend, k * blendI + to.k * blend);
        }
        return result.normalized();
    }

    /**
     * This quaternion must be of 1 length
     * @param vector
     * @return
     */
    @Deprecated //not that kind of rotation
    public Vec3f rotateFast(final Vec3f vector) {//TODO get rid
        return conjug().mul(new Quaternionf(vector)).mul(this).getXYZ();
    }

    public Vec3f rotate(final Vec3f vector) {

        float newVar_7 = (-(this.i * vector.x) + -(this.j * vector.y) + -(this.k * vector.z));
        float newVar_22 = ((this.a * vector.x) + (this.j * vector.z) + -(this.k * vector.y));
        float newVar_37 = ((this.a * vector.y) + (this.k * vector.x) + -(this.i * vector.z));
        float newVar_52 = ((this.a * vector.z) + (this.i * vector.y) + -(this.j * vector.x));
        float newVar_69 = -this.i;
        float newVar_71 = -this.j;
        float newVar_73 = -this.k;
        return new Vec3f(
                ((newVar_7 * newVar_69) + (newVar_22 * this.a) + (newVar_37 * newVar_73) + -(newVar_52 * newVar_71)),
                ((newVar_7 * newVar_71) + (newVar_37 * this.a) + (newVar_52 * newVar_69) + -(newVar_22 * newVar_73)),
                ((newVar_7 * newVar_73) + (newVar_52 * this.a) + (newVar_22 * newVar_71) + -(newVar_37 * newVar_69)));

//        return this.mul(new Quaternionf(vector)).mul(conjug()).getXYZ();
    }

//    public Quaternionf rotSub(Quaternionf from) {
//        return from.conjug().mul(this);
//    }

    @Deprecated
    public Quaternionf sub(Quaternionf other) {
        return new Quaternionf(a - other.a, i - other.i, j - other.j, k - other.k);
    }

    public float length() {
        return magnitude();
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
        m.set(1, 0, 2.0f * (xy - wz));
        m.set(2, 0, 2.0f * (xz + wy));
        m.set(0, 1, 2.0f * (xy + wz));
        m.set(1, 1, 1.0f - 2.0f * (x2 + z2));
        m.set(2, 1, 2.0f * (yz - wx));
        m.set(0, 2, 2.0f * (xz - wy));
        m.set(1, 2, 2.0f * (yz + wx));
        m.set(2, 2, 1.0f - 2.0f * (x2 + y2));
        m.set(3, 3, 1);
        return m;
    }

    public void fillMatrix4(Matrix4 m) {
        float x2 = i * i;
        float y2 = j * j;
        float z2 = k * k;
        float xy = i * j;
        float xz = i * k;
        float yz = j * k;
        float wx = a * i;
        float wy = a * j;
        float wz = a * k;
        m.set(0, 0, 1.0f - 2.0f * (y2 + z2));
        m.set(1, 0, 2.0f * (xy - wz));
        m.set(2, 0, 2.0f * (xz + wy));
        m.set(0, 1, 2.0f * (xy + wz));
        m.set(1, 1, 1.0f - 2.0f * (x2 + z2));
        m.set(2, 1, 2.0f * (yz - wx));
        m.set(0, 2, 2.0f * (xz - wy));
        m.set(1, 2, 2.0f * (yz + wx));
        m.set(2, 2, 1.0f - 2.0f * (x2 + y2));
        m.set(3, 3, 1);
    }

    public Matrix3 toMatrix3() {
        float x2 = i * i;
        float y2 = j * j;
        float z2 = k * k;
        float xy = i * j;
        float xz = i * k;
        float yz = j * k;
        float wx = a * i;
        float wy = a * j;
        float wz = a * k;
        Matrix3 m = new Matrix3();
        m.set(1, 1, 1.0f - 2.0f * (y2 + z2));
        m.set(2, 1, 2.0f * (xy - wz));
        m.set(3, 1, 2.0f * (xz + wy));
        m.set(1, 2, 2.0f * (xy + wz));
        m.set(2, 2, 1.0f - 2.0f * (x2 + z2));
        m.set(3, 2, 2.0f * (yz - wx));
        m.set(1, 3, 2.0f * (xz - wy));
        m.set(2, 3, 2.0f * (yz + wx));
        m.set(3, 3, 1.0f - 2.0f * (x2 + y2));
        return m;
    }

    //     https://github.com/Kent-H/blue3D/blob/master/Blue3D/src/blue3D/type/QuaternionF.java
    public Quaternionf ln() {
        Quaternionf result = new Quaternionf();
        float r = (float) Math.sqrt(this.i * this.i + this.j * this.j + this.k * this.k);
        float t = r > 0.00001f ? (float) Math.atan2(r, this.a) / r : 0.0f;
        return ijka(this.i * t, this.j * t, this.k * t,
                0.5f * (float) Math.log(this.a * this.a + this.i * this.i + this.j * this.j + this.k * this.k));
    }

    public Quaternionf exp() {
        float r = (float) Math.sqrt(this.i * this.i + this.j * this.j + this.k * this.k);
        float et = (float) Math.exp(this.a);
        float s = r >= 0.00001f ? et * (float) Math.sin(r) / r : 0f;
        return ijka(this.i * s, this.j * s, this.k * s, et * (float) Math.cos(r));
    }

    public Quaternionf pow(float n) {
        return this.ln().scale(n).exp();
    }

    public Quaternionf rotSub(Quaternionf from) {
        return this.mul(from.conjug());
    }

    public Quaternionf scale(float scale) {
        return ijka(this.i * scale, this.j * scale, this.k * scale, this.a * scale);
    }

    public Vec3f imaginary() {
        return new Vec3f(i, j, k);
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

    public static void copy(Quaternionf from, Quaternionf to) {
        to.a = from.a;
        to.i = from.i;
        to.j = from.j;
        to.k = from.k;
    }

    public void copyFrom(Quaternionf from) {
        this.a = from.a;
        this.i = from.i;
        this.j = from.j;
        this.k = from.k;
    }

    public void setAijk(float a, float i, float j, float k) {
        this.a = a;
        this.i = i;
        this.j = j;
        this.k = k;
    }
}

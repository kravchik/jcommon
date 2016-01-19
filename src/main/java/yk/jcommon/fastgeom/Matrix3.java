package yk.jcommon.fastgeom;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 06/01/15
 * Time: 19:26
 */
public class Matrix3 {
    private static final int S = 3;

    public float[] data = new float[S*S];

    public void set(int i, int j, float d) {
        data[(i-1) * S + j-1] = d;
    }

    public float get(int i, int j) {
        return data[(i-1) * S + j-1];
    }

    public static Matrix3 setIdentity(Matrix3 m) {
        m.data[0 * S + 0] = 1.0f;
        m.data[0 * S + 1] = 0.0f;
        m.data[0 * S + 2] = 0.0f;
        m.data[1 * S + 0] = 0.0f;
        m.data[1 * S + 1] = 1.0f;
        m.data[1 * S + 2] = 0.0f;
        m.data[2 * S + 0] = 0.0f;
        m.data[2 * S + 1] = 0.0f;
        m.data[2 * S + 2] = 1.0f;
        return m;
    }

    public Matrix3 setIdentity() {
        return setIdentity(this);
    }

    public Matrix3 store(FloatBuffer buf) {
        buf.put(data);
        return this;
    }

    public Matrix3 multiply(Matrix3 by) {
        Matrix3 result = new Matrix3();

        for(int i = 1;i <= S;i++){
            for(int j = 1;j <= S;j++){
                float r = 0;
                for(int k = 1;k <= S;k++) r += get(i, k) * by.get(k, j);
                result.set(i, j, r);
            }
        }
        return result;
    }

    public Matrix3 invert() {
        Matrix3 result = new Matrix3();
        if (!gluInvertMatrix(result)) throw new RuntimeException("can't invert matrix");
        return result;
    }

    public float getRound(int i, int j) {
        i--;
        j--;
        if (i < 0) i = S-((-i)%S);
        else i = i % S;
        if (j < 0) j = S-((-j)%S);
        else j = j % S;
        return data[S * (i%S) + (j%S)];
    }

    public float det() {
        float result = 0;
        for (int k = 1; k <= S; k++) {
            float r1 = 1;
            float r2 = 1;
            for (int l = 1; l <= S; l++) {
                r1 *= getRound(k + l, l);
                r2 *= getRound(k - l, l);
            }
            result = result + r1 - r2;
        }
        return result;
    }


    private static float solve22(List<Float> ff) {
        return ff.get(0) * ff.get(3) - ff.get(1) * ff.get(2);
    }

    private List<Float> getSub(int i, int j) {
        return al(getRound(i + 1, j + 1) , getRound(i + 1, j + 2), getRound(i + 2, j + 1), getRound(i + 2, j + 2));
    }

    //TODO code-generate super-optimized
    private boolean gluInvertMatrix(Matrix3 output)//http://stackoverflow.com/questions/983999/simple-3x3-matrix-inverse-code-c
    {
        for (int i = 1; i <= S; i++) for (int j = 1; j <= S; j++) output.set(j/*!*/, i/*!*/, solve22(getSub(i, j)));
        float d = det();
        if (d == 0) return false;
        d = 1f / d;
        for (int i = 0; i < S*S; i++) output.data[i] *= d;
        return true;
    }

    public Matrix3 transpose() {
        Matrix3 result = new Matrix3();
        for (int i = 1; i <= S; i++) for (int j = 1; j <= S; j++) result.set(j, i, get(i, j));
        return result;
    }

    public Vec3f multiply(Vec3f v) {
        return new Vec3f(v.x * get(1, 1) + v.x * get(1, 2) + v.x * get(1, 3),
                v.y * get(2, 1) + v.y * get(2, 2) + v.y * get(2, 3),
                v.z * get(3, 1) + v.z * get(3, 2) + v.z * get(3, 3));
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

}

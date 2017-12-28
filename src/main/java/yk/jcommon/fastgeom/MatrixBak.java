package yk.jcommon.fastgeom;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 06/01/15
 * Time: 19:26
 */
public class MatrixBak {
    public final int w, h;
    public float[] data;

    public MatrixBak(int w, int h) {
        this.w = w;
        this.h = h;
        data = new float[w * h];
    }

    public void set(int i, int j, float d) {
        data[(i-1) * w + j-1] = d;
    }

    public float get(int i, int j) {
        return data[(i-1) * w + j-1];
    }

    public static MatrixBak setIdentity(MatrixBak m) {
//        if (m.w != m.h) throw new RuntimeException("can't set identity to " + m);
        for (int i = 0; i < m.h; i++) for (int j = 0; j < m.w; j++) m.set(i, j, i == j ? 1 : 0);
        return m;
    }

    public MatrixBak setIdentity() {
        return setIdentity(this);
    }

    //public Matrix store(FloatBuffer buf) {
    //    buf.put(data);
    //    return this;
    //}

    public MatrixBak multiply(MatrixBak by) {
  //      if (w != by.h || h != by.w) throw new RuntimeException("can't multiply " + this + " by " + by);
        MatrixBak result = new MatrixBak(w, by.h);
        for(int i = 1;i <= w;i++){
            for(int j = 1;j <= by.h;j++){
                float r = 0;
                for(int k = 1;k <= h;k++) r += get(i, k) * by.get(k, j);
                result.set(i, j, r);
            }
        }
        return result;
    }

    //public Matrix invert() {
    //    if (w != h) throw new RuntimeException("not implemented for " + this);
    //    Matrix result = new Matrix(w, h);
    //    if (!gluInvertMatrix(result)) throw new RuntimeException("can't invert matrix");
    //    return result;
    //}
    //
    //public float getRound(int i, int j) {
    //    i--;
    //    j--;
    //    if (i < 0) i = S-((-i)%S);
    //    else i = i % S;
    //    if (j < 0) j = S-((-j)%S);
    //    else j = j % S;
    //    return data[S * (i%S) + (j%S)];
    //}
    //
    //public float det() {
    //    float result = 0;
    //    for (int k = 1; k <= S; k++) {
    //        float r1 = 1;
    //        float r2 = 1;
    //        for (int l = 1; l <= S; l++) {
    //            r1 *= getRound(k + l, l);
    //            r2 *= getRound(k - l, l);
    //        }
    //        result = result + r1 - r2;
    //    }
    //    return result;
    //}
    //
    //
    //private static float solve22(List<Float> ff) {
    //    return ff.get(0) * ff.get(3) - ff.get(1) * ff.get(2);
    //}
    //
    //private float solve() {
    //    if (w == 1 && h == 1) return get(1, 1);
    //
    //}
    //
    //private Matrix getSub(int r, int c) {
    //    if (w == 1 || h == 1) throw new RuntimeException("can't get sub for " + this);
    //    Matrix result = new Matrix(w - 1, h - 1);
    //    for (int i = r + 1; i < r + w; i++) {
    //        for (int j = c + 1; j < c + h; j++) {
    //            result.set(i - r, j - c, getRound(i, j));
    //        }
    //    }
    //    return result;
    //}
    //
    ////TODO code-generate super-optimized
    //private boolean gluInvertMatrix(Matrix output)//http://stackoverflow.com/questions/983999/simple-3x3-matrix-inverse-code-c
    //{
    //    for (int i = 1; i <= S; i++) for (int j = 1; j <= S; j++) output.set(j/*!*/, i/*!*/, solve22(getSub(i, j)));
    //    float d = det();
    //    if (d == 0) return false;
    //    d = 1f / d;
    //    for (int i = 0; i < S*S; i++) output.data[i] *= d;
    //    return true;
    //}
    //
    //public Matrix transpose() {
    //    Matrix result = new Matrix();
    //    for (int i = 1; i <= S; i++) for (int j = 1; j <= S; j++) result.set(j, i, get(i, j));
    //    return result;
    //}
}

package yk.jcommon.fastgeom;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 06/01/15
 * Time: 19:26
 */
public class Matrix {
    public final int w, h;
    public float[] data;

    public Matrix(int w, int h) {
        this.w = w;
        this.h = h;
        data = new float[w * h];
    }

    public void set(int row, int column, float d) {
        data[(row-1) * w + column-1] = d;
    }

    public void setRow(int row, float... nn) {
        for (int i = 0; i < nn.length; i++) {
            set(row, i+1, nn[i]);
        }
    }

    public float get(int row, int column) {
        return data[(row-1) * w + column-1];
    }

    public static Matrix setIdentity(Matrix m) {
        for (int i = 0; i < m.h; i++) for (int j = 0; j < m.w; j++) m.set(i, j, i == j ? 1 : 0);
        return m;
    }

//    public Matrix setIdentity() {
//        return setIdentity(this);
//    }

    //public Matrix store(FloatBuffer buf) {
    //    buf.put(data);
    //    return this;
    //}

    public Matrix multiply(Matrix by) {
        //      if (w != by.h || h != by.w) throw new RuntimeException("can't multiply " + this + " by " + by);
        Matrix result = new Matrix(w, by.h);
        for(int i = 1;i <= w;i++){
            for(int j = 1;j <= by.h;j++){
                float r = 0;
                for(int k = 1;k <= h;k++) r += get(i, k) * by.get(k, j);
                result.set(i, j, r);
            }
        }
        return result;
    }

    public Matrix minor(int row, int column) {
        Matrix result = new Matrix(w - 1, h - 1);
        for (int cc = 1; cc <= w; cc++) {
            for (int rr = 1; rr <= h; rr++) {
                if (cc == column || rr == row) continue;
                int ccc;
                if (cc < column) ccc = cc;
                else ccc = cc - 1;
//                int ccc = cc < column ? cc : cc - 1;
                int rrr;
                if (rr < row) rrr = rr;
                else rrr = rr - 1;
//                int rrr = rr < row ? rr : rr - 1;
                result.set(rrr, ccc, get(rr, cc));
            }
        }
        return result;
    }

//    public float det() {
////        if (w != h) BadException.die("can' calc det on non-square matrix");
//        if (w == 1) return get(1, 1);
//        if (w == 2) return get(1, 1) * get(2, 2) - get(1, 2) * get(2, 1);
//        float result = 0;
//        for (int c = 1; c <= w; c++) {
//            float sign = (float) pow(-1, c + 1);
//            float a = get(1, c);
//            Matrix minor = minor(1, c);
//            float det = minor.det();
//            result += a * sign * det;
//        }
//        return result;
//    }

//    public static void main(String[] args) {
//        //TODO tests
//        Matrix m = new Matrix(1, 1);
//        m.set(1, 1, 5);
//        System.out.println("5 " + m.det());
//
//        m = new Matrix(4, 4);
//        m.setRow(1, 3, -3, -5, 8);
//        m.setRow(2, -3, 2, 4, -6);
//        m.setRow(3, 2, -5, -7, 5);
//        m.setRow(4, -4, 3, 5, -6);
//
//        System.out.println("18 " + m.det());
//    }

    public String toStringLn() {
        String result = "";
        for (int jj = 1; jj <= h; jj++) {
            result += "\n";
            for (int ii = 1; ii <= w; ii++) {
                result += " " + get(jj, ii);
            }
        }
        return result + "\n";
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

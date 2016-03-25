package yk.jcommon.loader;

import yk.jcommon.fastgeom.Vec2f;
import yk.jcommon.fastgeom.Vec3f;
import yk.jcommon.utils.IO;
import yk.jcommon.utils.Util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 2/2/14
 * Time: 2:23 PM
 */
public class WavefrontObj {
    public List<Vec3f> vertices = list();
    public List<Vec3f> normals = list();
    public List<Vec2f> texCoords = list();
    public List<List<FaceV>> faces = list();

    private static DecimalFormat F = new DecimalFormat("#.####");

    public static void main(String[] args) {
        readFromFile("Man_bolvanka_new.obj").saveToFile("test.obj");
    }

    public static WavefrontObj readFromFile(String fileName) {
        WavefrontObj result = new WavefrontObj();
        for (String s1 : IO.readFile(fileName).split("\n")) {
            if (s1.startsWith("v ")) result.vertices.add(parseVec(s1.split("v")[1].trim()));
            if (s1.startsWith("vt ")) result.texCoords.add(parseVec2(s1.split("vt")[1].trim()));
            if (s1.startsWith("vn ")) result.normals.add(parseVec(s1.split("vn")[1].trim()));
            if (s1.startsWith("f ")) result.faces.add(parseFaces(s1.split("f")[1].trim()));
        }
        return result;
    }

    private static List<FaceV> parseFaces(String f) {
        List<FaceV> result = list();
        for (String s : f.split(" ")) result.add(parseFace(s));
        return result;

    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            for (Vec3f v : vertices) pw.println("v  " + toString(v));
            pw.println();
            for (Vec2f v : texCoords) pw.println("vt " + toString(v));
            pw.println();
            for (Vec3f v : normals) pw.println("vn " + toString(v));
            pw.println();
            for (List<FaceV> face : faces) pw.println("f " + Util.join(face, " "));
            pw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("error saving wavefront object", e);
        }
    }

    public static String toString(Vec3f v) {
        return F.format(v.x) + " " + F.format(v.y) + " " + F.format(v.z);
    }

    public static String toString(Vec2f v) {
        return F.format(v.x) + " " + F.format(v.y);
    }



    private static FaceV parseFace(String f) {
        String[] ss = f.trim().split("/");
        FaceV result = new FaceV();
        result.v = (int) parse(ss[0]) - 1;
        if (ss.length > 1 && ss[1].length() > 0) result.t = (int) parse(ss[1]) - 1;
        if (ss.length > 2 && ss[2].length() > 0) result.n = (int) parse(ss[2]) - 1;
        return result;
    }

    public static Vec2f parseVec2(String s) {
        String[] vs = s.split(" ");
        return new Vec2f(parse(vs[0]), parse(vs[1]));
    }

    public static Vec3f parseVec(String s) {
        String[] vs = s.split(" ");
        return new Vec3f(parse(vs[0]), parse(vs[1]), parse(vs[2]));
    }

    public static float parse(String s) {
        return Float.parseFloat(s.trim());
    }

    public static class FaceV {
        public int v = -1;
        public int t = -1;
        public int n = -1;

        public FaceV() {
        }

        public FaceV(int v, int n) {
            this.v = v;
            this.n = n;
        }

        @Override
        public String toString() {
            if (t == -1 && n == -1) return v + 1 + "";
            if (t == -1) return v + 1 + "//" + (n + 1);
            if (n == -1) return v + 1 + "/" + (t + 1);
            return (v + 1) + "/" + (t + 1) + "/" + (n + 1);
        }
    }

}

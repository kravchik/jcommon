package yk.jcommon.utils;

import java.lang.reflect.Field;
import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 10/8/14
 * Time: 11:13 PM
 */
public class Scripts {

    public static void main(String[] args) {
        genCopier("/home/yuri/1/myproject/jcommon/src/utils/Scripts.java", Test.class);
    }

    public static void genCopier(String file, Class<?> clazz) {

        List<String> lines = al();
        String t = clazz.getSimpleName();
        lines.add("/*auto*/public " + t + " copy(){");
        lines.add("/*auto*/    " + t + " result = new " + t + "();");
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            lines.add("/*auto*/    result." + fieldName + " = " + fieldName + ";");
        }
        lines.add("/*auto*/    return result;");
        lines.add("/*auto*/}");

        for (String line : lines) {
            System.out.println(line);
        }

        IO.writeFile(file, Util.insertLines(IO.readFile(file), "copier", lines));

    }

    public static class Test {
        public float a;
        public Test t;

        //copier auto generated text
public Test copy(){
    Test result = new Test();
    result.a = a;
    result.t = t;
    return result;
}

//copier auto generated text

    }



}

package yk.jcommon.utils;

import java.util.List;
import java.util.Random;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/11/14
 * Time: 3:18 PM
 */
public class Psw {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            gen();
            System.out.println();
        }
    }

    private static void gen() {
        Random rnd = new Random(System.nanoTime());

        String numbers = "0123456789";
        //String NUMBERS = "!@#$%^&*()";
        String letters = low();
        String LETTERS = high();

        List<String> ss = list(
//                numbers,
                //NUMBERS,
                letters,
                LETTERS
        );
        String s = "";
        for (String sss : ss) s += sss;

        List<String> chars = list();
        for (int i = 0; i < 16; i++) chars.add(s.charAt(rnd.nextInt(s.length())) + "");

        System.out.println();
//        for (int i = 0; i < chars.size(); i++) {
//            String c = chars.get(i);
//            if (numbers.contains(c)) {
//                System.out.print("_");
//            } else {
//                System.out.print(" ");
//            }
//            if (i % 4 == 3) System.out.print(" ");
//        }
        System.out.println();
        for (int i = 0; i < chars.size(); i++) {
            String c = chars.get(i);
            System.out.print(c);
            if (i % 4 == 3) System.out.print(" ");
        }
        System.out.print("                            ");
        for (int i = 0; i < chars.size(); i++) {
            String c = chars.get(i);
            System.out.print(c);
            if (i % 4 == 3) System.out.print(" ");
        }
        System.out.println();
//        for (int i = 0; i < chars.size(); i++) {
//            String c = chars.get(i);
//            if (rnd.nextBoolean()) {
//                System.out.print("*");
//            } else {
//                System.out.print(" ");
//            }
//            if (i % 4 == 3) System.out.print(" ");
//        }
    }

    private static String high() {
        String s = "";
        for (char ch = 'A'; ch <= 'Z'; ++ch) s += ch;
        return s;
    }

    private static String low() {
        String s = "";
        for (char ch = 'a'; ch <= 'z'; ++ch) s += ch;
        return s;
    }


}

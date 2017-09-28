package yk.jcommon.utils;

import yk.jcommon.collections.YList;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/11/14
 * Time: 3:18 PM
 */
//https://security.stackexchange.com/questions/15653/recommend-length-for-wi-fi-psk
//One very important caveat: There are other issues as well, beyond password length.
// It is very important that you turn off WPS, as WPS has major security holes.
// Also, I recommend that you use WPA2; avoid WPA-TKIP, and never use WEP.
public class Psw {
    public static YList<String> EXCLUDE = al("O", "0", "I", "l");


    public static void main(String[] args) throws NoSuchAlgorithmException {
        //numbers + letters + LETTERS
        //    8 symbols = good for wifi, reasonable for desktop
        //   12 symbols = too good for wifi, good for desktop
        //   16 symbols = too good for wifi, good for big finances

        //Random rnd = new Random(System.nanoTime());
        SecureRandom rnd = new SecureRandom();
        //SecureRandom rnd = SecureRandom.getInstanceStrong();// hangs on my system
        for (int i = 0; i < 5; i++) {
            gen(rnd, 8, ".");
            System.out.println();
        }
    }

    private static void gen(SecureRandom random, int length, String separator) throws NoSuchAlgorithmException {


        String numbers = "0123456789";
        String NUMBERS = "!@#$%^&*()";
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String LETTERS = letters.toUpperCase();

        List<String> ss = al(
                numbers,
                //NUMBERS,
                letters,
                LETTERS
        );
        String s = "";
        for (String sss : ss) s += sss;
        for (String e : EXCLUDE) s = s.replace(e, "");

        List<String> chars = al();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(s.length());
            chars.add(s.charAt(index) + "");
        }

        System.out.print(toString(chars, separator));
//        System.out.println();
//        System.out.print(toString(chars, separator) + "                            " + toString(chars, separator));
//        System.out.println();
    }
    private static String toString(List<String> chars, String separator) {
        String result = "";
        for (int i = 0; i < chars.size(); i++) {
            String c = chars.get(i);
            result += c;
            if (i % 4 == 3 && i < chars.size() - 1) result += separator;
        }
        return result;
    }

}

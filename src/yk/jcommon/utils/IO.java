package yk.jcommon.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Kravchik Yuri
 * Date: 11.04.2012
 * Time: 8:52 PM
 */
public class IO {

    public static InputStream stringToStream(String s) {
        try {
            return new ByteArrayInputStream(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream stringToStream(String s, String encoding) {
        try {
            return new ByteArrayInputStream(s.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream fileToStream(String file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String l;
            StringBuilder sb = new StringBuilder();
            while((l = br.readLine()) != null) {
                sb.append(l).append("\n");
            }
            return sb.toString();

        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static void writeFile(String file, String u) {
        try {
            FileWriter f = new FileWriter(file);
            f.write(u);
            f.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

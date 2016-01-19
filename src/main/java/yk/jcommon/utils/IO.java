package yk.jcommon.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

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

    //TODO script utils
    public static void replaceLines(String fileName, String prefix, List<String> lines) {
        writeFile(fileName, Util.insertLines(IO.readFile(fileName), prefix, lines));
    }

    public static String readResource(String path) {
        String content = resourceAsString(path);
        if (content == null) {
            throw BadException.die("File " + path + " not found");
        }
        return content;
    }

    public static String resourceAsString(String name) {
        return streamToString(resourceAsStream(name));
    }

    public static InputStream resourceAsStream(String name) {
        return IO.class.getClassLoader().getResourceAsStream(name);
    }

    public static String streamToString(InputStream in) {
        if (in == null) return null;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String l;
        StringBuilder sb = new StringBuilder();
        try {
            while((l = br.readLine()) != null) sb.append(l).append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

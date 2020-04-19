package yk.jcommon.utils;

import yk.jcommon.collections.YList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

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
            return readFile(new FileReader(file));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static String readFile(File file) {
        try {
            return readFile(new FileReader(file));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static YList<String> readStrings(String file) {
        try {
            return readStrings(new FileReader(file));
        } catch (IOException e) {
            throw new Error(e);
        }
    }
    private static String readFile(FileReader fileReader) throws IOException {
        BufferedReader br = new BufferedReader(fileReader);
        String l;
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        while((l = br.readLine()) != null) {
            if (!first) {
                sb.append("\n");
            }
            first = false;
            sb.append(l);
        }
        return sb.toString();
    }

    public static YList<String> readStrings(File file) {
        try {
            return readStrings(new FileReader(file));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static YList<String> readStrings(FileReader fileReader) throws IOException {
        BufferedReader br = new BufferedReader(fileReader);
        YList<String> result = al();
        String l;
        while((l = br.readLine()) != null) {
            result.add(l);
        }
        return result;
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

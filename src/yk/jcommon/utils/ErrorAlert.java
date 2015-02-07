package yk.jcommon.utils;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 1/30/14
 * Time: 9:26 PM
 */
public class ErrorAlert extends JFrame {

    public ErrorAlert(Throwable t) throws HeadlessException {//TODO add automatic send via http post
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        JTextArea textArea = new JTextArea(
                "ERROR REPORT\nAlpha version\npaperpointer.com\n" +
                "Please, send this report to kravchiky@gmail.com\n\n" + sw.toString(), 30, 50);
        add(textArea);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}

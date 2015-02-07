package yk.jcommon.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/29/14
 * Time: 11:22 PM
 */
public class Viewer extends JPanel implements MouseListener, MouseMotionListener {//TODO don't extends (because of list() )
    public int w = 640;
    public int h = 480;
    public BufferedImage result;
    public final List<JLabel> labels = new ArrayList<>();

    public static void main(String[] args) {
        new Viewer(640, 480);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(result, 0, 0, null);
    }

    public Viewer(int w, int h) {

        this.w = w;
        this.h = h;
        result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        JFrame frame = new JFrame("test");
        addMouseListener(this);
        setPreferredSize(new Dimension(w, h));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (int i = 0; i < 10; i++) {
            final JLabel label = new JLabel("label 1");
            label.setForeground(Color.BLUE);
            add(label);
            labels.add(label);
        }
//        canvas.setSize(640, 480);
        frame.addMouseMotionListener(this);
        frame.getContentPane().add(this);
        frame.pack();
        //frame.setSize(w, h);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        System.out.println("created");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        labels.get(0).setText(x + " " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        labels.get(0).setText(e.getX() + " " + e.getY());
        labels.get(0).repaint();
    }
}

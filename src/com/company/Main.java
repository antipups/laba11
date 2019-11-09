package com.company;

import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Main {
    public static void main(String[] args) {
        final int width = 512;  // размеры окна, ширина
        final int height = 512;  // размеры окна, высота
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        final double A = 1;     // коэфициент
        final double scale = 128;   // шаг для рисовки(пискели)
        final double zoom = 50; // зум, чем больше тем виднее
        final double step = 1 / scale;  // шаг для рисовки(пискели)
        Point last = null;      // с начала до конца
        final Point origin = new Point(width/2, height/2);

        for (double t = 0; t <= 4*PI; t+= step) {   // рисунок верхней части
            final double r = zoom * polarFunction(t, A);
            final int x = (int)round(r * cos(t));
            final int y = (int)round(r * sin(t));
            Point next = new Point(x, -y);
            if (last != null) {
                g.drawLine(origin.x + last.x, origin.y + last.y,
                        origin.x + next.x, origin.y + next.y);
            }
            last = next;
        }

        for (double t = 0; t <= 4*PI; t+= step) {   // отрцательной стороны
            final double r = zoom * polarFunction(t, A);
            final int x = (int)round(r * cos(t));
            final int y = (int)round(r * sin(t));
            Point next = new Point(-x, y);
            if (last != null) {
                g.drawLine(origin.x + last.x, origin.y + last.y,
                        origin.x + next.x, origin.y + next.y);
            }
            last = next;
        }
        // далее настройка окна
        JFrame frame = new JFrame("testit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static double polarFunction(double t, double A) {    // функция графика Ферма
        return A * sqrt(t);
    }
}
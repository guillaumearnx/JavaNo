package fr.arnoux23u.javano;

import fr.arnoux23u.javano.mvc.panels.Launcher;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //Creation de la fenetre
        JFrame f = new JFrame("JavaNO - Launcher");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Launcher l = new Launcher(f);
        f.setLayout(new BorderLayout());
        l.setPreferredSize(new Dimension(400, 400));
        f.setContentPane(l);
        f.requestFocusInWindow();
        f.pack();
        f.setVisible(true);
    }
}

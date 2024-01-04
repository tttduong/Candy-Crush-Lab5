package Fes Challenge;

import javax.swing.*;
import java.awt.*;


public class CandyCrush extends JFrame {
    public static void main(String[] args) {
        try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new CandyCrush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   
    public CandyCrush() {
        PlayMouse panel = new PlayMouse( new Rectangle(320 - 30, 280, 250, 50),new Rectangle(320 - 30, 390, 250, 50),this);
        panel.setPreferredSize(new Dimension(960, 640));

        setVisible(true);
        add(panel, BorderLayout.CENTER);
        pack();
        // Set the content pane layout to null

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        sound.playSound("src/resources/Sound/welcome.wav");
        setResizable(false);
        setTitle(Constant.TITLE);
        // setIconImage(getImage());
        setLocationRelativeTo(null);
        // Add the background to the content pane
        setVisible(true);
    }
}


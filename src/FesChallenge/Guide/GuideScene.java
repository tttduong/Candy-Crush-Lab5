package src.FesChallenge.Guide;

import src.FesChallenge.Controls.Constant;

import javax.swing.*;
import java.awt.*;

public class GuideScene extends JFrame {
    private Guide guide;
    private JFrame jFrame;

    public GuideScene() {
        super(Constant.TITLE);
        guide = new Guide(new Rectangle(800, 500, 200, 30), this);
        guide.setPreferredSize(new Dimension(960, 540));
        setVisible(true);
        getContentPane().add(guide, BorderLayout.CENTER);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(Constant.TITLE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
package src.FesChallenge;

import javax.swing.*;
import java.awt.*;

public class LoseScene extends JFrame {
    MatchBoard matchBoard;
    GameThread gameThread;
    StatusPanel statusPanel;

    public LoseScene(MatchBoard matchBoard, GameThread gameThread) {
        this.matchBoard = matchBoard;
        this.gameThread = gameThread;
        LosePanel lose = new LosePanel(this, new Rectangle(270, 420, 200, 100), new Rectangle(470, 420, 200, 100));
        lose.setPreferredSize(new Dimension(960, 540));
        setVisible(true);
        add(lose, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setTitle(Constant.TITLE);

        setLocationRelativeTo(null);
    }
}

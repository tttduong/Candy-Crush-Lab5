package src.FesChallenge.Final_Scene;



import src.FesChallenge.Body_Scence.MatchBoard;
import src.FesChallenge.Body_Scence.StatusPanel;
import src.FesChallenge.Controls.Constant;
import src.FesChallenge.Controls.GameThread;
import src.FesChallenge.Final_Scene.NextMouse;

import javax.swing.*;
import java.awt.*;

public class NextScene extends JFrame {

    MatchBoard matchBoard;
    GameThread gameThread;
    StatusPanel statusPanel;

//    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("icon/icon.png"));


    public NextScene(MatchBoard matchBoard, GameThread gameThread) {
        this.matchBoard = matchBoard;
        this.gameThread=gameThread;

        NextMouse panel = new NextMouse(new Rectangle(270, 420, 200, 100), new Rectangle(470, 420, 200, 100), this);
        panel.setPreferredSize(new Dimension(960, 540));

        setVisible(true);
        add(panel, BorderLayout.CENTER);
        pack();
        // Set the content pane layout to null

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(Constant.TITLE);
        setLocationRelativeTo(null);


    }

    public void NextGame() {
        int currentLevel = gameThread.getLevel();
        int nextLevel = currentLevel + 1;
        int goal = 50 * nextLevel;
        int speed = 1000 - 200 * currentLevel;
        if (speed < 300) {
            speed = 300;
        }
        this.dispose();

//        gameThread.stopGame();
        System.exit(0);

        gameThread.interrupt(); // Interrupt the thread to exit any sleeping states
        try {
            gameThread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameThread = new GameThread(matchBoard,statusPanel);
        matchBoard.requestFocus();
        gameThread.startGame();
        gameThread.restart();

    }
}

package src.FesChallenge.Body_Scence;

import src.FesChallenge.Body_Scence.StatusPanel;
import src.FesChallenge.Controls.MatchPanel;

import javax.swing.JFrame;
import java.awt.*;

public class MainBoard extends JFrame {
    private MatchPanel matchPanel;// Object matchPanel
    private StatusPanel statusPanel;//Object src.FesChallenge.Body_Scence.StatusPanel
    
    //Gọi Jframe, đưa panel lên hiển thị lên mành hình
    public MainBoard() {
        super("Fes Challenge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        matchPanel = new MatchPanel(8,8, this);
        getContentPane().add(matchPanel);
        statusPanel = new StatusPanel(this, 20*32);
        getContentPane().add(statusPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void notifyScoreUpdate(int score, int addAmount) { 
        statusPanel.updateScore(score, addAmount);//nâng điểm lên ở trong cái status Panel 
    }

    //method để bắt đầu lại trò chơi mới, khởi động lại số điểm bằng 0
    public void restart() {
        statusPanel.updateScore(0,0);
        matchPanel.restart();
    }
}
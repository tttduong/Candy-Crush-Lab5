import javax.swing.JFrame;
import java.awt.*;
import Sound.SoundMusic;

public class Game extends JFrame {
     SoundMusic sound = new SoundMusic("Sound\Candy Crush Saga New Soundtrack - Main menu 1.wav");
    private MatchPanel matchPanel;// Object matchPanel 
    private StatusPanel statusPanel;//Object StatusPanel
    
    //Gọi Jframe, đưa panel lên hiển thị lên mành hình
    public Game() {
        super("Match Game");
        sound.playSound("Sound\Candy Crush Saga New Soundtrack - Main menu 1.wav");
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

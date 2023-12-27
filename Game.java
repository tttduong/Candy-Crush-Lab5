import javax.swing.JFrame;
import java.awt.*;

public class Game extends JFrame {
    private MatchPanel matchPanel;
    private StatusPanel statusPanel;

    public static void main(String[] args) {
        Game game = new Game();
    }

    public Game() {
        super("Match Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        matchPanel = new MatchPanel(15,15, this);
        getContentPane().add(matchPanel);
        statusPanel = new StatusPanel(this, 15*32);
        getContentPane().add(statusPanel);
        pack();
        setVisible(true);
    }


}

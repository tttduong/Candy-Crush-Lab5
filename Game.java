import javax.swing.JFrame;
import java.awt.*;

public class Game extends JFrame {
    private MatchPanel matchPanel;

    public static void main(String[] args) {
        Game game = new Game();
    }

    public Game() {
        super("Match Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        matchPanel = new MatchPanel(15,15, this);
        getContentPane().add(matchPanel);
        pack();
        setVisible(true);
    }


}
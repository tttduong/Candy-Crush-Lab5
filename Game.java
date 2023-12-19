import javax.swing.JFrame;

public class Game extends JFrame {
    private MatchPannel matchPannel;

    public Game() {
        matchPanel = new MatchPanel(15,15, this);
        getContentPane().add(matchPanel);
    }
}
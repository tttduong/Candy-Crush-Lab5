import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Piece {
    int x, y, row, col, kind, match;

    public Piece() {
        match = 0;
    }
}
public class Game extends JPanel implements Runnable, MouseListener {
    final int WIDTH = 740;
    final int HEIGHT = 480;
    boolean isRunning;
    Thread thread;
    Piece[][] grid;
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
    }

    public static void main(String[] args) {
        JFrame w = new JFrame("Bejeweled (Match-3)");
        w.setResizable(false);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.add(new Game());
        w.pack();
        w.setLocationRelativeTo(null);
        w.setVisible(true);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void run() {

    }
}

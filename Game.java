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

    public void swap(Piece p1, Piece p2) {
        int rowAux = p1.row;
        p1.row = p2.row;
        p2.row = rowAux;

        int colAux = p1.col;
        p1.col = p2.col;
        p2.col = colAux;

        grid[p1.row][p1.col] = p1;
        grid[p2.row][p2.col] = p2;
    }

    public void start () {
        try {
            view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            grid = new Piece[10][10];

            background = ImageIO.read(getClass().getResource("/background.png"));
            gems = ImageIO.read(getClass().getResource("/gems.png"));
            cursor = ImageIO.read(getClass().getResource("/cursor.png"));

            for(int i = 0;i < 10;i++) {
                for(int j = 0;j < 10;j++) {
                    grid[i][j] = new Piece();
                }
            }

            for (int i = 1;i <=8;i++) {
                for(int j = 1;j <= 8;j++) {
                    grid[i][j].kind = (new Random().nextInt(7));
                    grid[i][j].row = i;
                    grid[i][j].col = j;
                    grid[i][j].x = j * tileSize;
                    grid[i][j].y = i * tileSize;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

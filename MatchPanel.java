import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MatchPanel extends JPanel{
    private Game game;
    private int width, height;
    private final int CELL_DIM = 32;
    private MatchBoard matchBoard;
    private Color[] colors = new Color[] {Color.BLUE, Color.BLACK, Color.YELLOW, Color.GREEN, Color.RED };
    
    public MatchPanel(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width*CELL_DIM, height*CELL_DIM));
        setBackground(Color.gray);

    }

    private void drawAllCells(Graphics g) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                drawCell(g, x, y);
            }
        }
    }

    private void drawCell(Graphics g, int x, int y) {
        g.setColor(colors[matchBoard.getCellValue(x,y)]);
        g.fillOval(x * CELL_DIM, y * CELL_DIM, CELL_DIM, CELL_DIM);
    }
}

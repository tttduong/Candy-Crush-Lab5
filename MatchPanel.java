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
    private List<Position> recentMatches;
    private List<Position> recentNewCells;
    
    public MatchPanel(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
        matchBoard = new MatchBoard(width,height, colors.length);
        recentMatches = new ArrayList<>();
        recentNewCells = new ArrayList<>();
        setPreferredSize(new Dimension(width*CELL_DIM, height*CELL_DIM));
        setBackground(Color.gray);

    }

    //Hiển thị các thao tác gần đây, điểm số hiện tại và trạng thái đặc biệt khi kích hoạt pos1
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawRecentMatches(g);
        drawAllCells(g);
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

    //Đánh dấu bằng các khung hình chữ nhật để hiển thị vị trí của những ô vừa ghép và những ô vừa được tạo mới ngẫu nhiên
    private void drawRecentMatches(Graphics g) {
        g.setColor(Color.ORANGE);
        for(Position p : recentMatches) {
            g.fillRect(p.x * CELL_DIM, p.y * CELL_DIM, CELL_DIM, CELL_DIM);
        }
        g.setColor(new Color(0,128,0));
        for(Position p : recentNewCells) {
            g.fillRect(p.x * CELL_DIM, p.y * CELL_DIM, CELL_DIM, CELL_DIM);
        }
    }
}

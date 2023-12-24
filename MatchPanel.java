import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MatchPanel extends JPanel implements MouseListener{
    private Game game;
    private int width, height;
    private final int CELL_DIM = 32;
    private MatchBoard matchBoard;
    private Color[] colors = new Color[] {Color.BLUE, Color.BLACK, Color.YELLOW, Color.GREEN, Color.RED };
    private List<Position> recentMatches;
    private List<Position> recentNewCells;
    /**
     * Enum GameState: xác định các trạng thái inputs
     * ChoosePos1: vị trí ầu tiên được chọn
     * ChoosePos2: vị trí thứ hai được chọn
     *
     * PauseForDestroy: Tạm dừng nhập inputs (ChoosePos1, ChoosePos2) khi nhận đc
     * valid position (2 vị trí đc chọn match với nhau) đến khi
     * tất cả các matches được xử lí xong -->  tiếp tục nhận inputs (ChoosePos1)
     *
     * Nếu ChoosePos2 được chọn ko match với ChoosePos1 --> quay về trc khi chọn ChoosePos1
     */

    private enum GameState { ChoosePos1, ChoosePos2, PauseForDestroy }
    private GameState gameState;
    /**
     * position để kiểm tra có match hay ko
     */
    private Position pos1, pos2;
    public MatchPanel(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
        gameState = GameState.ChoosePos1;
        matchBoard = new MatchBoard(width,height, colors.length);
        recentMatches = new ArrayList<>();
        recentNewCells = new ArrayList<>();
        setPreferredSize(new Dimension(width*CELL_DIM, height*CELL_DIM));
        setBackground(Color.gray);
        addMouseListener(this);

    }
    /**
     * Hiển thị các thao tác gần đây, điểm số hiện tại và trạng thái đặc biệt khi kích hoạt pos1
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawRecentMatches(g);
        drawAllCells(g);
        if(gameState == GameState.ChoosePos2)
            drawSelectedPos1(g);
    }
    /**
     * Vẽ các gems
     */
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
    /**
     * Đánh dấu bằng các khung hình chữ nhật để hiển thị vị trí của những ô vừa ghép và những ô vừa được tạo mới ngẫu nhiên
     */
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
    /**
     * Đặt giá trị ChoosePos1, gameState chuyển qua nhập ChoosePos2
     */
    private void setPos1(Position pos) {
        pos1 = pos;
        gameState = GameState.ChoosePos2;
        System.out.println("Set pos1 as: " + pos.x + " " + pos.y);
    }
    /**
    * Đặt giá trị ChoosePos2, gameState chuyển qua PauseForDestroy
    */
    private void setPos2(Position pos) {
        pos2 = pos;
        gameState = GameState.PauseForDestroy;
        System.out.println("Set pos2 as: " + pos.x + " " + pos.y);
    }
    /**
     * Kiểm tra xem 2 vị trí được chọn có liền kề nhau theo chiều dọc/ngang hay không
     */
    private boolean isAdjacentToPos1(int x, int y) {
        int diffx = Math.abs(x-pos1.x);
        int diffy = Math.abs(y-pos1.y);
        return (diffx == 0 || diffy == 0) && (diffx == 1 || diffy == 1);
    }
    /**
     *
     * Vẽ dấu cộng trắng đánh dấu 1st choosed gem
     */
    private void drawSelectedPos1(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(pos1.x*CELL_DIM,pos1.y*CELL_DIM+CELL_DIM/2,pos1.x*CELL_DIM+CELL_DIM, pos1.y*CELL_DIM+CELL_DIM/2);
        g.drawLine(pos1.x*CELL_DIM+CELL_DIM/2,pos1.y*CELL_DIM,pos1.x*CELL_DIM+CELL_DIM/2, pos1.y*CELL_DIM+CELL_DIM);
    }

    /**
     *  Vẽ grid theo số cells
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        // Draw vertical lines
        int y2 = 0;
        int y1 = height * CELL_DIM;
        for(int x = 0; x < width; x++)
            g.drawLine(x * CELL_DIM, y1, x * CELL_DIM, y2);

        // Draw horizontal lines
        int x2 = 0;
        int x1 = width * CELL_DIM;
        for(int y = 0; y < height; y++)
            g.drawLine(x1, y * CELL_DIM, x2, y * CELL_DIM);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!(gameState == GameState.ChoosePos1 || gameState == GameState.ChoosePos2)) return;

        int x = e.getX() / CELL_DIM;
        int y = e.getY() / CELL_DIM;
        if(gameState == GameState.ChoosePos2 && !isAdjacentToPos1(x,y)) {
            System.out.println("Too far apart! Force swapping back to choosing pos1.");
            gameState = GameState.ChoosePos1;
        }

        if(gameState == GameState.ChoosePos1) {
            setPos1(new Position(x,y));
        } else {
            setPos2(new Position(x,y));
        }
        repaint();
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
}

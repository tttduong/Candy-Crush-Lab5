package src.FesChallenge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

//ở lớp matchpanel dùng để hiển thị bảng panel
//và các thông tin sẽ được cập nhật trong matchpanel mỗi khi click chuột 
public class MatchPanel extends JPanel implements MouseListener {
    
     //cập nhận điểm số thông qua cái object game
    private Game game;
    
    private enum GameState { ChoosePos1, ChoosePos2, PauseForDestroy }
    //lưu trữ game state
    private GameState gameState;
    //vị trí 1,2 
    private Position pos1, pos2;
    //số ô trống của chiều rộng và chiều cao 
    private int width, height;
    //số điểm ảnh (pixel) khi mỗi ô biểu thị 
    private final int CELL_DIM = 32;
    //màu hiển thị khác nhau đối với mỗi ô
    private Color[] colors = new Color[] { Color.BLUE, Color.BLACK, Color.YELLOW, Color.GREEN, Color.RED };
    
    private MatchBoard matchBoard;
    //ghi lại các match đã thành công hay chưa 
    private List<Position> recentMatches;
    //ghi lại các ô đã swap
    private List<Position> recentNewCells;
    //bộ hẹn giờ 
    private Timer stateTimer;
    //ghi điểm hiện tại 
    private int score;

    private Image cellImage1, cellImage2,cellImage3, cellImage4,cellImage5, cellImage6,cellImage7;
    private Image backgroundImage;
    private Image resizedBackgroundImage;

    private Image cursorImage;

    //bắt đầu khởi tạo các thuộc tính trong method matchpanel 
    public MatchPanel(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
        matchBoard = new MatchBoard(width,height, colors.length);
        gameState = GameState.ChoosePos1;
        recentMatches = new ArrayList<>();
        recentNewCells = new ArrayList<>();
        setPreferredSize(new Dimension(width*CELL_DIM, height*CELL_DIM));
        setBackground(Color.gray);
        addMouseListener(this);
        createStableBoardState();
        configureTimer();
        score = 0;
        cellImage1 = new ImageIcon(getClass().getResource("/res/gem1.png")).getImage();
        cellImage2 = new ImageIcon(getClass().getResource("/res/gem2.png")).getImage();
        cellImage3 = new ImageIcon(getClass().getResource("/res/gem3.png")).getImage();
        cellImage4 = new ImageIcon(getClass().getResource("/res/gem4.png")).getImage();
        cellImage5 = new ImageIcon(getClass().getResource("/res/gem5.png")).getImage();
        cellImage6 = new ImageIcon(getClass().getResource("/res/gem6.png")).getImage();
        cellImage7 = new ImageIcon(getClass().getResource("/res/gem7.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("/res/background.png")).getImage();
        resizedBackgroundImage = backgroundImage.getScaledInstance(width * CELL_DIM, height * CELL_DIM, Image.SCALE_SMOOTH);
        cursorImage = new ImageIcon(getClass().getResource("/res/cursor.png")).getImage();
    }

    
     // vẽ các ô trong game và điền element vô 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //drawGrid(g);
        drawRecentMatches(g);
        drawAllCells(g);
        if(gameState == GameState.ChoosePos2)
            drawSelectedPos1(g);
    }

     // method này dùng để xử lí sau khi người chơi swap pos 1 và pos2 
     //sau khi swap thì hệ thống sẽ cập nhật là ăn điểm hay là không
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!(gameState == GameState.ChoosePos1 || gameState == GameState.ChoosePos2)) return;

        int x = e.getX() / CELL_DIM;
        int y = e.getY() / CELL_DIM;
        if(!isValidPosition(x,y)) return;

        if(gameState == GameState.ChoosePos2 && !isAdjacentToPos1(x,y)) {
            System.out.println("Too far apart! Force swapping back to choosing pos1.");
            gameState = GameState.ChoosePos1;
        }

        if(gameState == GameState.ChoosePos1) {
            setPos1(new Position(x,y));
        } else {
            setPos2(new Position(x,y));
            attemptCellSwap();
        }

        repaint();
    }

    
     //method để bắt đầu lại game nếu restart 
    public void restart() {
        score = 0;
        matchBoard.fillBoard();
        createStableBoardState();
        repaint();
    }

    
     //chọn ô thứ nhất và gamestate sẽ ghi lại, cập nhật dưới bảng output
    private void setPos1(Position pos) {
        pos1 = pos;
        gameState = GameState.ChoosePos2;
        System.out.println("Set pos1 as: " + pos.x + " " + pos.y);
    }

    
     //chọn ô thứ hai và gamestate sẽ ghi lại, cập nhật dưới bảng output  
    private void setPos2(Position pos) {
        pos2 = pos;
        gameState = GameState.PauseForDestroy;
        System.out.println("Set pos2 as: " + pos.x + " " + pos.y);
    }

    
     // kiểm tra xem nếu hai ô chọn có gần nhau vertically hay horizontally hay không
    private boolean isAdjacentToPos1(int x, int y) {
        int diffx = Math.abs(x-pos1.x); //vị trí x
        int diffy = Math.abs(y-pos1.y); //vị trí y
        return (diffx == 0 || diffy == 0) && (diffx == 1 || diffy == 1); //true nếu hai ô gần nhau 
    }

    
     // check position mỗi ô để swap position    
    private void drawAllCells(Graphics g) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                drawCell(g, x, y);
            }
        }
    }


    //thực hiện swap giữa hai position, nếu không khớp thì sẽ không swap và sẽ giữ nguyên position 
    //nếu khớp thì sẽ swap và các ô sẽ rơi xuống để khớp với ô trống cho đến khi không còn match nữa 
    private void attemptCellSwap() {
        List<Position> matches = matchBoard.getMatchesFromSwap(pos1, pos2);
        updateScore(matches.size());
        if(!matches.isEmpty()) {
            matchBoard.swapCells(pos1, pos2);
            List<Position> cellsToReplace = matchBoard.shuffledDownToFill(matches);
            matchBoard.fillPositions(cellsToReplace);
            recentMatches = matches;
            recentNewCells = cellsToReplace;
            stateTimer.start();
        } else {
            System.out.println("Nothing to match here. :(");
            gameState = GameState.ChoosePos1;
        }
    }

    //method này dùng để cập nhật điểm 
    private void updateScore(int addAmount) {
        if(addAmount > 0) {
            score += addAmount;
            game.notifyScoreUpdate(score, addAmount);
            //System.out.println("Score: " + score);
        }
    }

    /**
     * vẽ hình oval cho mỗi cell, và hiển thị màu cho mỗi cell 
     * @param g graphics object
     */
    private void drawCell(Graphics g, int x, int y) {
        int cellType = matchBoard.getCellValue(x, y);
        if (cellType >= 0 && cellType < colors.length) { // imageArray chứa hình ảnh tương ứng với giá trị của ô cell
            Image[] imageArray = new Image[7];
            imageArray[0] = cellImage1;
            imageArray[1] = cellImage2;
            imageArray[2] = cellImage3;
            imageArray[3] = cellImage4;
            imageArray[4] = cellImage5;
            imageArray[5] = cellImage6;
            imageArray[6] = cellImage7;
            Image image = imageArray[cellType]; // Lấy hình ảnh từ mảng imageArray dựa trên giá trị ô cell

            // Vẽ hình ảnh vào ô cell tại vị trí (x * CELL_DIM, y * CELL_DIM) với kích thước CELL_DIM x CELL_DIM
            g.drawImage(image, x * CELL_DIM, y * CELL_DIM, CELL_DIM, CELL_DIM, null);
        }
    }

    /**
     * 
     * highlight cái match và highlight cái cell gần nhất đã fill vào ô trống
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

    
     
     // đánh dấu cross ở postition 1 sau khi position 1 được chọn 
    private void drawSelectedPos1(Graphics g) {
        g.drawImage(cursorImage, pos1.x * CELL_DIM, pos1.y * CELL_DIM, CELL_DIM, CELL_DIM, null);
    }

    
     
     //vẽ grid dựa trên số ô được chọn 
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        // vẽ một line dọc
        int y2 = 0;
        int y1 = height * CELL_DIM;
        for(int x = 0; x < width; x++)
            g.drawLine(x * CELL_DIM, y1, x * CELL_DIM, y2);

        // vẽ một line ngang 
        int x2 = 0;
        int x1 = width * CELL_DIM;
        for(int y = 0; y < height; y++)
            g.drawLine(x1, y * CELL_DIM, x2, y * CELL_DIM);
    }

    /**
     * kiểm tra xem position có valid trong ô được chọn hay không 
     * @return true nếu valid position 
     */
    private boolean isValidPosition(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return false;
        return true;
    }

    //xóa match và cộng điểm, cell mới fill vô ô trống 
    private void createStableBoardState() {
        List<Position> matches = matchBoard.findAllMatches();
        while(!matches.isEmpty()) {
            System.out.println("Fixing board state!");
            matchBoard.fillPositions(matches);
            matches = matchBoard.findAllMatches();
        }
    }

    //Bộ đếm thời gian kích hoạt, nó sẽ tìm thấy tất cả các kết quả phù hợp trên bảng.
    //Sau đó cập nhật và bắt đầu lại bộ đếm thời gian.
    //Một khi không còn trận đấu nào nữa, trạng thái trò chơi được hoán đổi trở lại việc chọn pos1.
    private void configureTimer() {
        stateTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Position> matches = matchBoard.findAllMatches();
                updateScore(matches.size());
                if(!matches.isEmpty()) {
                    List<Position> cellsToReplace = matchBoard.shuffledDownToFill(matches);
                    matchBoard.fillPositions(cellsToReplace);
                    recentMatches = matches;
                    recentNewCells = cellsToReplace;
                    repaint();
                    stateTimer.start();
                    System.out.println("Made changes, waiting again!");
                } else {
                    System.out.println("All done :)");
                    gameState = GameState.ChoosePos1;
                }
            }
        });
        stateTimer.setRepeats(false);
    }

    
     // buộc phải có method mousePressed
    @Override
    public void mousePressed(MouseEvent e) {}
    
     // Buộc phải có mouseReleased 
    @Override
    public void mouseReleased(MouseEvent e) {}
    
     // buộc phải có method mouseEntered
    @Override
    public void mouseEntered(MouseEvent e) {}
    
     //buộc phải có mouseExited 
    @Override
    public void mouseExited(MouseEvent e) {}
    
}

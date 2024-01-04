package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * src.MatchBoard class:
 * Biểu thị một lưới 2D gồm các số ngẫu nhiên để có thể tìm các hình giống nhau; từ 3 hình trở lên cả ngang lẫn dọc
 */
public class MatchBoard {
  //2D grid 
  private int[][] cellValues;
  private int width, height;
  private Random rand;  // để tạo ra các tổ hợp khối hình random cho game
  private int maxNums; /*Range( 0 to -1 for random numbers)*/
  private boolean enforceAdjacent; // Used to SWAP from an adjacent match to one that allows swaps between any two points.
    /**
     * hoán đổi giữa hai điểm bất kỳ.
     * @param maxNums Số lượng tối đa các loại khác nhau.
     */
  public MatchBoard(int width, int height, int maxNums) {
        this.width = width;
        this.height = height;
        cellValues = new int[width][height];
        rand = new Random();
        this.maxNums = maxNums;
        fillBoard();
        enforceAdjacent = true;
    }
    /**
     * Hiện tại, lớp chấp nhận bất kỳ hai ô nào trên bảng để hoán đổi thay vì chỉ các ô liền kề.
     * @param enforceAdjacent . Trạng thái được đặt thực thi Liền kề.
     */
    public void setEnforceAdjacent(boolean enforceAdjacent) {
        this.enforceAdjacent = enforceAdjacent;
    }
    /**
     * Nhận giá trị tại vị trí đã chỉ định x, y.
     */
    public int getCellValue(int x, int y) {
        return cellValues[x][y];
    }
    /**
     * điền vào bảng với một bộ số hoàn toàn ngẫu nhiên trong phạm vi từ 0 đến maxNums-1
     */
    public void fillBoard() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                cellValues[x][y] = rand.nextInt(maxNums);
            }
        }
    }
    /**
     * Điền tất cả các vị trí đã chỉ định với các số ngẫu nhiên từ 0 đến maxNums-1
     */
    public void fillPositions(List<Position> positions) {
        for(Position p : positions) {
            cellValues[p.x][p.y] = rand.nextInt(maxNums);
        }
    }
    public List<Position> findAllMatches() {
        List<Position> matched = new ArrayList<>();
        // Kiểm tra hàng
        for(int y = 0; y < height; y++) {
            getMatchesOnRow(y, matched);
        }

        // Kiểm tra cột
        for(int x = 0; x < width; x++) {
            getMatchesOnColumn(x, matched);
        }

        return matched;
    }
    // Swap những cái gem lại với nhau
    public void swapCells(Position pos1, Position pos2) {                   
        int temp = cellValues[pos1.x][pos1.y];
        cellValues[pos1.x][pos1.y] = cellValues[pos2.x][pos2.y];
        cellValues[pos2.x][pos2.y] = temp;
    }

    public List<Position> getMatchesFromSwap(Position pos1, Position pos2) {
        List<Position> matched = new ArrayList<>();
        int diffx = Math.abs(pos1.x - pos2.x);
        int diffy = Math.abs(pos1.y - pos2.y);
        // Kiểm tra xem nếu hợp lệ thì hoán đổi
        if(enforceAdjacent && !((diffx == 0 || diffy == 0) && (diffx == 1 || diffy == 1))) return matched;
        // Thay đổi gems
        swapCells(pos1, pos2);

        // 
        getMatchesOnRow(pos1.y, matched);
        if(!enforceAdjacent || diffy > 0)
            getMatchesOnRow(pos2.y, matched);
        getMatchesOnColumn(pos1.x, matched);
        if(!enforceAdjacent || diffx > 0)
            getMatchesOnColumn(pos2.x, matched);

        // nếu ko giống sẽ swap lại
        swapCells(pos1, pos2);

        return matched;
    }
    public List<Position> shuffledDownToFill(List<Position> positions) {
        int[] affectedColumns = new int[width];
        for(int x = 0; x < width; x++) {
            affectedColumns[x] = 0;
        }
        for(Position pos : positions) {
            affectedColumns[pos.x]++;
            shuffleDownToFill(pos);
        }
        return getAffectedPositionsFromAffectedColumns(affectedColumns);
    }
  
    private void getMatchesOnRow(int y, List<Position> matchedRef) {
        for(int x = 0; x < width - 2; x++) {
            // check xem 3 gems cùng hàng có giống nhau không?  
            if(cellValues[x][y] == cellValues[x+1][y] && cellValues[x][y] == cellValues[x+2][y]) {
                int matchValue = cellValues[x][y];
                Position p = new Position(x,y);
                // Thêm vị trí vào danh sách các vị trí đã khớp nếu nó chưa được thêm trước đó
                if(!matchedRef.contains(p))
                    matchedRef.add(p);
                // Step forward to the second cell. Second and third will be matches at least.
                x++;
                // Tiếp tục kiểm tra các ô tiếp theo trong cùng một dòng có giá trị giống nhau không
                while(x < width && cellValues[x][y] == matchValue) {
                    p = new Position(x,y);
                    if(!matchedRef.contains(p))
                        matchedRef.add(p);
                    x++;
                }
                // quay trở lại nếu gems không match
                x--;
            }
        }
    }

    private void getMatchesOnColumn(int x, List<Position> matchedRef) {
        for(int y = 0; y < height - 2; y++) {
            // check xem 3 gems cùng hàng có giống nhau không? 
            if(cellValues[x][y] == cellValues[x][y+1] && cellValues[x][y] == cellValues[x][y+2]) {
                int matchValue = cellValues[x][y];
                Position p = new Position(x,y);
                if(!matchedRef.contains(p))
                    matchedRef.add(p);
                y++;
                while(y < height && cellValues[x][y] == matchValue) {
                    p = new Position(x,y);
                    if(!matchedRef.contains(p))
                        matchedRef.add(p);
                    y++;
                }
                y--;
            }
        }
    }
    private void shuffleDownToFill(Position pos) {
        for(int y = pos.y; y >= 1; y--) {
            cellValues[pos.x][y] = cellValues[pos.x][y-1];
        }
    }

     private List<Position> getAffectedPositionsFromAffectedColumns(int[] affectedColumns) {
        List<Position> affectedPositions = new ArrayList<>();
        for(int x = 0; x < affectedColumns.length; x++) {
            if(affectedColumns[x] > 0) {
                for(int y = 0; y < affectedColumns[x]; y++) {
                    affectedPositions.add(new Position(x,y));
                }
            }
        }
        return affectedPositions;
    }

}

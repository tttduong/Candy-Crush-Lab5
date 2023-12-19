import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//  * MatchBoard class: 
//  * Biểu thị một lưới 2D gồm các số ngẫu nhiên để có thể tìm các hình giống nhau; từ 3 hình trở lên cả ngang lẫn dọc
//  */


public class MatchBoard {
  //2D grid 
  private int[][] cellValues;
  private int width, height;
  private Random rand;  // để tạo ra các tổ hợp khối hình random cho game
  private int maxNums; /*Range( 0 to -1 for random numbers)*/
  private boolean enforceAdjacent; // Used to SWAP from an adjacent match to one that allows swaps between any two points.
  
//   // hoán đổi giữa hai điểm bất kỳ.
//   * @param maxNums Số lượng tối đa các loại khác nhau.


  public MatchBoard(int width, int height, int maxNums) {
        this.width = width;
        this.height = height;
        cellValues = new int[width][height];
        rand = new Random();
        this.maxNums = maxNums;
        fillBoard();
        enforceAdjacent = true;
    }
  
    //Hiện tại, lớp chấp nhận bất kỳ hai ô nào trên bảng để hoán đổi thay vì chỉ các ô liền kề.
    // @param enforceAdjacent . Trạng thái được đặt thực thi Liền kề.
    public void setEnforceAdjacent(boolean enforceAdjacent) {
        this.enforceAdjacent = enforceAdjacent;
    }
    
     // Nhận giá trị tại vị trí đã chỉ định x, y.
    public int getCellValue(int x, int y) {
        return cellValues[x][y];
    } 

    //điền vào bảng với một bộ số hoàn toàn ngẫu nhiên trong phạm vi từ 0 đến maxNums-1
    public void fillBoard() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                cellValues[x][y] = rand.nextInt(maxNums);
            }
        }
    }

    //Điền tất cả các vị trí đã chỉ định với các số ngẫu nhiên từ 0 đến maxNums-1
    public void fillPositions(List<Position> positions) {
        for(Position p : positions) {
            cellValues[p.x][p.y] = rand.nextInt(maxNums);
        }
    }
}

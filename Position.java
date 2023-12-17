import java.util.Objects;

/**
 * Position: vị trí điểm theo tọa độ xy
 */
public class Position {
    /**
     * XY coordinate (tọa độ)
     */
    public int x;

    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *Compare the position of 2 objects.
     * non-Object --> false
     * khác x hoặc y, x và y --> false
     * x=x && y=y  --> true
     * Compares the Position object against another object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    /**
     * Function creates an object's hash representation
     *Trả về biểu diễn hàm băm của object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
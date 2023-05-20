import java.util.Objects;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean canMoveThere() {  /////// только координаты, без учета расположения объектов
        return (x >= 0 && x <= Map.x) && (y >= 0 && y <= Map.y);
    }

    public Coordinates shift(CoordinateShift coordinateShift) {
        return new Coordinates(this.x + coordinateShift.shiftX, this.y + coordinateShift.shiftY);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

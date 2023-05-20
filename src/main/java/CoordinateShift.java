public class CoordinateShift {
    public int shiftX;
    public int shiftY;

    public CoordinateShift(int shiftX, int shiftY) {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    @Override
    public String toString() {
        return "CoordinateShift{" +
                "shiftX=" + shiftX +
                ", shiftY=" + shiftY +
                '}';
    }
}

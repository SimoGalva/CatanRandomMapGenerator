package hexagon;

public class HexagonPoint {
    //classe POJO per contenere le coordinate esagonali si un esagono qualsiasi; (per coordinate si intende quellle a vista lancaindo il main)
    private final int rowHexCoord;
    private final int diagHexCoord;

    public HexagonPoint(int rowHexCoord, int diagHexCoord) {
        this.rowHexCoord = rowHexCoord;
        this.diagHexCoord = diagHexCoord;
    }

    public int getRowHexCoord() {
        return rowHexCoord;
    }

    public int getDiagHexCoord() {
        return diagHexCoord;
    }

    public String toString() {
        return this.rowHexCoord + ":" + this.diagHexCoord;
    }

}

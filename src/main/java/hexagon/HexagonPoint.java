package hexagon;

import static java.lang.Integer.parseInt;

public class HexagonPoint {
    //classe POJO per contenere le coordinate esagonali si un esagono qualsiasi; (per coordinate si intende quellle a vista lancaindo il main)
    private final int rowHexCoord;
    private final int diagHexCoord;

    public HexagonPoint(int diagHexCoord, int rowHexCoord ) {
        this.diagHexCoord = diagHexCoord;
        this.rowHexCoord = rowHexCoord;
    }

    public HexagonPoint(String coordinateStr) {
        this(parseInt(coordinateStr.split(":")[0]), parseInt(coordinateStr.split(":")[1]));
    }

    public int getRowHexCoord() {
        return rowHexCoord;
    }

    public int getDiagHexCoord() {
        return diagHexCoord;
    }

    public String toString() {
        return this.diagHexCoord + ":" + this.rowHexCoord;
    }

    public HexagonPoint clone() {
        return new HexagonPoint(this.diagHexCoord, this.diagHexCoord);
    }
}

package hexagon;


import hexagon.material.Materials;
import hexagon.number.Numbers;

public class BorderHexagon extends HexagonalBase {
    //esagono sui bordi: dimensione pointer 4.

    public BorderHexagon(Materials material, Numbers number, int pointerDimension, HexagonPoint point) {
        super(material, number, pointerDimension, point);
    }
}

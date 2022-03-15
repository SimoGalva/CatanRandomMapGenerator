package hexagon;

import hexagon.material.Materials;
import hexagon.number.Numbers;

public class CentralHexagon extends HexagonalBase {
    //esagono centrale: dimensione pointer 6.

    public CentralHexagon(Materials material, Numbers number, int pointerDimension, HexagonPoint point) {
        super(material, number, pointerDimension, point);
    }
}

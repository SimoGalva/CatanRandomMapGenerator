package hexagon;

import hexagon.material.Materials;
import hexagon.number.Numbers;

public class VertexHexagon extends HexagonalBase {
//esagono sui vertici: dimensione pointer 3.

    public VertexHexagon(Materials material, Numbers number, int pointerDimension, HexagonPoint point) {
        super(material, number, pointerDimension, point);
    }
}

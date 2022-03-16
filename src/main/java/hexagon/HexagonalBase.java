package hexagon;

import hexagon.material.MaterialCounter;
import hexagon.material.Materials;
import hexagon.number.Numbers;

public abstract class HexagonalBase {
    private final MaterialCounter materialCounter;
    private HexagonFE hexagonFEToken;
    private final Materials material;
    private HexagonPoint[] pointer; //TODO: il pointer bisogna capire come costruirlo, serviraà un pointerBuilder a livello di engine;
    private final Numbers number;
    private HexagonPoint hexAsPoint;

    public HexagonPoint getHexAsPoint() {
        return hexAsPoint;
    }

    public HexagonFE getHexagonFEToken() {
        return hexagonFEToken;
    }
    public void setHexagonFEToken(HexagonFE hexagonFEToken) {
        this.hexagonFEToken = hexagonFEToken;
    }

    public MaterialCounter getMaterialCounter() {
        return materialCounter;
    }

    public Materials getMaterial() {
        return material;
    }

    public Numbers getNumber() {
        return number;
    }

    public HexagonalBase(Materials material, Numbers number, int pointerDimension, HexagonPoint point) {
        this.materialCounter = MaterialCounter.getInstance();
        this.material = material;
        this.number = number;
        this.pointer = new HexagonPoint[pointerDimension];
        this.hexAsPoint = point;
        //lascialo sempre come ultima istruzione: servono gli altri parametri settati.
        buildPointer();
    }

    private void buildPointer() {
        String[] currentHexCoordinate = hexAsPoint.toString().split(":");
        int diagCoord = Integer.parseInt(currentHexCoordinate[0]);
        int rowCoord = Integer.parseInt(currentHexCoordinate[1]);


    }
}

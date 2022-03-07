package hexagon;

import hexagon.material.MaterialCounter;
import hexagon.material.Materials;

public abstract class HexagonalBase {
    private final MaterialCounter materialCounter;
    private HexagonFE hexagonFEToken;
    private final Materials material;
    private HexagonalBase[] pointer; //TODO: il pointer bisogna capire come costruirlo, serviraà un pointerBuilder a livello di engine;
    private final int number;
    private int[] coordMap = new int[2];

    public HexagonFE getHexagonFEToken() {
        return hexagonFEToken;
    }
    public void setHexagonFEToken(HexagonFE hexagonFEToken) {
        this.hexagonFEToken = hexagonFEToken;
    }

    public HexagonalBase[] getPointer() {
        return pointer;
    }
    public void setPointer(HexagonalBase[] pointer) {
        this.pointer = pointer;
    }

    public MaterialCounter getMaterialCounter() {
        return materialCounter;
    }

    public Materials getMaterial() {
        return material;
    }

    public int getNumber() {
        return number;
    }

    public int[] getCoordMap() {
        return coordMap;
    }

    public HexagonalBase(Materials material, int number, int pointerDimension) {
        this.materialCounter = MaterialCounter.getInstance();
        this.material = material;
        this.number = number;
        this.pointer = new HexagonalBase[pointerDimension];
    }
}

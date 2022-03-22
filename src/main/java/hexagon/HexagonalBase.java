package hexagon;

import coordinate.HexagonalCoordinate4PHandler;
import hexagon.material.MaterialCounter;
import hexagon.material.Materials;
import hexagon.number.Numbers;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class HexagonalBase {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final MaterialCounter materialCounter;
    private final HexagonalCoordinate4PHandler coordinate4PHandler = new HexagonalCoordinate4PHandler();


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

    public HexagonPoint[] getPointer() {
        return pointer;
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
        logger.info("buildPoint: starting to build pointer for ["+hexAsPoint.toString()+"].");
        String[] currentHexCoordinate = hexAsPoint.toString().split(":");
        int diagCoord = Integer.parseInt(currentHexCoordinate[0]);
        int rowCoord = Integer.parseInt(currentHexCoordinate[1]);
        ArrayList<HexagonPoint> pointerBuilder = new ArrayList<>();

        //costruisco un array temporaneo semper da 6, che sottopongo al controllo di esistenza delle coordinate.
        //Nella lista finiranno tutte e sole quelle che esistono effettivamente e saranno tante quante la dimensione del pointer.
        //Me ne frego dell'ordine degli elementi del pointer. Eventualmente si può implementare un metodo di sorting.
        HexagonPoint[] pointerBuilderTemp = new HexagonPoint[6];
        pointerBuilderTemp[0] = new HexagonPoint(diagCoord +1, rowCoord);
        pointerBuilderTemp[1] = new HexagonPoint(diagCoord, rowCoord + 1);
        pointerBuilderTemp[2] = new HexagonPoint(diagCoord - 1, rowCoord + 1);
        pointerBuilderTemp[3] = new HexagonPoint(diagCoord - 1, rowCoord);
        pointerBuilderTemp[4] = new HexagonPoint(diagCoord, rowCoord -1);
        pointerBuilderTemp[5] = new HexagonPoint(diagCoord + 1, rowCoord - 1);

        for (HexagonPoint pointToTest : pointerBuilderTemp) {
            if (coordinate4PHandler.existsCoordinate(pointToTest)) {
                pointerBuilder.add(pointToTest);
            }
        }
        pointerBuilder.trimToSize();
        if (pointerBuilder.size() == this.pointer.length) {
            int i = 0;
            for (HexagonPoint pointToSet : pointerBuilder) {
                this.pointer[i] = pointToSet;
                i = i++;
            }
        } else {
            logger.severe("buildPointer: something went wrong in handling coordinates for pointer. Number of points do not equal pointer dimension.");
        }
    }
}

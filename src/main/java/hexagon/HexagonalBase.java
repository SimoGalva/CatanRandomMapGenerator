package hexagon;

import coordinate.AbstractCoordinateHandler;
import hexagon.material.MaterialCounter;
import hexagon.material.Materials;
import hexagon.number.Numbers;

import java.util.ArrayList;
import java.util.logging.Logger;

public class HexagonalBase {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final MaterialCounter materialCounter;
    private final AbstractCoordinateHandler coordinateHandler;


    private HexagonFE hexagonFEToken;
    private final Materials material;
    private HexagonPoint[] pointer;
    private Numbers number;
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
    public void setNumber(Numbers number) {
        this.number = number;
    }

    public HexagonPoint[] getPointer() {
        return pointer;
    }

    public void setHexAsPoint(String coordinateStr) {
        this.hexAsPoint = new HexagonPoint(coordinateStr);
    }

    public HexagonalBase(Materials material, Numbers number, int pointerDimension, HexagonPoint point) {
        MaterialCounter tempMaterialCounter;
        this.coordinateHandler = AbstractCoordinateHandler.getInstance();
        try {
            tempMaterialCounter = MaterialCounter.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            tempMaterialCounter = null;
        }
        this.materialCounter = tempMaterialCounter;
        this.material = material;
        this.number = number;
        this.pointer = new HexagonPoint[pointerDimension];
        this.hexAsPoint = point;
        //lascialo sempre come ultima istruzione: servono gli altri parametri settati.
        if (pointerDimension != 0) {
            this.buildPointer();
        }
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
            if (coordinateHandler.existsCoordinate(pointToTest)) {
                pointerBuilder.add(pointToTest);
            }
        }
        pointerBuilder.trimToSize();
        if (pointerBuilder.size() == this.pointer.length) {
            int i = 0;
            for (HexagonPoint pointToSet : pointerBuilder) {
                this.pointer[i] = pointToSet;
                i ++;
            }
        } else {
            logger.severe("buildPointer: something went wrong in handling coordinates for pointer. Number of points do not equal pointer dimension.");
        }
    }

    public static HexagonalBase createInstance(Materials material, Numbers number, HexagonPoint hexPoint) {
        return createInstance(material, number, 0,hexPoint);
    }

    public static HexagonalBase createInstance(Materials material, Numbers number, int pointerDim, HexagonPoint hexPoint) {
        return new HexagonalBase(material, number, pointerDim, hexPoint);
    }

    public HexagonFE defineHexagonFE(int x, int y, int r) {
        return new HexagonFE(x, y, r, material.getColorValue());
    }
}

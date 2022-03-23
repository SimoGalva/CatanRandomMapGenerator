package engine;

import coordinate.HexagonalCoordinate4PHandler;
import hexagon.*;
import hexagon.material.MaterialCounter;
import hexagon.material.MaterialHandler;
import hexagon.material.Materials;
import hexagon.number.NumberCounter;
import hexagon.number.NumberHandler;
import hexagon.number.Numbers;

public class GenerationHelper {
    private boolean isMainIsland;
    private boolean seaAllowed;
    private boolean desertAllowed = true;

    protected void generetionThroughPointers(int numberOfHexagonsLeft, HexagonalBase hexagonStarter, boolean isMainIsland) {
        this.isMainIsland = isMainIsland;
        int level = 0;
        generetionThroughPointers(numberOfHexagonsLeft, hexagonStarter, level);
    }

    private void generetionThroughPointers(int numberOfHexagonsLeft, HexagonalBase hexagonStarter, int level) {
        if (isMainIsland && level < 2) { //il limite due messo indicativo a caso bisogna ragionarci in base a come escono i risultati
            this.seaAllowed = false;
        } else {
            this.seaAllowed = true;
        }
        level = level + 1;
        HexagonPoint[] currentPointer = hexagonStarter.getPointer().clone();
        //TODO: implementare in modo che si chiami ricorsivamente e a ogni giro riempia il pointer del esagono passato.
        //duque: popolo il pointer intero del punto base, dopodichè sceglie un punto random del pointer e manda il corrispondente esagono nel livello successivo
        // aggiornando il numero di esagoni rimasti. Ciò finchè il numero di esagoni rimasti è 0. a quel punto esce. (va previsto che in generale il pointer non si riempie tutto).
        //i livelli si possono utilizzare in coppia con un parametro che dice da che livello è concesso che si metta mare o deserto. L'idea è quella implementata che non va ovviamente.
        // devo far si che non sia statica, ma non serve la singleton instance.

        generetionThroughPointers(numberOfHexagonsLeft, hexagonStarter, level);
    }

    public static HexagonalBase generateHexagon(HexagonPoint pointInGeneration) {
        HexagonalCoordinate4PHandler coordinateHandler = HexagonalCoordinate4PHandler.getInstance();
        MaterialCounter materialCounter = MaterialCounter.getInstance();
        NumberCounter numberCounter = NumberCounter.getInstance();
        MaterialHandler materialHandler = new MaterialHandler();
        NumberHandler numberHandler = new NumberHandler();

        boolean isNumberValid = false;
        boolean isMaterialValid = false;
        Materials materialCntr = null;
        Numbers numberCntr = null;
        HexagonalBase ret;

        int pointerCntrDim = coordinateHandler.calculatePointerDimesnsion(pointInGeneration);
        do {
            if (!isMaterialValid) {
                materialCntr = materialHandler.pickRandomMaterial(MaterialHandler.LAND);
                isMaterialValid = materialCounter.consumeMaterial(materialCntr);
            }
            if (!isNumberValid) {
                numberCntr = numberHandler.pickRandomNumber(materialCntr);
                isNumberValid = numberCounter.consumeNumber(numberCntr);
            }
        } while (!isNumberValid && !isMaterialValid);
        if (pointerCntrDim == 6) {
            ret = new CentralHexagon(materialCntr, numberCntr, pointerCntrDim, pointInGeneration);
        } else if (pointerCntrDim == 4 || pointerCntrDim == 5) {
            ret = new BorderHexagon(materialCntr, numberCntr, pointerCntrDim, pointInGeneration);
        } else {
            ret = new VertexHexagon(materialCntr, numberCntr, pointerCntrDim, pointInGeneration);
        }
        return ret;
    }
}

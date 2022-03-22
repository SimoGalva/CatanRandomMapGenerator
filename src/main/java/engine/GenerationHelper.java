package engine;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;

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

}

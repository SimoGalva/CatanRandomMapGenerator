package engine;

import hexagon.HexagonalBase;

import java.util.HashMap;
import java.util.logging.Logger;

public class PostGenerationHelper {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private String[] tempCoupleOfNearHexagons = new String[2];

    public boolean AreNearThoseOnList(HashMap<String, HexagonalBase> listToCheck) {
        //todo: implementare questo, deve ritornare un booleano e salvare nell'array la matrice di due colonne che su ogni riga ha una coppia di numeri vicini. in modo da poterla recuperare a posteriori.
        //oppure salva un array con i primi due vicini, tanto ne switcherei uno alla volta.
        return true;
    }

    public HashMap<String,HexagonalBase> switchNearHexagon() {
        //todo: farà uno swicth e rilancerà l'areNear finchè non becca una soluzione.
        return null;
    }
}

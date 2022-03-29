import frontEnd.FErunner;
import globalMap.CatMap;
import globalMap.GlobalMapHandler;

public class MainTest {
    public static void main(String[] args){
        //TODO: non riesco a far rispettare i parametri esattamente diciamo che sono dei max corrisponedenti acconsentiti?
        // tienine conto quanto implementi l'input utente.
        CatMap map = new CatMap(4,1,2);
        map.generateIslands();
        map.fillOcean();
        GlobalMapHandler.printMap();
        map.postGeneratingFixing();
        GlobalMapHandler.printMap();

        FErunner runner = new FErunner();
        runner.run();
    }

}

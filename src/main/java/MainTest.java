import frontEnd.FErunner;
import globalMap.CatMap;
import globalMap.GlobalMapHandler;

public class MainTest {
    public static void main(String[] args){
        CatMap map = new CatMap(4,1,2);
        map.generateIslands();
        map.fillOcean();
        map.postGeneratingFixing();
        GlobalMapHandler.printMap();

        FErunner runner = new FErunner();
        runner.run();
    }

}

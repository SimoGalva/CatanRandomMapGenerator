import globalMap.CatMap;

public class MainTest {
    public static void main(String[] args){
        CatMap map = new CatMap(3,1,5);
        map.generateMapIslands();
    }
}

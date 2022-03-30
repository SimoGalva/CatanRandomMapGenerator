package engine;

import engine.pojo.Params;
import globalMap.CatMap;

public class RefreshEngine implements Runnable {
    private Params params;
    private CatMap newMapToRebuild;

    public RefreshEngine(Params params) {
        this.params = params;
    }

    @Override
    public void run() {
        clearSingleInstances();
        newMapToRebuild = new CatMap(params.getIslandNumber(), params.getMainIslandNumber(), params.getMainIslandWeight());
        //TODO: riprendi da qui a implementare
    }

    private void clearSingleInstances() {
        //TODO: riportare a null le signleton Instances e ripulire globalMapHandler e capire come refreshare la jFrame
    }


}

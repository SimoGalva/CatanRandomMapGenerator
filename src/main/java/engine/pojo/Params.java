package engine.pojo;

public class Params {
    private int islandNumber;
    private int mainIslandNumber;
    private int mainIslandWeight;

    public Params(int islandNumber, int mainIslandNumber, int mainIslandWeight) {
        this.islandNumber = islandNumber;
        this.mainIslandNumber = mainIslandNumber;
        this.mainIslandWeight = mainIslandWeight;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    public void setIslandNumber(int islandNumber) {
        this.islandNumber = islandNumber;
    }

    public int getMainIslandNumber() {
        return mainIslandNumber;
    }

    public void setMainIslandNumber(int mainIslandNumber) {
        this.mainIslandNumber = mainIslandNumber;
    }

    public int getMainIslandWeight() {
        return mainIslandWeight;
    }

    public void setMainIslandWeight(int mainIslandWeight) {
        this.mainIslandWeight = mainIslandWeight;
    }
}

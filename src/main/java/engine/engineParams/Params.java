package engine.engineParams;

public class Params {
    private int islandNumber;
    private int mainIslandNumber;
    private int mainIslandWeight;
    private int numberOfPlayer;

    public Params(int islandNumber, int mainIslandNumber, int mainIslandWeight, int numberOfPlayer) {
        this.islandNumber = islandNumber;
        this.mainIslandNumber = mainIslandNumber;
        this.mainIslandWeight = mainIslandWeight;
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
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

    @Override
    public String toString() {
        return "Params{" +
                "islandNumber = [" + islandNumber +
                "], mainIslandNumber = [" + mainIslandNumber +
                "], mainIslandWeight = [" + mainIslandWeight +
                "], numberOfPlayer = [" + numberOfPlayer +
                "]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Params params = (Params) o;
        return islandNumber == params.islandNumber && mainIslandNumber == params.mainIslandNumber && mainIslandWeight == params.mainIslandWeight && numberOfPlayer == params.numberOfPlayer;
    }
}

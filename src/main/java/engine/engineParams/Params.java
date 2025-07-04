package engine.engineParams;

import saving.ConfigHandler;
import utils.Constants;
import utils.exceptions.GenericLoadingException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.logging.Logger;

public class Params {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.PARAMS);

    private int islandNumber;
    private int mainIslandNumber;
    private int mainIslandWeight;
    private int numberOfPlayer;

    public Params(int islandNumber, int mainIslandNumber, int mainIslandWeight, int numberOfPlayer) {
        this.islandNumber = islandNumber;
        this.mainIslandNumber = mainIslandNumber;
        this.mainIslandWeight = mainIslandWeight;
        this.numberOfPlayer = numberOfPlayer;

        boolean isSavedConfing = false;
         try {
             isSavedConfing = ConfigHandler.getInstance().forceConfingSync(islandNumber, mainIslandNumber, mainIslandWeight, numberOfPlayer);
         }catch (GenericLoadingException e){/*non dovrebbe succedere mai*/}
        if (isSavedConfing) {
            logger.info("New params configuration saved: [" + this + "]");
        } else {
            logger.warning("Something went wrong in saving new params configuration. Data are lost.");
        }
    }

    //costruttore solo per i parametri da file config
    private Params(int[] parmasArray) {
        this.islandNumber = parmasArray[0];
        this.mainIslandNumber = parmasArray[1];
        this.mainIslandWeight = parmasArray[2];
        this.numberOfPlayer = parmasArray[3];
    }

    public static Params getRandomConstrainedParams(String constrain, int... value) {
        Params ret = null;
        switch (constrain) {
            case Constants.RANDOM_LOCK_NUMBER_PLAYER:
                logger.info("Generating params with constrain = [" + Constants.RANDOM_LOCK_NUMBER_PLAYER + "].");
                Random randomizer = new Random();

                PrimitiveIterator.OfInt iterator = Arrays.stream(value).iterator();
                int firstValue = iterator.nextInt();
                
                int numberOfIslandBound = (firstValue == 5 || firstValue == 4) ? 5 : 6;
                int numberOfIsland = randomizer.nextInt(numberOfIslandBound) + 1;
                int numberOfMainIsland = randomizer.nextInt(numberOfIsland) +1;
                int mainIslandWeight = (numberOfMainIsland == numberOfIsland) ? 10 : randomizer.nextInt(10)+1;
                
                ret = new Params(numberOfIsland,numberOfMainIsland ,mainIslandWeight, firstValue);
                break;
            default:
                logger.info("Invalid constrains. Returning null.");
                break;
        }

        return ret;
    }

    public static Params getLoadedParams() {
        int[] parmasFromConfig = null;
        try {
            parmasFromConfig  = ConfigHandler.getInstance().retrieveData();
        } catch (GenericLoadingException e){/*non dovrebbe succedere*/}
        return new Params(parmasFromConfig);
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

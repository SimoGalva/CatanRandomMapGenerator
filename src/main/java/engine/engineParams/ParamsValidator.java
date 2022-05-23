package engine.engineParams;

import java.util.Arrays;
import java.util.logging.Logger;

public class ParamsValidator {
    private final Logger logger = Logger.getLogger(getClass().getName());


    public boolean validate(Params params) {
        logger.info("validate: starting validation process.");
        boolean isValidParams = false;
        if (params == null) return false;
        if (
                ((params.getIslandNumber() >= 1 && params.getIslandNumber() <= 5 && params.getNumberOfPlayer() <= 4)
                || (params.getIslandNumber() >= 1 && params.getIslandNumber() <= 6 && params.getNumberOfPlayer() <= 6 && params.getNumberOfPlayer() > 4))
            && params.getMainIslandNumber() >= 1 && params.getMainIslandNumber() <= params.getIslandNumber()
            && params.getMainIslandWeight() >=1 && params.getMainIslandWeight() <= 10
            && Arrays.asList(3,4,5,6).contains(params.getNumberOfPlayer())) {
            if (params.getMainIslandNumber() == params.getIslandNumber()) {
                logger.info("validate: number of island equals to number of main island, forcing main island weight to 10.");
                params.setMainIslandWeight(10);
            }
            logger.info("validate: new params are valid.");
            isValidParams = true;
        } else {
            logger.info("validate: invalid params. Returning false.");
        }
        return isValidParams;
    }
}

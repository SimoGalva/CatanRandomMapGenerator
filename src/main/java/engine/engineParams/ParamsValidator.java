package engine.engineParams;

import java.util.logging.Logger;

public class ParamsValidator {
    private final Logger logger = Logger.getLogger(getClass().getName());


    public boolean validate(Params params) {
        logger.info("validate: starting validation process.");
        boolean isValidParams = false;
        if (params == null) return false;
        if (params.getIslandNumber() >= 1 && params.getIslandNumber() <= 5
            && params.getMainIslandNumber() >= 1 && params.getMainIslandNumber() <= params.getIslandNumber()
            && params.getMainIslandWeight() >=1 && params.getMainIslandWeight() <= 10
            && params.getNumberOfPlayer() == 4) {
            logger.info("validate: new params are valid.");
            isValidParams = true;
        }
        return isValidParams;
    }
}

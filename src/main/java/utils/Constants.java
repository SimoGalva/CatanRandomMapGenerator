package utils;

import java.awt.*;

public class Constants {

    public static final Color BACKGROUND_COLOR = new Color(0x6E6E72);

    public final static String NEXT_LINE = " \n";
    public final static String SEPARATOR = ";";

    public final static String LOAD = "load";
    public final static String SAVE = "save";
    public static final String RANDOM_LOCK_NUMBER_PLAYER = "random_NP_Lock";
    public static final String SCREENSHOT = "screenshot";



    public static class ConstantsButtons {
        public static final String DEFAULT_SAVE_LOAD_PATH_BUTTON = "DefaultLoadSavePathButton";
        public static final String CONFIRM_BUTTON = "ConfirmButton";
        public static final String CONFIRM_BUTTON_SETTINGS = "ConfirmButtonForSettings";
        public static final String CONFIRM_BUTTON_SAVE = "ConfirmButtonForSave";
        public static final String CONFIRM_BUTTON_LOAD = "ConfirmButtonForLoad";
        public static final String CONFIRM_BUTTON_ERROR = "ConfirmButtonError";
        public static final String REFRESH_BUTTON = "RefreshButton";
        public static final String SETTINGS_BUTTON = "SettingsButton";
        public static final String SAVE_BUTTON = "SaveButton";
        public static final String LOAD_BUTTON = "LoadButton";
        public static final String PRINT_BUTTON = "PrintButton";
    }

    public static class ConstantsTextLines {
        public static final String ISLAND_NUMBER = "IslandNumber";
        public static final String MAIN_ISLAND_NUMBER = "MainIslandNumber";
        public static final String MAIN_ISLAND_WEIGHT = "MainIslandWeight";
        public static final String PLAYER_NUMBER = "PlayerNumber";

        public static final String PATH = "Path";
        public static final String FILE_NAME = "File Name";

        public static final String ERROR_FRAME_MASSAGE_SAVE = "An error occurred while saving data. \n Please retry with different path and file name.";
        public static final String ERROR_FRAME_MASSAGE_LOAD = "An error occurred while loading data. \n Please retry with different path and file name.";
        public static final String ERROR_FRAME_MASSAGE_INVALID_PARAMS = "Inserted Invalid parameters retry inserting new ones.";
    }

    public static class PiecesForPlayers {
        public static final int PLAYER_3 = 44;
        public static final int PLAYER_4 = 51;
        public static final int PLAYER_5 = 58;
        public static final int PLAYER_6 = 65;
    }

    public class ConstrainsReloading {
        public static final String FORCE_RELOAD_FROM_SCRATCH = "FORCE_RELOAD_FROM_SCRATCH";
        public static final String MAY_NEED_RELOADING_FROM_SCRATCH = "MAY_RELOAD_FROM_SCRATCH";
        public static final String NORMAL_RELOADING = "NORMAL_RELOAD";
    }
}

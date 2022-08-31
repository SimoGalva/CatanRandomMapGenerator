package utils.logging;

public enum LoggingClassesEnum {
    ABSTRACT_COORDINATE_HANDLER 	            ("AbstractCoordinateHandler"),
    COORDINATE_HANDLER_3P 	                    ("CoordinateHandler3P"),
    COORDINATE_HANDLER_4P 	                    ("CoordinateHandler4P"),
    COORDINATE_HANDLER_5P 	                    ("CoordinateHandler5P"),
    COORDINATE_HANDLER_6P 	                    ("CoordinateHandler6P"),
    PARAMS 	                                    ("Params"),
    PARAMS_VALIDATOR 	                        ("ParamsValidator"),
    GENERATION_HELPER 	                        ("GenerationHelper"),
    MAIN_ENGINE 	                            ("MainEngine"),
    MAP_GENERATOR_ENGINE 	                    ("MapGeneratorEngine"),
    POST_GENERATION_HELPER 	                    ("PostGenerationHelper"),
    REFRESH_ENGINE 	                            ("RefreshEngine"),
    MODIFY_BUTTON 	                            ("ModifyButton"),
    REFRESH_BUTTON 	                            ("RefreshButton"),
    SAVE_BUTTON 	                            ("SaveButton"),
    SETTINGS_BUTTON 	                        ("SettingsButton"),
    CONFIRM_BUTTON 	                            ("ConfirmButton"),
    MAP_FRAME 	                                ("MapFrame"),
    MAP_PANEL                                   ("MapPanel"),
    SETTINGS_FRAME 	                            ("SettingsFrame"),
    NUMBER_LINE 	                            ("NumberLine"),
    FE_RUNNER 	                                ("FErunner"),
    CAT_MAP 	                                ("CatanMap"),
    GLOBAL_MAP_HANDLER 	                        ("MapHandler"),
    MATERIAL_COUNTER 	                        ("MaterialCounter"),
    MATERIAL_HANDLER 	                        ("MaterialHandler"),
    MATERIALS 	                                ("Materials"),
    NUMBER_COUNTER 	                            ("NumberCounter"),
    NUMBER_HANDLER 	                            ("NumberHandler"),
    NUMBERS 	                                ("Numbers"),
    HEXAGONAL_BASE                          	("HexagonalBase"),
    HEXAGON_FE 	                                ("HexagonFE"),
    HEXAGON_POINT 	                            ("HexagonPoint"),
    ISLAND 	                                    ("Island"),
    ISLAND_CONTROLLER 	                        ("IslandController"),
    DIAG_SETTINGS_HOLDER 	                    ("DiagSettingsHolder"),
    SWITCHING_HEXAGONS 	                        ("SwitchingHexagons"),
    CONSTANTS_FRONT_END 	                    ("Constants"),
    LOGGING_CLASSES_ENUM 	                    ("LoggingClassesEnum"),
    UTILS 	                                    ("Utils"),
    SAVING_FORMATTER                            ("SavingFormatter"),
MAP_SAVING_HANDLER                              ("MapSavingHandler");

    private String className;

    LoggingClassesEnum(String className) {
        this.className = className;
    }

    public String toString() {
        return this.name();
    }

    public String getValue(){
        return this.className;
    }
}

package com.boundmap.panner.utils;


public class ApplicationConstants {
    public static ApplicationConstants INSTANCE = new ApplicationConstants();
    public String SD_CARD_PATH = null;

    public static final String APP_CONFIG_FILENAME = "flight-planner-config.xml";
    public static final String MISSION_FILE_EXTENSION = ".dat";
    public static final String SENSOR_FILE_EXTENSION = ".cfg";
    public static final String CONF = "conf";
    public static final String MISSION_DATA_FILENAME = "MissionData.dat";
    public static final int GROUND_RESOLUTION_MIN_VAL = 0;
    public static final int GROUND_RESOLUTION_MAX_VAL = 30;
    public static final int GROUND_RESOLUTION_INCREMENT = 1;
    public static final String GROUND_RESOLUTION_UNIT ="cm";
    public static final int ALTITUDE_MIN_VAL = 0;
    public static final int ALTITUDE_MAX_VAL = 100;
    public static final int ALTITUDE_INCREMENT = 5;
    public static final String ALTITUDE_UNIT="m";
    public static final int DEFAULT_ALTITUDE_VALUE = 80;
    public static final int DEFAULT_GROUND_RESOLUTION_VALUE = 16;

    public static final String GOOGLE_MAP_API_URL = "https://maps.googleapis.com/maps/api/";
    public static final String GOOGLE_API_KEY = "AIzaSyBaSbTBz9T9eB57kzD-OArNDzxWG20886k";

    public static final String SENSOR_FILE_EXTENTION =".cfg";
    public static final String APPLICATION_VERSION_URL = "http://162.209.0.149/PrecisionHawk_Version.txt";

}


package sample;


public enum MapType {
    terrain("terrain"),
    roadmap("roadmap"),
    hybrid("hybrid");

    private String mapTypeValue;

    MapType(String mapTypeValue) {
        this.mapTypeValue = mapTypeValue;
    }

    public static MapType getMapTypeForConfigValue(String mapTypeValue) throws IllegalArgumentException {
        for (MapType mapType : values()) {
            if (mapType.getMapTypeConfigValue().equals(mapTypeValue)) {
                return mapType;
            }
        }

        throw new IllegalArgumentException("Invalid Map type configuration value specified: " + mapTypeValue);
    }

    public String getMapTypeConfigValue() {
        return mapTypeValue;
    }
}

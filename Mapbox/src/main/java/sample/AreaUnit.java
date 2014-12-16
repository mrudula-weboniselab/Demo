package sample;

public enum AreaUnit {
    SQUARE_METERS("Sq m", 1),
    SQUARE_KILOMETERS("Sq km", 1000000),
    HECTARES("Hectares", 10000),
    ACRES("Acres", 4046.86);

    private String unitDisplayName;
    private double squareMetersPerUnit;

    AreaUnit(String unitDisplayName, double squareMetersPerUnit) {
        this.unitDisplayName = unitDisplayName;
        this.squareMetersPerUnit = squareMetersPerUnit;
    }

    public String getDisplayStringForAreaInSquareMeters(Double enclosedAreaInSquareMeters) {
        String displayString = "Unknown";

        if (enclosedAreaInSquareMeters != null) {
            Double calculatedArea = calculateAreaFromSquareMeters(enclosedAreaInSquareMeters);

            switch (this) {
                case ACRES:
                case SQUARE_KILOMETERS:
                case HECTARES:
                    displayString = String.format("%.2f %s", calculatedArea, unitDisplayName);
                    break;

                case SQUARE_METERS:
                    displayString = String.format("%d %s", calculatedArea.intValue(), unitDisplayName);
                    break;
            }
        }

        return displayString;
    }

    public double calculateAreaFromSquareMeters(double areaInSquareMeters) {
        return areaInSquareMeters / squareMetersPerUnit;
    }



}


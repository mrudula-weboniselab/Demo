package com.boundmap.panner.models;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;



import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "waypoint")
public class Waypoint  {
    private static final int DEFAULT_ALTITUDE = 130;

    private DoubleProperty latitude;
    private DoubleProperty longitude;
    private IntegerProperty altitude;

    public Waypoint() {

    }

    public Waypoint(double latitude, double longitude) {
        this(latitude, longitude, DEFAULT_ALTITUDE);
    }

    public Waypoint(Double latitude, Double longitude, Integer altitude) {
        this.latitude = new SimpleDoubleProperty(this, "latitude", latitude);
        this.longitude = new SimpleDoubleProperty(this, "longitude", longitude);
        if (altitude != null) {
            this.altitude = new SimpleIntegerProperty(this, "altitude", altitude);
        } else {
            this.altitude = new SimpleIntegerProperty();
        }
    }

    public Double getLatitude() {
        return latitudeProperty().get();
    }

    public void setLatitude(Double latitude) {
        latitudeProperty().set(latitude);
    }

    public DoubleProperty latitudeProperty() {
        if (latitude == null) {
            latitude = new SimpleDoubleProperty(this, "latitude");
        }
        return latitude;
    }

    public Double getLongitude() {
        return longitudeProperty().get();
    }

    public void setLongitude(Double longitude) {
        longitudeProperty().set(longitude);
    }

    public DoubleProperty longitudeProperty() {
        if (longitude == null) {
            longitude = new SimpleDoubleProperty(this, "longitude");
        }
        return longitude;
    }

    public Integer getAltitude() {
        return altitudeProperty().get();
    }

    public void setAltitude(Integer altitude) {
        altitudeProperty().set(altitude);
    }

    public IntegerProperty altitudeProperty() {
        if (altitude == null) {
            altitude = new SimpleIntegerProperty(this, "altitude");
        }
        return altitude;
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                append(latitude.doubleValue()).
                append(longitude.doubleValue()).
                append(altitude.intValue()).
                hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Waypoint rhs = (Waypoint) obj;

        return new EqualsBuilder().
                append(latitude.doubleValue(), rhs.latitude.doubleValue()).
                append(longitude.doubleValue(), rhs.longitude.doubleValue()).
                append(altitude.doubleValue(), rhs.altitude.doubleValue()).
                isEquals();
    }
}

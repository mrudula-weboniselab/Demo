package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by webonise on 15-12-2014.
 */




        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configuration")
public class MapConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MapConfig.class);

    private String cardPath;
    private String lastLoadedMissionName;
    private AreaUnit coverageAreaUnit;
    private MapType mapType;
    private String skipVersion;

    private Boolean enableSSL;
    private Boolean isGeofencingEnable;
    private Integer windowX;
    private Integer windowY;
    private Integer windowWidth;
    private Integer windowHeight;

    public MapConfig() {
        coverageAreaUnit = AreaUnit.ACRES;
        mapType = MapType.terrain;

    }

    public MapConfig(MapConfig mapConfig) {
        this.cardPath = mapConfig.getCardPath();
        this.lastLoadedMissionName = mapConfig.getLastLoadedMissionName();
        this.coverageAreaUnit = mapConfig.getCoverageAreaUnit();
        this.skipVersion = mapConfig.getSkipVersion();

        this.enableSSL = mapConfig.getEnableSSL();
        this.isGeofencingEnable = mapConfig.isGeofencingEnabled();
        this.windowX = mapConfig.getWindowX();
        this.windowY = mapConfig.getWindowY();
        this.windowWidth = mapConfig.getWindowWidth();
        this.windowHeight = mapConfig.getWindowHeight();
    }

    public String getCardPath() {
        return cardPath;
    }

    public void setCardPath(String cardPath) {
        this.cardPath = cardPath;
    }

    public String getLastLoadedMissionName() {
        return lastLoadedMissionName;
    }

    public void setLastLoadedMissionName(String lastLoadedMissionName) {
        this.lastLoadedMissionName = lastLoadedMissionName;
    }

    public AreaUnit getCoverageAreaUnit() {
        return coverageAreaUnit;
    }

    public void setCoverageAreaUnit(AreaUnit coverageAreaUnit) {
        this.coverageAreaUnit = coverageAreaUnit;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public String getSkipVersion() {
        return skipVersion;
    }

    public void setSkipVersion(String skipVersion) {
        this.skipVersion = skipVersion;
    }

    /**
     * The api base URL. This property is unique in that it should not be
     * written to the configuration file but it should be read and used instead
     * of the default base URL if it has been manually configured.
     *
     * @return
     */




    public Boolean getEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(Boolean sslEnabled) {
        this.enableSSL = sslEnabled;
    }

    public Boolean isGeofencingEnabled() {
        return isGeofencingEnable;
    }

    public void setGeofencingEnabled(Boolean isGeofencingEnable) {
        this.isGeofencingEnable = isGeofencingEnable;
    }

    public Integer getWindowX() {
        return windowX;
    }

    public void setWindowX(Integer windowX) {
        this.windowX = windowX;
    }

    public Integer getWindowY() {
        return windowY;
    }

    public void setWindowY(Integer windowY) {
        this.windowY = windowY;
    }

    public Integer getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(Integer windowWidth) {
        this.windowWidth = windowWidth;
    }

    public Integer getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(Integer windowHeight) {
        this.windowHeight = windowHeight;
    }
}

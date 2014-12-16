package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class GoogleMapConfig extends VBox {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleMapConfig.class);

    private MapConfig mapConfig;

    @FXML
    private WebView webView;

    private DoubleProperty surveyAreaInSqKm = new SimpleDoubleProperty();
    private StringProperty surveyAreaDisplay = new SimpleStringProperty();

    @FXML
    private VBox noNetConnection;

    @FXML
    private VBox map;

    private JSObject missionEditor;


    public GoogleMapConfig() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GoogleMapsRegion.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException("Error loading GoogleMapsRegion: " + ex.getMessage(), ex);
        }

        surveyAreaInSqKm.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                surveyAreaDisplay.set(mapConfig.getCoverageAreaUnit().getDisplayStringForAreaInSquareMeters(surveyAreaInSqKm.get()));
            }
        });
    }

}

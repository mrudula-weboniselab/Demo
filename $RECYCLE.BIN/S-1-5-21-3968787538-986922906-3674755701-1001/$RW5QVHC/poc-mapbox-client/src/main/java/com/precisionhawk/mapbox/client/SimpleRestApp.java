package com.precisionhawk.mapbox.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the client-side JavaFX REST application. This loads the JavaFX UI via a Spring-based application
 * context and presents it to the user.
 */
public class SimpleRestApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(SimpleRestApp.class);

    private static Collection<String> allCss;
    private static WebView wv;

    static {
    	
    	allCss = new ArrayList<String>();
		allCss.add("/styles/styles.css");
		allCss.add("https://api.tiles.mapbox.com/mapbox.js/v2.1.4/mapbox.css");
		allCss.add("https://api.tiles.mapbox.com/mapbox.js/plugins/leaflet-draw/v0.2.2/leaflet.draw.css");

    }
    
    private WebEngine we;

    /**
     * Main entry point called when the application starts. This follows the typical JavaFX pattern of delegating
     * straight to the Application.launch method, which then triggers the start() method below.
     *
     * @param args any line arguments passed to the application at startup. This may be from the command line or from
     *             the the launch file if called from Webstart or an Applet, etc.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Main JavaFX initialisation method which is called indirectly by the main() method above on startup. This method
     * loads the user interface from a Spring-based application context and adds it to the provided Stage.
     *
     * @param stage the main Stage (i.e. Window) that the application is to run within.
     * @throws ScriptException 
     * @throws FileNotFoundException 
     */
    public void start(Stage stage) throws ScriptException, FileNotFoundException {

        log.info("Starting SimpleRestApp application");

        showPolygon();
        StackPane root = new StackPane();
        root.getChildren().add(wv);

        Scene scene = new Scene(root, 750, 700);
        scene.getStylesheets().addAll(allCss);

        stage.setScene(scene);
        stage.setTitle("Precision Hawk Mapbox Demo");
        stage.show();
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.put("stage", stage);
        //engine.eval("load('nashorn:mozilla_compat.js')");
        //engine.eval("load('https://api.tiles.mapbox.com/mapbox.js/v2.1.4/mapbox.js')");

        //engine.eval(new FileReader("src/main/resources/polygon.js"));
        //engine.eval("load('https://api.tiles.mapbox.com/mapbox.js/plugins/leaflet-draw/v0.2.2/leaflet.draw.js')");
        //engine.eval("load('https://api.tiles.mapbox.com/mapbox.js/plugins/leaflet-geodesy/v0.1.0/leaflet-geodesy.js')");
    }
    
    private void showPolygon() {
    	
        try {
        	
            wv = new WebView();
            wv.setVisible(true);
            we = wv.getEngine();
            we.setJavaScriptEnabled(true);
            File file = new File("src/main/resources/fxml/polygon.html");
            we.load(file.toURI().toURL().toString());
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        }




    
    }

}

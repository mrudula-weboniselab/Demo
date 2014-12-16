package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // create BorderPane
        BorderPane borderPane = new BorderPane();
        // create web engine and view
        WebView webView = new WebView();

        /*finds page.html resource relative to the class location
        page.html is the name of the desired resource.*/
        String url = getClass().getResource("../drawMarker.html").toExternalForm();

        //Using webView and webEngine load url of resource
        webView.getEngine().load(url);
        borderPane.setCenter(webView);
        // create scene
        final Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        //SetTitle
        stage.setTitle("CSV Demo");
        //provide Height and Width
        stage.setHeight(300);
        stage.setWidth(250);
        // show stage
        stage.show();


    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
               launch(args);
    }
}

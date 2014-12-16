package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by webonise on 11-12-2014.
 */
public class Main extends Application {


    import javafx.application.Application;
    import javafx.application.Platform;
    import javafx.beans.property.StringProperty;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.event.EventHandler;
    import javafx.geometry.Insets;
    import javafx.scene.Node;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Menu;
    import javafx.scene.control.MenuBar;
    import javafx.scene.control.MenuItem;
    import javafx.scene.control.TableView;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.ColumnConstraints;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.RowConstraints;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.stage.Stage;

    public class Main extends Application {

        public void start(Stage primaryStage) throws Exception {


            primaryStage.setTitle("Employee Resource Center");
            BorderPane root = new BorderPane();
            GridPane gpane = new GridPane();
            Scene scene = new Scene(root, 900, 600, Color.BLACK);
            //grid pane layout
            gpane.setPadding(new Insets(4));
            //scene.getStylesheets().addAll(Main.class.getResource("myStyle.css").toExternalForm());
            gpane.setHgap(10);
            gpane.setVgap(20);
            VBox dataBox = new VBox();
            ColumnConstraints column1 = new ColumnConstraints(50, 100, 200);
            ColumnConstraints column2 = new ColumnConstraints(50, 100, 200);
            ColumnConstraints column3 = new ColumnConstraints(50, 100, 400);
            RowConstraints row1 = new RowConstraints(400);
            RowConstraints row2 = new RowConstraints(400);
            row1.setMaxHeight(50);
            row2.setMaxHeight(400);
            final TableView<ObservableList<StringProperty>> table = new TableView<>();
            String[] headerValues;

            //menu bar
            MenuBar menuBar = new MenuBar();
            root.setTop(menuBar);
            Menu fileMenu = new Menu("Menu");
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.setOnAction(actionEvent -> Platform.exit());
            fileMenu.getItems().addAll(exitMenuItem);
            dataBox.setMinSize(600, 600);
            //add background color
            //gpane.getStyleClass().add("my-gpane");

            //buttons
            Button getDtaBtn = new Button("Get Data");
            Button entrDtaBtn = new Button("Enter Data");
            Button genShftRpt = new Button ("Generate Shift Report");

            //add stuff together
            gpane.getColumnConstraints().addAll(column1, column2, column3);
            menuBar.getMenus().add(fileMenu);
            gpane.add(getDtaBtn, 1, 1);
            gpane.add(entrDtaBtn, 2, 1);
            gpane.add(genShftRpt, 3, 1);
            root.setCenter(gpane);
            gpane.add(dataBox, 1, 2);

            //make buttons do stuff
            getDtaBtn.setOnAction(new EventHandler<ActionEvent>(){
                public void handle (ActionEvent event){
                    dataBox.getChildren().clear();
                    String csvFileToRead = "resources/MOCK_DATA.csv";
                    DataImporter myData = new DataImporter();
                    Boolean hasHeader = true;
                    myData.populateTable(table, csvFileToRead, hasHeader);

                    dataBox.setMaxSize(200,600);
                    dataBox.getChildren().add(table);

                }

            });

            entrDtaBtn.setOnAction(new EventHandler<ActionEvent>(){
                public void handle (ActionEvent event){
                    dataBox.getChildren().clear();
                    String csvFileToRead = "resources/MOCK_DATA.csv";
                    Boolean hasHeader = true;
                    EnterDataToFile newData = new EnterDataToFile(table, csvFileToRead, hasHeader);

                    dataBox.getChildren().add(newData.displayForm());
                }


            });

            genShftRpt.setOnAction(new EventHandler<ActionEvent>(){
                public void handle (ActionEvent event){
                    dataBox.getChildren().clear();
                    String csvFileToRead = "resources/MOCK_DATA.csv";
                    Boolean hasHeader = true;
                    ShiftPlan newShiftPlan = new ShiftPlan(table, csvFileToRead, hasHeader);
                    dataBox.getChildren().add(newShiftPlan.displayForm());
                    dataBox.getChildren().add(table);
                }


            });

            primaryStage.setScene(scene);
            primaryStage.show();


        }


        public static void main(String[] args) {
            launch(args);

        }
    }
    Shift Plan Class:
}

package com.boundmap.panner.region;



import com.boundmap.javafx.controls.AdvancedTextField;
import com.boundmap.javafx.controls.PromptDialog;
import com.boundmap.panner.ApplicationState;
import com.boundmap.panner.controls.WaypointEditingCell;
import com.boundmap.panner.models.Waypoint;
import com.boundmap.panner.utils.ApplicationConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;


public class BoundedMapRegion extends VBox {
    private static final Logger LOG = LoggerFactory.getLogger(FlightPlannerRegion.class);

    @FXML
    private TableView<Waypoint> tableViewWayPoints;
    @FXML private RadioButton radioButtonLinear;
    @FXML private RadioButton radioButtonBounded;
    @FXML private Slider altitudeSlider;
    @FXML private Label labelSliderMinValue;
    @FXML private Label labelSliderMaxValue;
    @FXML private Button buttonGroundResolution;
    @FXML private Button buttonAltitude;
    @FXML private Label labelSliderValue;
    @FXML private TextField textFieldAddWaypointLatitude;
    @FXML private TextField textFieldAddWaypointLongitude;

    private ApplicationState applicationState;

    public BoundedMapRegion(){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MapBound.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initializeWaypointsTable();

    }

    public void initializeWaypointsTable() {

        tableViewWayPoints.setEditable(true);

        tableViewWayPoints.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    final PromptDialog dialog = new PromptDialog(String.format("Delete waypoint?"));
                    dialog.removeButton(dialog.getCancelButton());

                    EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            EventTarget targetButton = actionEvent.getTarget();
                            if (targetButton.equals(dialog.getYesButton())) {
                                //deleteSelectedWayPoints();
                            }

                        }
                    };

                    dialog.setButtonsHandler(eventHandler);
                    dialog.show();
                }
            }
        });

        tableViewWayPoints.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                markSelectedWayPointFromTable();
            }
        });

        Callback<TableColumn, TableCell> latLongCellFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new WaypointEditingCell(AdvancedTextField.DOUBLE_REGEX);
            }
        };

        TableColumn latitudeCol = new TableColumn<Waypoint, Double>("Latitude");
        latitudeCol.prefWidthProperty().bind(tableViewWayPoints.widthProperty().divide(2));
        latitudeCol.setCellValueFactory(new PropertyValueFactory("latitude"));
        latitudeCol.setCellFactory(latLongCellFactory);
        latitudeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Waypoint, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Waypoint, Double> t) {
                        ((Waypoint) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLatitude(t.getNewValue());
                       // applicationState.commitChanges();
                        markSelectedWayPointFromTable();
                    }
                });

        TableColumn longitudeCol = new TableColumn<Waypoint, Double>("Longitude");
        longitudeCol.prefWidthProperty().bind(tableViewWayPoints.widthProperty().divide(2));
        longitudeCol.setCellValueFactory(new PropertyValueFactory("longitude"));
        longitudeCol.setCellFactory(latLongCellFactory);
        longitudeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Waypoint, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Waypoint, Double> t) {
                        ((Waypoint) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLongitude(t.getNewValue());
                        //applicationState.commitChanges();
                        markSelectedWayPointFromTable();
                    }
                });

        tableViewWayPoints.getColumns().setAll(latitudeCol, longitudeCol);
    }


    private void markSelectedWayPointFromTable(){
        applicationState.setSelectedWaypoint(tableViewWayPoints.getSelectionModel().getSelectedItem());
    }









    public void setApplicationState(final ApplicationState applicationState) {
        if (this.applicationState != null) {
            throw new RuntimeException("ApplicationState may only be set once!");
        }

        this.applicationState = applicationState;


        this.applicationState.addEventHandler(ApplicationState.ApplicationStateEvent.SELECTED_WAYPOINT_UPDATED, new EventHandler<ApplicationState.ApplicationStateEvent>() {
            @Override
            public void handle(ApplicationState.ApplicationStateEvent applicationStateEvent) {
                LOG.debug("Handling selected waypoint update in FlightPlannerRegion");
                tableViewWayPoints.getSelectionModel().select(applicationState.getSelectedWaypoint());
            }
        });


    }

}


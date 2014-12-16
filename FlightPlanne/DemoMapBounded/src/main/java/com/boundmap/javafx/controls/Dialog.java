package com.boundmap.javafx.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class Dialog {
    private static final Logger LOG = LoggerFactory.getLogger(Dialog.class);

    private static final double DIALOG_MIN_WIDTH = 400;
    private static final double DIALOG_MIN_HEIGHT = 100;
    private static final double DEFAULT_PADDING = 10;
    private static final double DEFAULT_BUTTON_SPACING = 10;


    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private HBox controlsBar;
    private Region contentRegion;
    private boolean visible = false;
    private Map<Button, KeyCode> buttonsKeyMap = new HashMap<>();

    public Dialog() {
        this(null);
    }

    public Dialog(Window owner) {
        stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setIconified(false);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.initOwner(owner);


        controlsBar = new HBox();
        controlsBar.setAlignment(Pos.CENTER_RIGHT);
        controlsBar.setPadding(new Insets(DEFAULT_PADDING));
        controlsBar.setSpacing(DEFAULT_BUTTON_SPACING);

        borderPane = new BorderPane();
        borderPane.setBottom(controlsBar);
        borderPane.setCenter(contentRegion);
        borderPane.setPadding(new Insets(DEFAULT_PADDING));
        borderPane.setStyle("-fx-border-width:5;-fx-border-color:#c1262d;-fx-border-style:solid;-fx-border-radius:1;-fx-padding:20;");

        scene = new Scene(borderPane);
        stage.setMinWidth(DIALOG_MIN_WIDTH);
        stage.setMinHeight(DIALOG_MIN_HEIGHT);
        stage.setScene(scene);

    }

    public void show() {
        stage.show();
        visible = true;
    }

    public void close() {
        stage.close();
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setContentRegion(Region contentRegion) {
        this.contentRegion = contentRegion;
        borderPane.setCenter(contentRegion);
    }

    public Button addButton(String buttonText, EventHandler<ActionEvent> eventHandler, final KeyCode keyCode) {
        final Button button = new Button(buttonText);
        button.addEventHandler(ActionEvent.ACTION, eventHandler);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(keyCode)) {
                    button.fire();
                }
            }
        });
        buttonsKeyMap.put(button, keyCode);
        controlsBar.getChildren().add(button);
        return button;
    }

    public void removeButton(Button button) {
        controlsBar.getChildren().remove(button);
        if (buttonsKeyMap.containsKey(button)) {
            buttonsKeyMap.remove(button);
        }
    }

    public void addButton(Button button) {
        controlsBar.getChildren().add(button);
    }


}

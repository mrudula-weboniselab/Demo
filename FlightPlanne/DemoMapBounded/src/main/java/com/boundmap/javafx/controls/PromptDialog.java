package com.boundmap.javafx.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by webonise on 16-12-2014.
 */
public class PromptDialog extends Dialog {
    private static final Logger LOG = LoggerFactory.getLogger(PromptDialog.class);

    private Label promptLabel;
    private ImageView iconImageView;
    private Button yesButton;
    private Button noButton;
    private Button cancelButton;

    public PromptDialog(String promptText) {
        this(null, promptText,"/images/confirmationIcon.png");
    }

    public PromptDialog(Window owner, String promptText, String iconImage) {
        super(owner);

        promptLabel = new Label(promptText);
        iconImageView = new ImageView(iconImage);

        HBox contentRegion = new HBox();
        contentRegion.setAlignment(Pos.CENTER);
        contentRegion.getChildren().add(iconImageView);
        contentRegion.getChildren().add(promptLabel);
        setContentRegion(contentRegion);

        yesButton = addButton("Yes", getDefaultButtonHandler(), KeyCode.ENTER);
        noButton = addButton("No", getDefaultButtonHandler(), KeyCode.N);
        cancelButton = addButton("Cancel", getDefaultButtonHandler(), KeyCode.ESCAPE);
        yesButton.setFocusTraversable(true);
    }


    private EventHandler<ActionEvent> getDefaultButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        };
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setButtonsHandler(EventHandler<ActionEvent> eventHandler) {
        getYesButton().setOnAction(eventHandler);
        getNoButton().setOnAction(eventHandler);
        getCancelButton().setOnAction(eventHandler);
    }

}


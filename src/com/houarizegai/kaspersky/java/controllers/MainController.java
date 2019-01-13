package com.houarizegai.kaspersky.java.controllers;

import com.houarizegai.kaspersky.java.Launcher;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;

    // For Make Stage Drageable
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeStageDrageable();
    }

    private void makeStageDrageable() {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launcher.stage.setX(event.getScreenX() - xOffset);
                Launcher.stage.setY(event.getScreenY() - yOffset);
                Launcher.stage.setOpacity(0.7f);
            }
        });
        root.setOnDragDone((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });
        root.setOnMouseReleased((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });

    }

    @FXML
    private void onHide() {
        Launcher.stage.setIconified(true);
    }

    @FXML
    private void onClose() {
        Platform.exit();
    }
}

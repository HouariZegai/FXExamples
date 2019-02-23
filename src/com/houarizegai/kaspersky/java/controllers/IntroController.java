package com.houarizegai.kaspersky.java.controllers;

import com.houarizegai.kaspersky.java.Launcher;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {
    @FXML
    private StackPane root;

    // For Make Stage Drageable
    private double xOffset = 0;
    private double yOffset = 0;

    private StackPane homeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeStageDrageable();

        try {
            homeView = FXMLLoader.load(getClass().getResource("/com/houarizegai/kaspersky/resources/views/Home.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
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
    private void onContinue() {
        ((Stage) root.getScene().getWindow()).setScene(new Scene(homeView));
    }

    @FXML
    private void onSkip() {
        ((Stage) root.getScene().getWindow()).setScene(new Scene(homeView));
    }
}

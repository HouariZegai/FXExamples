package com.houarizegai.musicplayer;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicPlayerController implements Initializable {
    @FXML
    private VBox paneMusic;
    @FXML
    private VBox paneSettings;

    @FXML
    private JFXHamburger hamburgerShowHideMenu;

    @FXML
    private Label fieldMusicName;

    @FXML
    private JFXProgressBar progressMusic;

    // For Make Stage Drageable
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeStageDrageable();
        initMenu();
    }

    // For make stage Drageable
    private void makeStageDrageable() {
        paneMusic.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        paneMusic.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launcher.stage.setX(event.getScreenX() - xOffset);
                Launcher.stage.setY(event.getScreenY() - yOffset);
                Launcher.stage.setOpacity(0.7f);
            }
        });
        paneMusic.setOnDragDone((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });
        paneMusic.setOnMouseReleased((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });

    }

    // For show or hide menu
    private void initMenu() {
        HamburgerBasicCloseTransition hamburgerTransition = new HamburgerBasicCloseTransition(hamburgerShowHideMenu);
        hamburgerTransition.setRate(-1);
        hamburgerShowHideMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(hamburgerTransition.getRate() == -1) {
                hamburgerTransition.setRate(1);

                paneSettings.setVisible(true);
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(1.4));
                ft.setFromValue(0.0d);
                ft.setToValue(1.0d);
                ft.setNode(paneSettings);
                ft.play();
            } else {
                hamburgerTransition.setRate(-1);

                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(1.4));
                ft.setFromValue(1.0d);
                ft.setToValue(0.0d);
                ft.setNode(paneSettings);
                ft.play();
                ft.setOnFinished(event -> {
                    paneSettings.setVisible(false);
                });
            }
            hamburgerTransition.play();
        });
    }

    /* Start Control window */

    @FXML
    private void onHide(MouseEvent event) {
        ((Stage) fieldMusicName.getScene().getWindow()).setIconified(true);
    }
    @FXML
    private void onClose(MouseEvent event) {
        Platform.exit();
    }

    /* End Control window */

    /* Start Status Music */

    @FXML
    private void onNext(MouseEvent event) {

    }

    @FXML
    private void onPlayPause(MouseEvent event) {
        FontAwesomeIconView iconPausePlay = ((FontAwesomeIconView) event.getSource());
        iconPausePlay.setGlyphName((iconPausePlay.getGlyphName().equals("PAUSE")? "PLAY" : "PAUSE"));
    }

    @FXML
    private void onPrevious(MouseEvent event) {

    }

    /* End Status Music */
}

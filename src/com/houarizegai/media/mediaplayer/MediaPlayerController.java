package com.houarizegai.media.mediaplayer;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MediaPlayerController implements Initializable {
    @FXML
    private BorderPane root;

    @FXML
    private Label lblMediaTitle;

    @FXML
    private JFXSlider sliderMedia, sliderVolume;

    @FXML
    private Label lblCurrentTimeMedia, lblTotalTimeMedia;

    @FXML
    private StackPane mediaContainer;

    @FXML
    private MediaView mediaView;

    @FXML
    private FontAwesomeIconView iconPlayPause;

    private MediaPlayer mediaPlayer;

    private FileChooser fileChooser;

    boolean isTotalMaked; // This boolean using just for fixing problem of total time = 0

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Init file chooser for load media */
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Select the media", "*.mp3", "*.mp4");
        fileChooser.getExtensionFilters().add(extensionFilter);

        //For auto-resize
        mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height").subtract(197));
        mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width").subtract(40));

        // Make keyborad typed listener (shortcut)
        root.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.SPACE))
                onPlayPause();
            if(e.getCode().equals(KeyCode.O))
                onOpen();

            if(e.getCode().equals(KeyCode.ADD))
                sliderVolume.setValue(sliderVolume.getValue() + 10);
            if(e.getCode().equals(KeyCode.SUBTRACT))
                sliderVolume.setValue(sliderVolume.getValue() - 10);
        });
    }

    @FXML
    private void onFullScreen() {
        Stage stage = ((Stage) mediaView.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void onOpen() {
        try {
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile == null)
                return;

            String filePath = selectedFile.toURI().toString();
            sliderMedia.setDisable(false); // Enable change the value of media slider

            // Make the name of media for showing in app
            lblMediaTitle.setText(filePath.split("/")[filePath.split("/").length -1].replace("%20", " "));

            mediaPlayer = new MediaPlayer(new Media(filePath));

            // Auto change value of slider & time
            mediaPlayer.currentTimeProperty().addListener(e -> {
                updateValues();
            });

            // Auto change video time when i change the value of slider
            sliderMedia.valueProperty().addListener(e -> {
                if(sliderMedia.isPressed()) {
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(sliderMedia.getValue() / 100));
                }
            });

            iconPlayPause.setGlyphName("PAUSE");
            
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            mediaPlayer.setAutoPlay(false);

            sliderVolume.setValue(100); // set volume to default (100%)

            /* Change label of total media time */
            int totalMediaTime = (int) mediaPlayer.getTotalDuration().toMillis();
            lblTotalTimeMedia.setText(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalMediaTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalMediaTime) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalMediaTime)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(totalMediaTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMediaTime))));

            // Auto change media volume
            mediaPlayer.volumeProperty().bind(sliderVolume.valueProperty().divide(100));

        } catch (MediaException me) {
            me.printStackTrace();
        }
    }

    @FXML
    private void onReplay() {
        if(mediaPlayer == null)
            return;

        mediaPlayer.seek(mediaPlayer.getStartTime());
    }

    @FXML
    private void onPlayPause() {
        if(mediaPlayer == null)
            return;

        if(iconPlayPause.getGlyphName().equals("PAUSE")) { // is paused ?
            mediaPlayer.pause();
            iconPlayPause.setGlyphName("PLAY");
        } else {
            mediaPlayer.play();
            iconPlayPause.setGlyphName("PAUSE");
        }

    }

    private void updateValues() { // Auto change value of slider & time
        sliderMedia.setValue(mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);

        int currentMediaTime = (int) mediaPlayer.getCurrentTime().toMillis();
        lblCurrentTimeMedia.setText(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(currentMediaTime),
                TimeUnit.MILLISECONDS.toMinutes(currentMediaTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(currentMediaTime)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(currentMediaTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentMediaTime))));

        if(!isTotalMaked) {
            /* Change label of total media time */
            int totalMediaTime = (int) mediaPlayer.getTotalDuration().toMillis();

            if(totalMediaTime == 0)
                return;

            lblTotalTimeMedia.setText(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalMediaTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalMediaTime) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalMediaTime)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(totalMediaTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMediaTime))));

            isTotalMaked = true;
        }

    }
}

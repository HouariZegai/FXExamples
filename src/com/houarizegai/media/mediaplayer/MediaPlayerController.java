package com.houarizegai.media.mediaplayer;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MediaPlayerController implements Initializable {
    @FXML
    private Label lblMediaTitle;

    @FXML
    private JFXSlider sliderMedia, sliderVolume;

    @FXML
    private Label lblCurrentTimeMedia, lblTotalTimeMedia;

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    private FileChooser fileChooser;

    boolean isTotalMaked; // This boolean using just for fixing problem of total time = 0

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Init file chooser for load media */
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Select the media", "*.mp3", "*.mp4");
        fileChooser.getExtensionFilters().add(extensionFilter);

        /* bind video size with gui */
//        DoubleProperty width = mediaView.fitWidthProperty();
//        DoubleProperty height = mediaView.fitHeightProperty();
//        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
//        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    }

    @FXML
    private void onOpen() {
        try {
            File selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile == null)
                return;

            String filepath = selectedFile.toURI().toString();
            sliderMedia.setDisable(false); // Enable change the value of media slider

            mediaPlayer = new MediaPlayer(new Media(filepath));

            // Auto change value of slider & time
            mediaPlayer.currentTimeProperty().addListener(e -> {
                updateValues();
            });

            // Auto Change video time when i change the value of slider
            sliderMedia.valueProperty().addListener(e -> {
                if(sliderMedia.isPressed()) {
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(sliderMedia.getValue() / 100));
                }
            });

            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            mediaPlayer.setAutoPlay(false);

            /* Change label of total media time */
            int totalMediaTime = (int) mediaPlayer.getTotalDuration().toMillis();
            System.out.println(totalMediaTime);
            lblTotalTimeMedia.setText(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalMediaTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalMediaTime) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalMediaTime)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(totalMediaTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMediaTime))));

            // Auto change media volume
            sliderVolume.valueProperty().addListener(e -> {
                if(sliderVolume.isPressed()) {
                    mediaPlayer.setVolume(sliderVolume.getValue() / 100);
                }
            });

        } catch (MediaException me) {
            me.printStackTrace();
        }
    }

    @FXML
    private void onReplay() {
        mediaPlayer.seek(mediaPlayer.getStartTime());
    }

    @FXML
    private void onPlay() {
        mediaPlayer.play();
    }

    @FXML
    private void onPause() {
        mediaPlayer.pause();
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

        if(isTotalMaked)
            return;

        /* Change label of total media time */
        int totalMediaTime = (int) mediaPlayer.getTotalDuration().toMillis();
        System.out.println(totalMediaTime);
        lblTotalTimeMedia.setText(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(totalMediaTime),
                TimeUnit.MILLISECONDS.toMinutes(totalMediaTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalMediaTime)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(totalMediaTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMediaTime))));
        isTotalMaked = true;
    }
}

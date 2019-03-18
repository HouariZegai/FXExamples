package com.houarizegai.media.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/houarizegai/media/musicplayer/MusicPlayer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        Launcher.stage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

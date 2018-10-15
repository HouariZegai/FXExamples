package com.houarizegai.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/houarizegai/musicplayer/MusicPlayer.fxml"));
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        Launcher.stage = stage;
        stage.show();
    }
}

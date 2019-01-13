package com.houarizegai.pgcd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/houarizegai/pgcd/Main.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("PGCD");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

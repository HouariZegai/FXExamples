package com.houarizegai.convertcurrency.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/houarizegai/convertcurrency/resources/views/ConvertCurrency.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Convert Currency");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

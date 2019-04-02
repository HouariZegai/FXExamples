package com.houarizegai.logicaleditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogicalEditorDemo extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LogicalEditor.fxml"));
            stage.setScene(new Scene(root));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        stage.setTitle("Logical Editor");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

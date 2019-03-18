package com.houarizegai.media.youtubeplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class YoutubePlayerDemo extends Application {

    @Override
    public void start(Stage stage) {
        WebView webview = new WebView();
        webview.getEngine().load(
                "http://www.youtube.com/embed/zPl8eLLLJYU?autoplay=1"
        );
        webview.setPrefSize(640, 390);

        stage.setScene(new Scene(webview));
        stage.setTitle("Youtube Player");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}

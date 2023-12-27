package ru.kpfu.itis.oris.nikolaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    final static String api_key = "8abc4289-b0c6-425c-8bb6-35772b27d254";
    final int v = 420;
    final int v1 = 400;
    String title = "Airplanes";
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
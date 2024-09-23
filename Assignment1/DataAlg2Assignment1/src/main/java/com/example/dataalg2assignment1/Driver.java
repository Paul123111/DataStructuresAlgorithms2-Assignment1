package com.example.dataalg2assignment1;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends javafx.application.Application {
    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("main-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1050, 500);
        stage.setTitle("Pills and Image Analyser");
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void exit() {
        stage.close();
    }
}
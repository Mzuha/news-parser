package com.mzuha.newsparser;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {

    public static final String TITLE = "News";

    public static void launchJavaFXApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("GET NEWS");
        Group root = new Group();
        root.getChildren().add(button);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

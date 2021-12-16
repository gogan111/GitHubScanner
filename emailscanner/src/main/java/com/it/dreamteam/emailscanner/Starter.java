package com.it.dreamteam.emailscanner;

import com.it.dreamteam.emailscanner.controller.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static String loginScreen = "loginScreen";
    public static String loginScreenFile = "login-view.fxml";
    public static String mainScreen = "mainScreen";
    public static String mainScreenFile = "main-view.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        ScreensController controller = new ScreensController(this.getHostServices());
        controller.loadScreen(Starter.loginScreen, Starter.loginScreenFile);
        controller.loadScreen(Starter.mainScreen, Starter.mainScreenFile);

        controller.setScreen(Starter.loginScreen);
        Group root = new Group();
        root.getChildren().addAll(controller);
        Scene scene = new Scene(root);
        stage.setTitle("GitScanner");
        stage.setScene(scene);
        stage.show();
    }
}

package me.petrolingus.jlt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("JavaFX Canvas");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.showingProperty().addListener((observable, oldValue, newValue) -> System.exit(0));
    }
}
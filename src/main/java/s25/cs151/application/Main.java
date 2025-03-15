package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Office Hero - Welcome!");
        MainMenuPage.setActive(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
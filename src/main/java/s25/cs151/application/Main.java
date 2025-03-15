package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MainMenuPage mainMenu = new MainMenuPage();
        mainMenu.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
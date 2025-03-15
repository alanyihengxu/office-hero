package s25.cs151.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene mainMenu = SceneProvider.getMainScene();
        Scene officeHours = SceneProvider.getOfficeHoursScene();

        stage.setTitle("Office Hero");

        stage.setScene(mainMenu);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
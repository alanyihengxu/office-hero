package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    private void config() throws IOException{
        File file = new File("data/office_hours.csv");

        // Create file if it doesn't exist
        if (file.createNewFile()) {
            System.out.println("Created new file: " + file);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        config();

        stage.setTitle("Office Hero - Welcome!");
        MainMenuPage.setActive(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
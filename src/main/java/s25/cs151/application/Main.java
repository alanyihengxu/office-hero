package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    private void config() throws IOException{
        // Create data directory if it doesn't exist
        File dir = new File("data");
        if (dir.mkdir()) {
            System.out.println("Created new directory: " + dir);
        }

        // Create file if it doesn't exist
        File officeHourFile = new File("data/office_hours.csv");
        if (officeHourFile.createNewFile()) {
            System.out.println("Created new file: " + officeHourFile);
        }

        File courseFile = new File("data/courses.csv");
        if (courseFile.createNewFile()) {
            System.out.println("Created new file: " + courseFile);
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
package s25.cs151.application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TimeSlotPage {
    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();



        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}

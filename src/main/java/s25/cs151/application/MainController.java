package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Stage stage;

    // Changes the scene to given source
    private void changeScene(String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(source));
        Scene scene = new Scene(fxmlLoader.load(), Constants.WIDTH, Constants.HEIGHT);

        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void onOfficeHoursButtonClick(ActionEvent event) throws IOException {
        // Obtain stage reference from the node's scene window
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        // Change to the semester-office-hours scene
        changeScene("semester-office-hours.fxml");
    }

    @FXML
    protected void onNewHoursButtonClick() {

    }

    @FXML
    protected void onNewCourseButtonClick() {

    }

    @FXML
    protected void onNewAppointmentButtonClick() {

    }

    @FXML
    protected void onUpdateButtonClick() {

    }
}
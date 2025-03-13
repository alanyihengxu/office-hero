package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfficeHoursController {

    private Stage stage;

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TextField yearField;

    @FXML
    private CheckBox mondayCheckBox;

    @FXML
    private CheckBox tuesdayCheckBox;

    @FXML
    private CheckBox wednesdayCheckBox;

    @FXML
    private CheckBox thursdayCheckBox;

    @FXML
    private CheckBox fridayCheckBox;

    // Method to show an alert box
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to change the scene to a given source
    private void changeScene(Stage stage, String source) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(source));
        Scene scene = new Scene(fxmlLoader.load(), Constants.WIDTH, Constants.HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    // Method for handling "Submit" button action
    @FXML
    public void onSubmitButtonClick() {

        // Create local variables for OfficeHoursEntry
        String semester = semesterComboBox.getValue();
        String year = yearField.getText();
        List<String> selectedDays = new ArrayList<>();

        // Check if the year is entered and valid
        if (year == null || year.isEmpty()) {
            showAlert("Error", "Please enter a year.");
            return;
        }

        try {
            int yearInt = Integer.parseInt(year);
            if (year.length() != 4 || yearInt < LocalDate.now().getYear()) {
                throw new NumberFormatException(); // Invalid year length or past year
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a current or future 4-digit year.");
            return;
        }

        // Check if at least one day is selected
        if (mondayCheckBox.isSelected()) selectedDays.add("Monday");
        if (tuesdayCheckBox.isSelected()) selectedDays.add("Tuesday");
        if (wednesdayCheckBox.isSelected()) selectedDays.add("Wednesday");
        if (thursdayCheckBox.isSelected()) selectedDays.add("Thursday");
        if (fridayCheckBox.isSelected()) selectedDays.add("Friday");

        if (selectedDays.isEmpty()) {
            showAlert("Error", "Please select at least one day.");
            return;
        }

        // Create an OfficeHoursEntry object
        OfficeHoursEntry entry = new OfficeHoursEntry(year, semester, selectedDays);
        //TODO: implement data storage for the entries
    }

    // Method for handling "Back to Home" button action
    @FXML
    public void onBackToHomeButtonClick(ActionEvent event) throws IOException {
        // Obtain stage reference from the node's scene window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        // Change to the main-menu scene
        changeScene(stage, "main-menu.fxml");
    }
}

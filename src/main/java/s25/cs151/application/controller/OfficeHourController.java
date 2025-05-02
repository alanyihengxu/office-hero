package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import s25.cs151.application.model.OfficeHourEntry;
import s25.cs151.application.view.MainMenuPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static s25.cs151.application.controller.EntrySort.*;

public class OfficeHourController {

    public static void attachHandlers(Stage stage, ComboBox<String> semesterBox, TextField yearField,
                                      List<CheckBox> dayCheckboxes, Button submitButton, Button backButton) {

        submitButton.setOnAction(e -> {
            try {
                handleSubmit(stage, semesterBox, yearField, dayCheckboxes);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.setOnAction(e -> {
            MainMenuPage.setActive(stage);
        });
    }

    public static ObservableList<OfficeHourEntry> loadOfficeHours() {
        return FXCollections.observableArrayList(
                EntrySort.readOfficeHourCSV("data/office_hours.csv")
        );
    }


    private static void handleSubmit(Stage stage, ComboBox<String> semesterBox, TextField yearField, List<CheckBox> dayCheckboxes) throws IOException {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        String semester = semesterBox.getValue();
        String yearText = yearField.getText().trim();

        if (semester == null || semester.isEmpty()) {
            isValid = false;
            errorMessage.append("Please select a semester.\n");
        }

        if (yearText.length() != 4 || !yearText.matches("\\d{4}")) {
            isValid = false;
            errorMessage.append("Year must be exactly 4 digits.\n");
        }

        List<String> selectedDays = new ArrayList<>();
        for (CheckBox cb : dayCheckboxes) {
            if (cb.isSelected()) {
                selectedDays.add(cb.getText());
            }
        }

        if (selectedDays.isEmpty()) {
            isValid = false;
            errorMessage.append("Please select at least one day.\n");
        }

        if (isValid) {
            OfficeHourEntry newEntry = new OfficeHourEntry(semester, Integer.parseInt(yearText), selectedDays);
            List<OfficeHourEntry> current = readOfficeHourCSV("data/office_hours.csv");
            for (OfficeHourEntry existing : current) {
                if (newEntry.compares(existing)) {
                    showAlert("Error", "Duplicate semester and year found.");
                    return;
                }
            }
            current.add(newEntry);
            addSortedOfficeHourData(current);
            showAlert("Success", "Office hour entry successfully added.");
            MainMenuPage.setActive(stage);
        } else {
            showAlert("Error", errorMessage.toString());
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if ("Success".equals(title)) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

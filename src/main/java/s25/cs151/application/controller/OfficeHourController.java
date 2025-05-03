package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import s25.cs151.application.controller.sort.EntrySort;
import s25.cs151.application.model.entry.OfficeHourEntry;
import s25.cs151.application.controller.sort.OfficeHourEntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfficeHourController {

    private static final String OFFICEHOUR_FILE = "data/office_hours.csv";

    public static ObservableList<OfficeHourEntry> loadOfficeHours() {
        EntrySort<OfficeHourEntry> reader = new OfficeHourEntrySort();
        return FXCollections.observableArrayList(reader.readAndSort(OFFICEHOUR_FILE));
    }

    public static void attachHandlers(Stage stage, ComboBox<String> semesterBox, TextField yearField,
                                      List<CheckBox> dayCheckboxes, Button submitButton, Button backButton) {
        submitButton.setOnAction(e -> handleSubmit(stage, semesterBox, yearField, dayCheckboxes));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
    }

    private static void handleSubmit(Stage stage, ComboBox<String> semesterBox, TextField yearField,
                                     List<CheckBox> dayCheckboxes) {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        String semester = semesterBox.getValue();
        String yearText = yearField.getText().trim();

        if (semester == null || semester.isEmpty()) {
            isValid = false;
            errorMessage.append("Please select a semester.\n");
        }

        if (!yearText.matches("\\d{4}")) {
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

            try {
                List<OfficeHourEntry> officeHours = new OfficeHourEntrySort().readAndSort(OFFICEHOUR_FILE);
                for (OfficeHourEntry existing : officeHours) {
                    if (newEntry.compares(existing)) {
                        showAlert("Error", "This office hour entry already exists.");
                        return;
                    }
                }
                officeHours.add(newEntry);
                saveOfficeHours(officeHours);
                showAlert("Success", "Office hour entry successfully added.");
                MainMenuPage.setActive(stage);
            } catch (Exception ex) {
                showAlert("Error", "Failed to save office hour.");
            }
        } else {
            showAlert("Error", errorMessage.toString());
        }
    }

    private static void saveOfficeHours(List<OfficeHourEntry> officeHours) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OFFICEHOUR_FILE, false))) {
            for (OfficeHourEntry entry : officeHours) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save office hour entries.", e);
        }
    }

    private static void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        if ("Success".equals(title)) {
            alert.setAlertType(javafx.scene.control.Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

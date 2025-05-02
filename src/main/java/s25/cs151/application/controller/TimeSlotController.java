package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import s25.cs151.application.model.TimeSlotEntry;
import s25.cs151.application.view.MainMenuPage;
import s25.cs151.application.controller.EntrySort;

import java.io.IOException;
import java.util.List;

public class TimeSlotController {

    public static void attachHandlers(Stage stage, Spinner<Integer> fromHour, Spinner<Integer> fromMinute,
                                      Spinner<Integer> toHour, Spinner<Integer> toMinute,
                                      Button submitButton, Button backButton) {

        submitButton.setOnAction(e -> handleSubmit(stage, fromHour, fromMinute, toHour, toMinute));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
    }

    public static ObservableList<TimeSlotEntry> loadTimeSlots() {
        return FXCollections.observableArrayList(
                EntrySort.readTimeSlotCSV("data/semester_time_slots.csv")
        );
    }

    private static void handleSubmit(Stage stage, Spinner<Integer> fromHour, Spinner<Integer> fromMinute,
                                     Spinner<Integer> toHour, Spinner<Integer> toMinute) {

        int fh = fromHour.getValue();
        int fm = fromMinute.getValue();
        int th = toHour.getValue();
        int tm = toMinute.getValue();

        if (th < fh || (th == fh && tm <= fm)) {
            showAlert("Error", "End time must be after start time.");
            return;
        }

        TimeSlotEntry newEntry = new TimeSlotEntry(fh, fm, th, tm);

        List<TimeSlotEntry> current = EntrySort.readTimeSlotCSV("data/semester_time_slots.csv");
        current.add(newEntry);
        EntrySort.addSortedTimeSlotData(current);
        showAlert("Success", "Time slot successfully added.");
        MainMenuPage.setActive(stage);
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

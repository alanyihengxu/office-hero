package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import s25.cs151.application.model.sort.EntrySort;
import s25.cs151.application.model.entry.TimeSlotEntry;
import s25.cs151.application.model.sort.TimeSlotEntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TimeSlotController {

    private static final String TIMESLOT_FILE = "data/semester_time_slots.csv";

    public static ObservableList<TimeSlotEntry> loadTimeSlots() {
        EntrySort<TimeSlotEntry> reader = new TimeSlotEntrySort();
        return FXCollections.observableArrayList(reader.readAndSort(TIMESLOT_FILE));
    }

    public static void attachHandlers(Stage stage, Spinner<Integer> fromHour, Spinner<Integer> fromMinute,
                                      Spinner<Integer> toHour, Spinner<Integer> toMinute,
                                      Button submitButton, Button backButton) {

        submitButton.setOnAction(e -> handleSubmit(stage, fromHour, fromMinute, toHour, toMinute));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
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

        try {
            List<TimeSlotEntry> slots = new TimeSlotEntrySort().readAndSort(TIMESLOT_FILE);
            slots.add(newEntry);
            saveTimeSlots(slots);
            showAlert("Success", "Time slot successfully added.");
            MainMenuPage.setActive(stage);
        } catch (Exception ex) {
            showAlert("Error", "Failed to save time slot.");
        }
    }

    private static void saveTimeSlots(List<TimeSlotEntry> slots) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TIMESLOT_FILE, false))) {
            for (TimeSlotEntry entry : slots) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save time slots.", e);
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

package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.sort.EntrySort;
import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.model.sort.AppointmentEntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SearchController {

    private static final String APPOINTMENT_FILE = "data/appointments.csv";

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if ("Success".equals(title))
            alert.setAlertType(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void attachHandlers(TextField name, TableView<AppointmentEntry> tableView,
                                      Button searchButton, Button backButton, Button deleteButton, Stage stage) {
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
        searchButton.setOnAction(e -> handleSearch(name, tableView));
        deleteButton.setOnAction(e -> handleDelete(tableView));
    }

    private static List<AppointmentEntry> loadAppointments() {
        EntrySort<AppointmentEntry> reader = new AppointmentEntrySort();
        return reader.readAndSort(APPOINTMENT_FILE);
    }

    private static void handleSearch(TextField name, TableView<AppointmentEntry> tableView) {
        String searchName = name.getText().trim().toLowerCase();
        if (searchName.isEmpty()) {
            showAlert("Error", "Please enter a student's name.");
            return;
        }

        ObservableList<AppointmentEntry> appointments = FXCollections.observableArrayList(loadAppointments());

        ObservableList<AppointmentEntry> filtered = appointments.filtered(
                appt -> appt.getName().toLowerCase().contains(searchName)
        );

        if (filtered.isEmpty()) {
            showAlert("Info", "No appointments found for that student.");
            return;
        }

        SortedList<AppointmentEntry> sortedFiltered = new SortedList<>(filtered);
        sortedFiltered.setComparator((appt1, appt2) -> {
            int dateCompare = appt2.getDate().compareTo(appt1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return appt2.getTimeSlot().compareTo(appt1.getTimeSlot());
        });
        tableView.setItems(sortedFiltered);
    }

    private static void handleDelete(TableView<AppointmentEntry> tableView) {
        AppointmentEntry deleteItem = tableView.getSelectionModel().getSelectedItem();
        if (deleteItem == null) {
            showAlert("Error", "Please select an appointment.");
            return;
        }

        try {
            List<AppointmentEntry> appointments = loadAppointments();
            appointments.removeIf(appt -> appt.equals(deleteItem));
            saveAppointments(appointments);

            ObservableList<AppointmentEntry> filtered = FXCollections.observableArrayList(appointments);
            tableView.setItems(filtered);

            showAlert("Success", "Appointment successfully deleted.");
        } catch (Exception e) {
            showAlert("Error", "Failed to delete appointment.");
        }
    }

    private static void saveAppointments(List<AppointmentEntry> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENT_FILE, false))) {
            for (AppointmentEntry entry : appointments) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save appointment entries.", e);
        }
    }
}

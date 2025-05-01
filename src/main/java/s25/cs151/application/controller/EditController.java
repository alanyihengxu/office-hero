package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import s25.cs151.application.model.AppointmentEntry;

public class EditController {
    /**
     * This method is a helper method for displaying alerts; the default alert
     * type is error.
     *
     * @param: String title, String message
     * @return: Void
     *
     */

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (title.equals("Success"))
            alert.setAlertType(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void search(TextField name, TableView<AppointmentEntry> tableView) {
        String searchName = name.getText().trim().toLowerCase();

        // Get all appointments
        ObservableList<AppointmentEntry> appointments = FXCollections.observableArrayList(
                EntrySort.readAppointmentCSV("data/appointments.csv")
        );

        // Filter by student name
        ObservableList<AppointmentEntry> filtered = appointments.filtered(
                appt -> appt.getName().toLowerCase().contains(searchName)
        );

        // Alert message
        if (filtered.isEmpty()) {
            showAlert("Info", "No appointments found for that student.");
            return;
        }

        //Sort
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
}

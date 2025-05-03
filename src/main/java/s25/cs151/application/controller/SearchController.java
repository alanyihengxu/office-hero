package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.view.MainMenuPage;

public class SearchController {
    /**
     * This method is a helper method for displaying alerts; the default alert
     * type is error.
     *
     * @param: String title, String message
     * @return: Void
     *
     */
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (title.equals("Success"))
            alert.setAlertType(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void attachhandlers(TextField name, TableView<AppointmentEntry> tableView, Button searchButton, Button backButton, Button deleteButton, Stage stage){
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
        searchButton.setOnAction(e-> handleSearch(name, tableView));
        deleteButton.setOnAction(e-> handleDelete(tableView));

    }

    private static void handleSearch(TextField name, TableView<AppointmentEntry> tableView){
        String searchName = name.getText().trim().toLowerCase();
        if (searchName.isEmpty()) {
            showAlert("Error", "Please enter a student's name.");
            return;
        }

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

    private static void handleDelete(TableView<AppointmentEntry> tableView){
        AppointmentEntry deleteItem = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (deleteItem == null) {
            showAlert("Error", "Please select an appointment.");
            return;
        } else {
            DeleteSchedules delete1 = new DeleteSchedules();
            delete1.deleteSearch("data/appointments.csv",deleteItem);
            ObservableList<AppointmentEntry> filtered = tableView.getItems().filtered(
                    appt -> appt != deleteItem
            );
            tableView.setItems(filtered);
        }
    }
}

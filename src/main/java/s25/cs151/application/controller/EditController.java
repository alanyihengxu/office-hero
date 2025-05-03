package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.model.entry.CourseEntry;
import s25.cs151.application.model.entry.OfficeHourEntry;
import s25.cs151.application.model.entry.TimeSlotEntry;
import s25.cs151.application.view.EditAppointmentPage;
import s25.cs151.application.view.EditSearchPage;
import s25.cs151.application.view.MainMenuPage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EditController {
    // The appointment that is being edited
    private static AppointmentEntry editItem;

    // Getter for edit item
    public static AppointmentEntry getEditItem() {
        return editItem;
    }

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

    public static List<String> loadOfficeHours() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/office_hours.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                OfficeHourEntry entry = OfficeHourEntry.fromCSV(line);
                entries.add(entry.getSemester() + " " + entry.getYear() + ": " + entry.getDays());
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load data.");
        }
        return entries;
    }

    public static List<String> loadCourses() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CourseEntry entry = CourseEntry.fromCSV(line);
                entries.add(entry.getCourseCode() + "-" + entry.getSectionNumber());
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load data.");
        }
        return entries;
    }

    public static List<String> loadTimeSlots() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/semester_time_slots.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TimeSlotEntry entry = TimeSlotEntry.fromCSV(line);
                String fromMinute = (entry.getFromMinute() < 10 ? "0" : "") + entry.getFromMinute();
                String toMinute = (entry.getToMinute() < 10 ? "0" : "") + entry.getToMinute();
                entries.add(entry.getFromHour() + ":" + fromMinute + " - " + entry.getToHour() + ":" + toMinute);
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load data.");
        }
        return entries;
    }

    // Return to Main Menu
    public static void mainMenu(Stage stage) {
        MainMenuPage.setActive(stage);  // Switch to NewScene
    }

    //Sort initial appointments
    public static void initialTableView(TableView<AppointmentEntry> tableView) {
        SortedList<AppointmentEntry> initialAppointments = new SortedList<>(
                FXCollections.observableArrayList(EntrySort.readAppointmentCSV("data/appointments.csv"))
        );
        initialAppointments.setComparator((appt1, appt2) -> {
            int dateCompare = appt2.getDate().compareTo(appt1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return appt2.getTimeSlot().compareTo(appt1.getTimeSlot());
        });
        tableView.setItems(initialAppointments);
    }

    // Search based on name and display in tableView
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

    // Handles edit button and redirect to Edit Appointment
    public static void edit(Stage stage, TableView<AppointmentEntry> tableView) {
        editItem = tableView.getSelectionModel().getSelectedItem();
        if (editItem == null) {
            EditController.showAlert("Error", "Please select an appointment.");
        } else {
            try {
                EditAppointmentPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    //Submit button in action + checks for valid inputs
    public static void submit(TextField name, DatePicker date, ComboBox<String> timeSlot, ComboBox<String> course, TextField reason, TextField comment, Stage stage) {
        boolean isValid = true;
        String errorMessage = "";

        String studentName = name.getText().trim();
        if (studentName.isEmpty()) {
            isValid = false;
            errorMessage += "Student name is required.\n";
        }

        String appointmentDate = null;
        if (date.getValue() == null) {
            isValid = false;
            errorMessage += "Date is required.\n";
        } else {
            appointmentDate = date.getValue().toString();
        }

        String selectedTimeSlot = timeSlot.getValue();
        if (selectedTimeSlot == null) {
            isValid = false;
            errorMessage += "Time slot is required.\n";
        }

        String selectedCourse = course.getValue();
        if (selectedCourse == null) {
            isValid = false;
            errorMessage += "Course is required.\n";
        }

        String appointmentReason = reason.getText().trim().isEmpty() ? "N/A" : reason.getText().trim();
        String appointmentComment = comment.getText().trim().isEmpty() ? "N/A" : comment.getText().trim();

        if (isValid) {
            AppointmentEntry newEntry = new AppointmentEntry(
                    studentName,
                    appointmentDate,
                    selectedTimeSlot,
                    selectedCourse,
                    appointmentReason,
                    appointmentComment
            );

            try {
                DeleteSchedules delete = new DeleteSchedules();
                delete.deleteSearch("data/appointments.csv",editItem);

                // Load, append, and sort appointments
                List<AppointmentEntry> current = EntrySort.readAppointmentCSV("data/appointments.csv");
                current.add(newEntry);
                EntrySort.addSortedAppointmentData(current);

                showAlert("Success", "Appointment successfully changed.");
                EditSearchPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                showAlert("Error", "Failed to save appointment.");
            }
        } else {
            showAlert("Error", errorMessage);
        }
    }
}

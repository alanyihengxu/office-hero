package s25.cs151.application.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.model.entry.CourseEntry;
import s25.cs151.application.model.entry.TimeSlotEntry;
import s25.cs151.application.controller.sort.AppointmentEntrySort;
import s25.cs151.application.controller.sort.EntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class AppointmentController {

    private static final String COURSE_FILE = "data/courses.csv";
    private static final String TIMESLOT_FILE = "data/semester_time_slots.csv";
    private static final String APPOINTMENT_FILE = "data/appointments.csv";

    public static List<String> loadCourses() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CourseEntry entry = CourseEntry.fromCSV(line);
                entries.add(entry.getCourseCode() + "-" + entry.getSectionNumber());
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load courses.");
        }
        return entries;
    }

    public static List<String> loadTimeSlots() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TIMESLOT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TimeSlotEntry entry = TimeSlotEntry.fromCSV(line);
                String fromMinute = (entry.getFromMinute() < 10 ? "0" : "") + entry.getFromMinute();
                String toMinute = (entry.getToMinute() < 10 ? "0" : "") + entry.getToMinute();
                entries.add(entry.getFromHour() + ":" + fromMinute + " - " + entry.getToHour() + ":" + toMinute);
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load timeslots.");
        }
        return entries;
    }

    private static List<AppointmentEntry> loadAppointments() {
        EntrySort<AppointmentEntry> reader = new AppointmentEntrySort();
        return reader.readAndSort(APPOINTMENT_FILE);
    }

    public static void attachHandlers(TextField name, DatePicker date, ComboBox<String> timeSlot,
                                      ComboBox<String> course, TextField reason, TextField comment,
                                      Button submitButton, Button backButton, Stage stage) {

        submitButton.setOnAction(e -> handleSubmit(name, date, timeSlot, course, reason, comment, stage));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
    }

    private static void handleSubmit(TextField name, DatePicker date, ComboBox<String> timeSlot,
                                     ComboBox<String> course, TextField reason, TextField comment, Stage stage) {

        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        String studentName = name.getText().trim();
        if (studentName.isEmpty()) {
            isValid = false;
            errorMessage.append("Student name is required.\n");
        }

        String appointmentDate = null;
        if (date.getValue() == null) {
            isValid = false;
            errorMessage.append("Date is required.\n");
        } else {
            appointmentDate = date.getValue().toString();
        }

        String selectedTimeSlot = timeSlot.getValue();
        if (selectedTimeSlot == null) {
            isValid = false;
            errorMessage.append("Time slot is required.\n");
        }

        String selectedCourse = course.getValue();
        if (selectedCourse == null) {
            isValid = false;
            errorMessage.append("Course is required.\n");
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
                List<AppointmentEntry> appointments = loadAppointments();
                appointments.add(newEntry);
                saveAppointments(appointments);

                showAlert("Success", "Appointment successfully submitted.");
                MainMenuPage.setActive(stage);
            } catch (Exception e) {
                showAlert("Error", "Failed to save appointment.");
            }
        } else {
            showAlert("Error", errorMessage.toString());
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

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if ("Success".equals(title))
            alert.setAlertType(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

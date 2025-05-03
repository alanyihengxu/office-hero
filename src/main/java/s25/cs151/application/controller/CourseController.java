package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.CourseEntry;
import s25.cs151.application.controller.sort.CourseEntrySort;
import s25.cs151.application.controller.sort.EntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.*;
import java.util.List;

public class CourseController {

    private static final String COURSE_FILE = "data/courses.csv";

    public static ObservableList<CourseEntry> loadCourses() {
        EntrySort<CourseEntry> reader = new CourseEntrySort();
        return FXCollections.observableArrayList(reader.readAndSort(COURSE_FILE));
    }

    public static void attachHandlers(Stage stage, TextField courseCode, TextField courseName, TextField sectionNumber,
                                      Button submitButton, Button backButton) {
        submitButton.setOnAction(e -> handleSubmit(stage, courseCode, courseName, sectionNumber));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
    }

    private static void handleSubmit(Stage stage, TextField courseCode, TextField courseName, TextField sectionNumber) {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        if (courseCode.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("Please enter course code.\n");
        }
        if (courseName.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("Please enter course name.\n");
        }
        if (sectionNumber.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("Please enter section number.\n");
        }

        if (isValid) {
            CourseEntry newEntry = new CourseEntry(
                    courseCode.getText().trim(),
                    courseName.getText().trim(),
                    sectionNumber.getText().trim()
            );

            try {
                List<CourseEntry> courses = new CourseEntrySort().readAndSort(COURSE_FILE);
                for (CourseEntry existing : courses) {
                    if (newEntry.compares(existing)) {
                        showAlert("Error", "This course entry already exists.");
                        return;
                    }
                }
                courses.add(newEntry);
                saveCourses(courses);
                showAlert("Success", "Course successfully added.");
                MainMenuPage.setActive(stage);
            } catch (Exception ex) {
                showAlert("Error", "Failed to save course.");
            }
        } else {
            showAlert("Error", errorMessage.toString());
        }
    }

    private static void saveCourses(List<CourseEntry> courses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE, false))) {
            for (CourseEntry entry : courses) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save course entries.", e);
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

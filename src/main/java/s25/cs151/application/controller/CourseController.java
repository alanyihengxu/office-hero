package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.CourseEntry;
import s25.cs151.application.controller.EntrySort;
import s25.cs151.application.view.MainMenuPage;

import java.io.IOException;
import java.util.List;

public class CourseController {

    public static void attachHandlers(Stage stage, TextField courseCode, TextField courseName, TextField sectionNumber,
                                      Button submitButton, Button backButton) {

        submitButton.setOnAction(e -> handleSubmit(stage, courseCode, courseName, sectionNumber));
        backButton.setOnAction(e -> MainMenuPage.setActive(stage));
    }

    public static ObservableList<CourseEntry> loadCourses() {
        return FXCollections.observableArrayList(
                EntrySort.readCourseCSV("data/courses.csv")
        );
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
                if (isDuplicate(newEntry)) {
                    showAlert("Error", "This course entry already exists.");
                } else {
                    saveCourse(newEntry);
                    showAlert("Success", "Course successfully added.");
                    MainMenuPage.setActive(stage);
                }
            } catch (IOException ex) {
                showAlert("Error", "Failed to save data.");
            }
        } else {
            showAlert("Error", errorMessage.toString());
        }
    }

    private static boolean isDuplicate(CourseEntry newEntry) throws IOException {
        List<CourseEntry> courses = EntrySort.readCourseCSV("data/courses.csv");
        for (CourseEntry existing : courses) {
            if (newEntry.compares(existing)) {
                return true;
            }
        }
        return false;
    }

    private static void saveCourse(CourseEntry entry) throws IOException {
        List<CourseEntry> courses = EntrySort.readCourseCSV("data/courses.csv");
        courses.add(entry);
        EntrySort.addSortedCourseData(courses);
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

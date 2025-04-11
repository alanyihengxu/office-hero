package s25.cs151.application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class AppointmentPage {
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

    /**
     * This method saves a user's office hour entry into a flat file.
     *
     * @param: OfficeHourEntry entry
     * @return: Void
     */

    private static void saveToFile(OfficeHourEntry entry) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/appointments.csv",true))) {
            writer.write(entry.toString());
            writer.newLine(); // Add a new line after each entry
        }
    }


    private static List<String> loadOfficeHours() {
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

    private static List<String> loadCourses() {
        List<String> entries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CourseEntry entry = CourseEntry.fromCSV(line);
                entries.add(entry.getCourseCode() + " Sec " + entry.getSectionNumber() + " - " + entry.getCourseName());
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load data.");
        }
        return entries;
    }

    private static List<String>loadTimeSlots() {
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

    /**
     * This method makes it so a stage that becomes active.
     * This stage houses the office hour page which allows users to
     * 1.Select Semester
     * 2.Enter current year
     * 3.Select days for office hours
     * @param: Stage (stage object)
     * @return: Void
     *
     */

    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();

        //Student name
        TextField name = new TextField();
        name.setPromptText("Enter student's full name");
        name.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        name.setFocusTraversable(false);
        name.setLayoutX(250);
        name.setLayoutY(150);
        root.getChildren().add(name);

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());
        date.setLayoutX(250);
        date.setLayoutY(200);
        root.getChildren().add(date);

        //Time slot selection
        ComboBox<String> timeSlot = new ComboBox<>();
        timeSlot.getItems().addAll(loadTimeSlots());
        timeSlot.setValue(timeSlot.getItems().getFirst());
        timeSlot.setLayoutX(250);
        timeSlot.setLayoutY(250);
        root.getChildren().add(timeSlot);

        //Time slot selection
        ComboBox<String> course = new ComboBox<>();
        course.getItems().addAll(loadCourses());
        course.setValue(course.getItems().getFirst());
        course.setLayoutX(250);
        course.setLayoutY(300);
        root.getChildren().add(course);

        //Reason
        TextField reason = new TextField();
        reason.setPromptText("Reason (optional)");
        reason.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        reason.setFocusTraversable(false);
        reason.setLayoutX(250);
        reason.setLayoutY(350);
        root.getChildren().add(reason);

        //Comment
        TextField comment = new TextField();
        comment.setPromptText("Comment (optional)");
        comment.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        comment.setFocusTraversable(false);
        comment.setLayoutX(250);
        comment.setLayoutY(400);
        root.getChildren().add(comment);

        //Submit button
        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(570);
        root.getChildren().add(submitButton);

        //Back to home page button
        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        root.getChildren().add(backButton);

        //Submit button in action + checks for valid inputs
        submitButton.setOnAction(e->{

        });

        backButton.setOnAction(e -> {
            try {
                MainMenuPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}
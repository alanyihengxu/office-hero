package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

public class CoursePage {
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
     * This method saves a user's course entry into a flat file.
     *
     * @param: CourseEntry entry
     * @return: Void
     */

    private static void saveToFile(CourseEntry entry) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/courses.csv",true))) {
            writer.write(entry.toString());
            writer.newLine(); // Add a new line after each entry
        }
    }

    /**
     * This method checks if an entry is a duplicate of
     * one already submitted (Course code, course name, and section number).
     *
     * @param: CourseEntry newEntry
     * @return: boolean isDuplicate
     */

    private static boolean isDuplicate(CourseEntry newEntry) throws IOException {
        // Read the CSV file to check for duplicates
        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CourseEntry existingEntry = CourseEntry.fromCSV(line);
                if (newEntry.compares(existingEntry)) {
                    reader.close();
                    return true;  // Duplicate found
                }
            }
        }
        return false;

    }

    private static ObservableList<CourseEntry> loadFile() {
        ObservableList<CourseEntry> entries = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CourseEntry entry = CourseEntry.fromCSV(line);
                entries.add(entry);
            }
        } catch(IOException ex) {
            showAlert("Error", "Failed to load data.");
        }
        return entries;
    }

    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();

        TableView<CourseEntry> tableView = new TableView<>();
        TableColumn<CourseEntry, String> courseCodeColumn = new TableColumn<>("Course Code");
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));

        TableColumn<CourseEntry, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<CourseEntry, String> sectionNumberColumn = new TableColumn<>("Section Number");
        sectionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));

        tableView.getColumns().add(courseCodeColumn);
        tableView.getColumns().add(courseNameColumn);
        tableView.getColumns().add(sectionNumberColumn);
        ObservableList<CourseEntry> courseEntries = loadFile();
        tableView.setItems(courseEntries);
        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(500, 250);
        root.getChildren().add(tableView);

        TextField courseCode = new TextField();
        courseCode.setLayoutY(130);
        root.getChildren().add(courseCode);

        TextField courseName = new TextField();
        courseName.setLayoutY(280);
        root.getChildren().add(courseName);

        TextField sectionNumber = new TextField();
        sectionNumber.setLayoutY(430);
        root.getChildren().add(sectionNumber);

        Button submitButton = new Button("Submit");
        submitButton.setLayoutY(570);
        root.getChildren().add(submitButton);

        submitButton.setOnAction(_->{
            boolean isValid = true;
            String errorMessage = "";

            if (courseCode.getText().isEmpty()) {
                isValid = false;
                errorMessage += "Please enter the course code.\n";
            }

            if (courseName.getText().isEmpty()) {
                isValid = false;
                errorMessage += "Please enter the course name.\n";
            }

            if (sectionNumber.getText().isEmpty()) {
                isValid = false;
                errorMessage += "Please enter the section number.\n";
            }

            if (isValid) {
                // Create CourseEntry object
                CourseEntry newEntry = new CourseEntry(courseCode.getText(), courseName.getText(), sectionNumber.getText());

                try {
                    // Check for duplicates
                    if (isDuplicate(newEntry)) {
                        showAlert("Error", "This course entry already exists.");
                    } else {
                        saveToFile(newEntry);  // Save entry
                        EntrySort.addSortedCourseData(EntrySort.readCourseCSV("data/courses.csv"));
                        showAlert("Success", "Course entry successfully added.");
                        MainMenuPage.setActive(stage);  // Switch to NewScene
                    }
                } catch (IOException ex) {
                    showAlert("Error", "Failed to save data.");
                }
            } else {
                showAlert("Error", errorMessage);
            }
        });

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}

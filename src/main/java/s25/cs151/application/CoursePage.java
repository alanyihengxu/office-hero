package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
        StackPane stackPane = new StackPane();
        StackPane stackPane2 = new StackPane();
        StackPane stackPane3 = new StackPane();

        //Background color
        Rectangle backgroundColor = new Rectangle(1000, 600);
        backgroundColor.setFill(Color.KHAKI);
        root.getChildren().add(backgroundColor);

        Rectangle blueRectangle = new Rectangle(220, 600);
        blueRectangle.setFill(Color.DARKKHAKI);

        blueRectangle.setLayoutX(0);
        blueRectangle.setLayoutY(0);

        // Add the blue rectangle to the root
        root.getChildren().add(blueRectangle);

        // Main title
        Text title = new Text("Course Information");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(260);
        title.setLayoutY(60);
        root.getChildren().add(title);


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

        Rectangle codeBox = new Rectangle(190, 50);
        codeBox.setFill(Color.ALICEBLUE);

        Text codeLabel = new Text("Course Code:");
        codeLabel.setStyle("-fx-font-size: 16px;");
        stackPane.getChildren().addAll(codeBox, codeLabel);
        stackPane.setLayoutX(15);
        stackPane.setLayoutY(120);
        root.getChildren().add(stackPane);

        TextField courseCode = new TextField();
        courseCode.setPromptText("Enter the course code");
        courseCode.setLayoutX(250);
        courseCode.setLayoutY(130);
        root.getChildren().add(courseCode);

        Rectangle nameBox = new Rectangle(190, 50);
        nameBox.setFill(Color.ALICEBLUE);

        Text nameLabel = new Text("Course Name:");
        nameLabel.setStyle("-fx-font-size: 16px;");
        stackPane2.getChildren().addAll(nameBox, nameLabel);
        stackPane2.setLayoutX(15);
        stackPane2.setLayoutY(270);
        root.getChildren().add(stackPane2);

        TextField courseName = new TextField();
        courseName.setPromptText("Enter the course name");
        courseName.setLayoutX(250);
        courseName.setLayoutY(280);
        root.getChildren().add(courseName);

        Rectangle sectionNumBox = new Rectangle(190, 50);
        sectionNumBox.setFill(Color.ALICEBLUE);

        Text sectionNumLabel = new Text("Section Number:");
        sectionNumLabel.setStyle("-fx-font-size: 16px;");
        stackPane3.getChildren().addAll(sectionNumBox, sectionNumLabel);
        stackPane3.setLayoutX(15);
        stackPane3.setLayoutY(400);
        root.getChildren().add(stackPane3);

        TextField sectionNumber = new TextField();
        sectionNumber.setPromptText("Enter the section number");
        sectionNumber.setLayoutX(250);
        sectionNumber.setLayoutY(415);
        root.getChildren().add(sectionNumber);

        //Image
        Image logo = new Image("logo.png");
        ImageView imageView1 = new ImageView(logo);
        imageView1.setLayoutX(700);
        imageView1.setLayoutY(380);
        imageView1.setFitWidth(200);
        imageView1.setFitHeight(200);

        Text logoText = new Text("Office Hero");
        logoText.setStyle("-fx-font-size: 36px;-fx-font-style: italic;");
        logoText.setLayoutX(730);
        logoText.setLayoutY(370);
        root.getChildren().add(logoText);
        root.getChildren().add(imageView1);

        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(570);
        root.getChildren().add(submitButton);

        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        root.getChildren().add(backButton);

        submitButton.setOnAction(e->{
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

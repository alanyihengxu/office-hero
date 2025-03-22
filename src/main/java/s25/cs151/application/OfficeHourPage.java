package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.io.*;


public class OfficeHourPage {
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

        File file = new File("data/office_hours.csv");
        // Append the new entry to the CSV file
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(entry.toString());
            writer.newLine(); // Add a new line after each entry
        }
    }

    /**
     * This method checks if an entry is a duplicate of
     * one already submitted (Semester and year).
     *
     * @param: OfficeHourEntry newEntry
     * @return: boolean isDuplicate
     */

    private static boolean isDuplicate(OfficeHourEntry newEntry) throws IOException {
        // Read the CSV file to check for duplicates
        try (BufferedReader reader = new BufferedReader(new FileReader("data/office_hours.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                OfficeHourEntry existingEntry = OfficeHourEntry.fromCSV(line);
                if (newEntry.compares(existingEntry)) {
                    reader.close();
                    return true;  // Duplicate found
                }
            }
        }
        return false;

    }
    private static ObservableList<OfficeHourEntry> loadOfficeHours() throws IOException {
        ObservableList<OfficeHourEntry> entries = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/office_hours.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Semester")) continue; //skips header line
                OfficeHourEntry entry = OfficeHourEntry.fromCSV(line);
                entries.add(entry);
            }
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
        Text title = new Text("Semester Office Hours");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(250);
        title.setLayoutY(60);
        root.getChildren().add(title);

        //Display in TableView
        TableView<OfficeHourEntry> tableView = new TableView<>();

        TableColumn<OfficeHourEntry, String> semesterColumn = new TableColumn<>("Semester");
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<OfficeHourEntry, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<OfficeHourEntry, List<String>> daysColumn = new TableColumn<>("Days");
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));

        daysColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(List<String> days, boolean empty) {
                super.updateItem(days, empty);
                if (empty || days == null) {
                    setText(null);
                } else {
                    setText(String.join(", ", days));
                }
            }
        });

        tableView.getColumns().addAll(semesterColumn, yearColumn, daysColumn);
        ObservableList<OfficeHourEntry> officeHourEntries = loadOfficeHours();
        tableView.setItems(officeHourEntries);
        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(500, 250);
        root.getChildren().add(tableView);


        Rectangle semesterBox = new Rectangle(190, 50);
        semesterBox.setFill(Color.ALICEBLUE);

        Text semesterLabel = new Text("Select the current semester:");
        semesterLabel.setStyle("-fx-font-size: 16px;");
        stackPane.getChildren().addAll(semesterBox, semesterLabel);
        stackPane.setLayoutX(15);
        stackPane.setLayoutY(120);

        //Semester selection
        ComboBox<String> semester = new ComboBox<>();
        semester.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semester.setValue("Spring");
        semester.setLayoutX(250);
        semester.setLayoutY(130);

        Rectangle yearBox = new Rectangle(190, 50);
        yearBox.setFill(Color.ALICEBLUE);

        Text yearLabel = new Text("Enter the current year:");
        yearLabel.setStyle("-fx-font-size: 16px;");
        stackPane2.getChildren().addAll(yearBox, yearLabel);
        stackPane2.setLayoutX(15);
        stackPane2.setLayoutY(270);

        //Year info
        TextField year = new TextField();
        year.setPromptText("Enter the year");
        year.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        year.setFocusTraversable(false);
        year.setLayoutX(250);
        year.setLayoutY(280);

        Rectangle dayBox = new Rectangle(190, 50);
        dayBox.setFill(Color.ALICEBLUE);

        Text dayLabel = new Text("Select the days available:");
        dayLabel.setStyle("-fx-font-size: 16px;");
        stackPane3.getChildren().addAll(dayBox, dayLabel);
        stackPane3.setLayoutX(15);
        stackPane3.setLayoutY(400);

        //Day selection
        CheckBox monday = new CheckBox("Monday");
        monday.setLayoutX(250);
        monday.setLayoutY(400);

        CheckBox tuesday = new CheckBox("Tuesday");
        tuesday.setLayoutX(250);
        tuesday.setLayoutY(430);

        CheckBox wednesday = new CheckBox("Wednesday");
        wednesday.setLayoutX(250);
        wednesday.setLayoutY(460);

        CheckBox thursday = new CheckBox("Thursday");
        thursday.setLayoutX(250);
        thursday.setLayoutY(490);

        CheckBox friday = new CheckBox("Friday");
        friday.setLayoutX(250);
        friday.setLayoutY(520);

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

        //Submit button
        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(570);

        //Back to home page button
        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);

        //adding all the visual elements to the pane
        root.getChildren().add(stackPane);
        root.getChildren().add(stackPane2);
        root.getChildren().add(stackPane3);
        root.getChildren().add(year);
        root.getChildren().add(semester);
        root.getChildren().add(monday);
        root.getChildren().add(tuesday);
        root.getChildren().add(wednesday);
        root.getChildren().add(thursday);
        root.getChildren().add(friday);
        root.getChildren().add(imageView1);
        root.getChildren().add(logoText);
        root.getChildren().add(submitButton);
        root.getChildren().add(backButton);

        //Submit button in action + checks for valid inputs
        submitButton.setOnAction(e->{
            boolean isValid = true;
            String errorMessage = "";

            if (semester.getValue() == null) {
                isValid = false;
                errorMessage += "Please select a semester.\n";
            }

            if (!(monday.isSelected() || tuesday.isSelected() ||
                    wednesday.isSelected() || thursday.isSelected() ||
                    friday.isSelected())) {
                isValid = false;
                errorMessage += "Please select at least one day.\n";
            }

            String yearInput = year.getText();
            boolean allDigits = true;
            for (int i = 0; i < yearInput.length(); i++) {
                if (!Character.isDigit(yearInput.charAt(i))) {
                    allDigits = false;
                    break;
                }
            }

            if (yearInput.length() != 4 || !allDigits) {
                isValid = false;
                errorMessage += "Please enter a valid year input of 4 digits.\n";
            } else {

                int years = Integer.parseInt(yearInput);
                int currentYear = LocalDate.now().getYear();
                if (years < currentYear) {
                    isValid = false;
                    errorMessage += "Please enter a current or future 4-digit year.\n";
                }
            }

            if (isValid) {
                // Create OfficeHourEntry object
                List<String> selectedDays = new ArrayList<>();
                if (monday.isSelected()) selectedDays.add("Monday");
                if (tuesday.isSelected()) selectedDays.add("Tuesday");
                if (wednesday.isSelected()) selectedDays.add("Wednesday");
                if (thursday.isSelected()) selectedDays.add("Thursday");
                if (friday.isSelected()) selectedDays.add("Friday");

                OfficeHourEntry newEntry = new OfficeHourEntry(semester.getValue(), Integer.parseInt(yearInput), selectedDays);

                try {
                    // Check for duplicates
                    if (isDuplicate(newEntry)) {
                        showAlert("Error", "This office hour entry already exists.");
                    } else {
                        saveToFile(newEntry);  // Save entry
                        EntrySort.addSortedData(EntrySort.readCSV("data/office_hours.csv"));
                        showAlert("Success", "Office hour entry successfully added.");
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
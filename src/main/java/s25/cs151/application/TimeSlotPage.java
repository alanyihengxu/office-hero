package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.time.LocalTime;


public class TimeSlotPage {
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
     * This method saves a user's time slot entry into a flat file.
     *
     * @param: TimeSlotEntry entry
     * @return: Void
     */
    private static void saveTimeSlotToFile(TimeSlotEntry entry) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/semester_time_slots.csv", true))) {
            writer.write(entry.toString());
            writer.newLine();
        }
    }

    /**
     * This method makes it so a stage that becomes active.
     * This stage allows user to pick a selected start time
     * and end time for their office hours then displays it on the table
     *
     * @param: Stage (stage object)
     * @return: Void
     *
     */

    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();
        StackPane stackPane = new StackPane();
        StackPane stackPane2 = new StackPane();
        //StackPane stackPane3 = new StackPane();

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
        Text title = new Text("Semester Time Slots");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(250);
        title.setLayoutY(60);
        root.getChildren().add(title);

        //Display in TableView
        TableView<TimeSlotEntry> tableView = new TableView<>();

        TableColumn<TimeSlotEntry, String> fromTimeColumn = new TableColumn<>("From Time:");
        fromTimeColumn.setCellValueFactory(cellData -> {
            TimeSlotEntry entry = cellData.getValue();
            String formatted = String.format("%02d:%02d", entry.getFromHour(), entry.getFromMinute());
            return new ReadOnlyStringWrapper(formatted);
        });

        TableColumn<TimeSlotEntry, String> toTimeColumn = new TableColumn<>("To Time:");
        toTimeColumn.setCellValueFactory(cellData -> {
            TimeSlotEntry entry = cellData.getValue();
            String formatted = String.format("%02d:%02d", entry.getToHour(), entry.getToMinute());
            return new ReadOnlyStringWrapper(formatted);
        });

        tableView.getColumns().add(fromTimeColumn);
        tableView.getColumns().add(toTimeColumn);

        ObservableList<TimeSlotEntry> timeSlotEntries =
                FXCollections.observableArrayList(EntrySort.readTimeSlotCSV("data/semester_time_slots.csv"));
        tableView.setItems(timeSlotEntries);

        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(500, 250);
        root.getChildren().add(tableView);


        Rectangle semesterBox = new Rectangle(190, 50);
        semesterBox.setFill(Color.ALICEBLUE);

        Text semesterLabel = new Text("From Hour:");
        semesterLabel.setStyle("-fx-font-size: 16px;");
        stackPane.getChildren().addAll(semesterBox, semesterLabel);
        stackPane.setLayoutX(15);
        stackPane.setLayoutY(120);

        //Semester selection
        HBox timeSelect1 = new HBox();

        Spinner<Integer> hourSelect1 = new Spinner<>(0, 23, LocalTime.now().getHour());
        hourSelect1.setPrefWidth(120);
        hourSelect1.setEditable(false);

        Spinner<Integer> minuteSelect1 = new Spinner<>(0, 59, LocalTime.now().getMinute());
        minuteSelect1.setEditable(false);
        minuteSelect1.setPrefWidth(120);

        //Adding to Hbox
        timeSelect1.getChildren().addAll(hourSelect1,minuteSelect1);
        timeSelect1.setLayoutX(250);
        timeSelect1.setLayoutY(130);


        HBox timeSelect2 = new HBox();

        Spinner<Integer> hourSelect2 = new Spinner<>(0, 23, LocalTime.now().getHour());
        hourSelect2.setPrefWidth(120);
        hourSelect2.setEditable(false);

        Spinner<Integer> minuteSelect2 = new Spinner<>(0, 59, LocalTime.now().getMinute());
        minuteSelect2.setEditable(false);
        minuteSelect2.setPrefWidth(120);

        //Adding to Hbox
        timeSelect2.getChildren().addAll(hourSelect2,minuteSelect2);
        timeSelect2.setLayoutX(250);
        timeSelect2.setLayoutY(280);

        Rectangle yearBox = new Rectangle(190, 50);
        yearBox.setFill(Color.ALICEBLUE);

        Text yearLabel = new Text("To Hour:");
        yearLabel.setStyle("-fx-font-size: 16px;");
        stackPane2.getChildren().addAll(yearBox, yearLabel);
        stackPane2.setLayoutX(15);
        stackPane2.setLayoutY(270);

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
        root.getChildren().add(timeSelect1);
        root.getChildren().add(timeSelect2);
        root.getChildren().add(imageView1);
        root.getChildren().add(logoText);
        root.getChildren().add(submitButton);
        root.getChildren().add(backButton);

        //Submit button in action + checks for valid inputs
        submitButton.setOnAction(_ -> {
            boolean isValid = true;
            String errorMessage = "";

            int fromHour = hourSelect1.getValue();
            int fromMinute = minuteSelect1.getValue();
            int toHour = hourSelect2.getValue();
            int toMinute = minuteSelect2.getValue();

            // Validation: ensure end time is after start time
            if (toHour < fromHour || (toHour == fromHour && toMinute <= fromMinute)) {
                isValid = false;
                errorMessage += "End time must be after start time.\n";
            }

            if (isValid) {
                TimeSlotEntry newEntry = new TimeSlotEntry(fromHour, fromMinute, toHour, toMinute);

                try {
                    saveTimeSlotToFile(newEntry);
                    EntrySort.addSortedTimeSlotData(EntrySort.readTimeSlotCSV("data/semester_time_slots.csv"));
                    showAlert("Success", "Time slot entry successfully added.");
                } catch (IOException ex) {
                    showAlert("Error", "Failed to save data.");
                }
            } else {
                showAlert("Error", errorMessage);
            }
        });


        backButton.setOnAction(_ -> {
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
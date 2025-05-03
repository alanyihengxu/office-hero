package s25.cs151.application.view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import s25.cs151.application.controller.OfficeHourController;
import s25.cs151.application.model.OfficeHourEntry;

import java.util.Arrays;
import java.util.List;

public class OfficeHourPage {

    public static void setActive(Stage stage) {
        Pane root = new Pane();

        // Main background
        Rectangle background = new Rectangle(1000, 600);
        background.setFill(Color.KHAKI);
        root.getChildren().add(background);

        // Sidebar background
        Rectangle sidebar = new Rectangle(220, 600);
        sidebar.setFill(Color.DARKKHAKI);
        sidebar.setLayoutX(0);
        sidebar.setLayoutY(0);
        root.getChildren().add(sidebar);

        // Title
        Text title = new Text("Semester Office Hours");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        title.setLayoutX(250);
        title.setLayoutY(60);
        root.getChildren().add(title);

        // TableView for existing office hours
        TableView<OfficeHourEntry> tableView = new TableView<>();
        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(450, 250);

        TableColumn<OfficeHourEntry, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<OfficeHourEntry, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<OfficeHourEntry, List<String>> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        daysCol.setCellFactory(col -> new TableCell<>() {
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

        tableView.getColumns().addAll(semesterCol, yearCol, daysCol);

        // Load existing office hours
        ObservableList<OfficeHourEntry> entries = OfficeHourController.loadOfficeHours();
        tableView.setItems(entries);

        root.getChildren().add(tableView);

        // Form labels and fields
        // Semester Label
        Rectangle semesterLabelBox = new Rectangle(190, 50);
        semesterLabelBox.setFill(Color.ALICEBLUE);
        StackPane semesterPane = new StackPane(semesterLabelBox, new Text("Select the current semester:"));
        semesterPane.setLayoutX(15);
        semesterPane.setLayoutY(120);
        root.getChildren().add(semesterPane);

        ComboBox<String> semesterBox = new ComboBox<>();
        semesterBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterBox.setValue("Spring");
        semesterBox.setLayoutX(250);
        semesterBox.setLayoutY(130);
        root.getChildren().add(semesterBox);

        // Year Label
        Rectangle yearLabelBox = new Rectangle(190, 50);
        yearLabelBox.setFill(Color.ALICEBLUE);
        StackPane yearPane = new StackPane(yearLabelBox, new Text("Enter the current year:"));
        yearPane.setLayoutX(15);
        yearPane.setLayoutY(270);
        root.getChildren().add(yearPane);

        TextField yearField = new TextField();
        yearField.setPromptText("Enter the year");
        yearField.setLayoutX(250);
        yearField.setLayoutY(280);
        root.getChildren().add(yearField);

        // Days Label
        Rectangle daysLabelBox = new Rectangle(190, 50);
        daysLabelBox.setFill(Color.ALICEBLUE);
        StackPane daysPane = new StackPane(daysLabelBox, new Text("Select the days available:"));
        daysPane.setLayoutX(15);
        daysPane.setLayoutY(400);
        root.getChildren().add(daysPane);

        List<CheckBox> dayCheckboxes = Arrays.asList(
                new CheckBox("Monday"), new CheckBox("Tuesday"),
                new CheckBox("Wednesday"), new CheckBox("Thursday"),
                new CheckBox("Friday")
        );
        int yStart = 400;
        for (CheckBox cb : dayCheckboxes) {
            cb.setLayoutX(250);
            cb.setLayoutY(yStart);
            root.getChildren().add(cb);
            yStart += 30;
        }

        // Buttons
        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(570);
        root.getChildren().add(submitButton);

        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        root.getChildren().add(backButton);

        // Logo
        Image logo = new Image("logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setLayoutX(700);
        logoView.setLayoutY(380);
        logoView.setFitWidth(200);
        logoView.setFitHeight(200);
        root.getChildren().add(logoView);

        Text logoText = new Text("Office Hero");
        logoText.setStyle("-fx-font-size: 36px; -fx-font-style: italic;");
        logoText.setLayoutX(730);
        logoText.setLayoutY(370);
        root.getChildren().add(logoText);

        // Attach functionality
        OfficeHourController.attachHandlers(stage, semesterBox, yearField, dayCheckboxes, submitButton, backButton);

        // Final Scene
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}

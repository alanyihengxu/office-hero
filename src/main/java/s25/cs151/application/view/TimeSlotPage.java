package s25.cs151.application.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import s25.cs151.application.controller.TimeSlotController;
import s25.cs151.application.model.entry.TimeSlotEntry;

public class TimeSlotPage {

    public static void setActive(Stage stage) {
        Pane root = new Pane();

        // Main background
        Rectangle background = new Rectangle(1000, 600);
        background.setFill(Color.KHAKI);
        root.getChildren().add(background);

        // Sidebar background
        Rectangle sidebar = new Rectangle(220, 600);
        sidebar.setFill(Color.DARKKHAKI);
        root.getChildren().add(sidebar);

        // Title
        Text title = new Text("Semester Time Slots");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        title.setLayoutX(250);
        title.setLayoutY(60);
        root.getChildren().add(title);

        // TableView
        TableView<TimeSlotEntry> tableView = new TableView<>();
        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(450, 250);

        TableColumn<TimeSlotEntry, String> fromTimeCol = new TableColumn<>("From Time");
        fromTimeCol.setCellValueFactory(cellData -> {
            TimeSlotEntry entry = cellData.getValue();
            return new javafx.beans.property.ReadOnlyStringWrapper(
                    String.format("%02d:%02d", entry.getFromHour(), entry.getFromMinute())
            );
        });

        TableColumn<TimeSlotEntry, String> toTimeCol = new TableColumn<>("To Time");
        toTimeCol.setCellValueFactory(cellData -> {
            TimeSlotEntry entry = cellData.getValue();
            return new javafx.beans.property.ReadOnlyStringWrapper(
                    String.format("%02d:%02d", entry.getToHour(), entry.getToMinute())
            );
        });

        tableView.getColumns().addAll(fromTimeCol, toTimeCol);

        tableView.setItems(TimeSlotController.loadTimeSlots());
        root.getChildren().add(tableView);

        // Form Fields
        Rectangle fromLabelBox = new Rectangle(190, 50);
        fromLabelBox.setFill(Color.ALICEBLUE);
        StackPane fromPane = new StackPane(fromLabelBox, new Text("From Hour:"));
        fromPane.setLayoutX(15);
        fromPane.setLayoutY(120);
        root.getChildren().add(fromPane);

        Spinner<Integer> fromHour = new Spinner<>(0, 23, 9);
        Spinner<Integer> fromMinute = new Spinner<>(0, 45, 0, 15);
        fromHour.setPrefWidth(80);
        fromMinute.setPrefWidth(80);
        fromHour.setLayoutX(250);
        fromHour.setLayoutY(130);
        fromMinute.setLayoutX(350);
        fromMinute.setLayoutY(130);
        root.getChildren().addAll(fromHour, fromMinute);

        Rectangle toLabelBox = new Rectangle(190, 50);
        toLabelBox.setFill(Color.ALICEBLUE);
        StackPane toPane = new StackPane(toLabelBox, new Text("To Hour:"));
        toPane.setLayoutX(15);
        toPane.setLayoutY(270);
        root.getChildren().add(toPane);

        Spinner<Integer> toHour = new Spinner<>(0, 23, 10);
        Spinner<Integer> toMinute = new Spinner<>(0, 45, 0, 15);
        toHour.setPrefWidth(80);
        toMinute.setPrefWidth(80);
        toHour.setLayoutX(250);
        toHour.setLayoutY(280);
        toMinute.setLayoutX(350);
        toMinute.setLayoutY(280);
        root.getChildren().addAll(toHour, toMinute);

        // Buttons
        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(370);

        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);

        root.getChildren().addAll(submitButton, backButton);

        // Logo
        Image logo = new Image("logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setLayoutX(700);
        logoView.setLayoutY(380);
        logoView.setFitWidth(200);
        logoView.setFitHeight(200);

        Text logoText = new Text("Office Hero");
        logoText.setStyle("-fx-font-size: 36px; -fx-font-style: italic;");
        logoText.setLayoutX(730);
        logoText.setLayoutY(370);

        root.getChildren().addAll(logoView, logoText);

        // Attach functionality
        TimeSlotController.attachHandlers(stage, fromHour, fromMinute, toHour, toMinute, submitButton, backButton);

        // Scene setup
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}

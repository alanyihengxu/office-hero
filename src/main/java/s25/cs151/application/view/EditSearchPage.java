package s25.cs151.application.view;

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
import s25.cs151.application.controller.EditController;
import s25.cs151.application.model.AppointmentEntry;

import java.io.IOException;


public class EditSearchPage {
    /**
     * This method makes it so a stage that becomes active.
     * This stage houses the search page which allows users to
     * 1.Select appointments by student name
     * 2.Edit Appointments
     * @param: Stage (stage object)
     * @return: Void
     *
     */

    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();
        StackPane stackPane = new StackPane();

        Rectangle blueRectangle = new Rectangle(220, 600);
        blueRectangle.setFill(Color.DARKKHAKI);

        blueRectangle.setLayoutX(0);
        blueRectangle.setLayoutY(0);

        // Add the blue rectangle to the root
        root.getChildren().add(blueRectangle);

        //Background color
        Rectangle backgroundColor = new Rectangle(1000, 600);
        backgroundColor.setFill(Color.KHAKI);
        root.getChildren().add(backgroundColor);

        // Main title
        Text title = new Text("Edit Appointments");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(260);
        title.setLayoutY(60);
        root.getChildren().add(title);

        //Appointments List
        TableView<AppointmentEntry> tableView = new TableView<>();

        TableColumn<AppointmentEntry, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<AppointmentEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<AppointmentEntry, String> timeSlotCol = new TableColumn<>("Time Slot");
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));

        TableColumn<AppointmentEntry, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<AppointmentEntry, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn<AppointmentEntry, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(dateCol);
        tableView.getColumns().add(timeSlotCol);
        tableView.getColumns().add(courseCol);
        tableView.getColumns().add(reasonCol);
        tableView.getColumns().add(commentCol);

        tableView.setLayoutX(15);
        tableView.setLayoutY(180);
        tableView.setPrefSize(600, 400);
        root.getChildren().add(tableView);

        EditController.initialTableView(tableView);

        Rectangle searchBox = new Rectangle(190, 50);
        searchBox.setFill(Color.ALICEBLUE);

        Text searchLabel = new Text("Search student name:");
        searchLabel.setStyle("-fx-font-size: 16px;");
        stackPane.getChildren().addAll(searchBox, searchLabel);
        stackPane.setLayoutX(15);
        stackPane.setLayoutY(90);
        root.getChildren().add(stackPane);

        //Search Bar
        TextField name = new TextField();
        name.setPromptText("Enter student's name");
        name.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        name.setFocusTraversable(false);
        name.setLayoutX(250);
        name.setLayoutY(100);
        root.getChildren().add(name);

        //Search button
        Button searchButton = new Button("Search");
        searchButton.setLayoutX(500);
        searchButton.setLayoutY(110);
        root.getChildren().add(searchButton);

        Button editButton = new Button("Edit");
        editButton.setLayoutX(560);
        editButton.setLayoutY(110);
        root.getChildren().add(editButton);

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

        //Back to home page button
        Button backButton = new Button("Back to Home Page");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        root.getChildren().add(backButton);

        //back to home button
        backButton.setOnAction(e -> EditController.mainMenu(stage));

        searchButton.setOnAction(e -> EditController.search(name, tableView));

        editButton.setOnAction(e -> EditController.edit(stage, tableView));

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}
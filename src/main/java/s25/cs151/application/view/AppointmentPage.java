package s25.cs151.application.view;

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
import s25.cs151.application.controller.AppointmentController;
import s25.cs151.application.controller.sort.EntrySort;
import s25.cs151.application.model.AppointmentEntry;
import s25.cs151.application.controller.sort.AppointmentEntrySort;

import java.io.*;
import java.time.LocalDate;

public class AppointmentPage {
    /**
     * This method makes it so a stage that becomes active.
     * This stage houses the appointment page which allows users to
     * 1.Enter student's name
     * 2.Choose schedule date
     * 3.Select time slot
     * 4.Select course
     * 5.Enter reason and comment optionally
     * @param: Stage (stage object)
     * @return: Void
     *
     */

    public static void setActive(Stage stage) throws IOException {
        Pane root = new Pane();
        StackPane stackPane = new StackPane();
        StackPane stackPane2 = new StackPane();
        StackPane stackPane3 = new StackPane();
        StackPane stackPane4 = new StackPane();
        StackPane stackPane5 = new StackPane();

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

        // Sidebar background
        Rectangle sidebar = new Rectangle(220, 600);
        sidebar.setFill(Color.DARKKHAKI);
        sidebar.setLayoutX(0);
        sidebar.setLayoutY(0);
        root.getChildren().add(sidebar);

        // Main title
        Text title = new Text("Appointment");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(260);
        title.setLayoutY(60);
        root.getChildren().add(title);

        //Appointments
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

        EntrySort<AppointmentEntry> reader = new AppointmentEntrySort();
        ObservableList<AppointmentEntry> appointments =
                FXCollections.observableArrayList(reader.readAndSort("data/appointments.csv"));
        tableView.setItems(appointments);

        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(450, 250);
        root.getChildren().add(tableView);

        Rectangle studentBox = new Rectangle(190, 50);
        studentBox.setFill(Color.ALICEBLUE);

        Text studentLabel = new Text("Student Name:");
        studentLabel.setStyle("-fx-font-size: 16px;");
        stackPane2.getChildren().addAll(studentBox, studentLabel);
        stackPane2.setLayoutX(15);
        stackPane2.setLayoutY(90);
        root.getChildren().add(stackPane2);

        //Student name
        TextField name = new TextField();
        name.setPromptText("Enter student's full name");
        name.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        name.setFocusTraversable(false);
        name.setLayoutX(250);
        name.setLayoutY(100);
        root.getChildren().add(name);

        Rectangle dateBox = new Rectangle(190, 50);
        dateBox.setFill(Color.ALICEBLUE);

        Text dateLabel = new Text("Select the date:");
        dateLabel.setStyle("-fx-font-size: 16px;");
        stackPane.getChildren().addAll(dateBox, dateLabel);
        stackPane.setLayoutX(15);
        stackPane.setLayoutY(180);
        root.getChildren().add(stackPane);

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());
        date.setLayoutX(250);
        date.setLayoutY(190);
        root.getChildren().add(date);

        Rectangle timeBox = new Rectangle(190, 50);
        timeBox.setFill(Color.ALICEBLUE);

        Text timeLabel = new Text("Select the time:");
        timeLabel.setStyle("-fx-font-size: 16px;");
        stackPane3.getChildren().addAll(timeBox, timeLabel);
        stackPane3.setLayoutX(15);
        stackPane3.setLayoutY(260);
        root.getChildren().add(stackPane3);

        //Time slot selection
        ComboBox<String> timeSlot = new ComboBox<>();
        timeSlot.getItems().addAll(AppointmentController.loadTimeSlots());
        if (!timeSlot.getItems().isEmpty())
            timeSlot.setValue(timeSlot.getItems().get(0));
        timeSlot.setLayoutX(250);
        timeSlot.setLayoutY(270);
        root.getChildren().add(timeSlot);

        Rectangle courseBox = new Rectangle(190, 50);
        courseBox.setFill(Color.ALICEBLUE);

        Text courseLabel = new Text("Select the course:");
        courseLabel.setStyle("-fx-font-size: 16px;");
        stackPane4.getChildren().addAll(courseBox, courseLabel);
        stackPane4.setLayoutX(15);
        stackPane4.setLayoutY(340);
        root.getChildren().add(stackPane4);

        //Time slot selection
        ComboBox<String> course = new ComboBox<>();
        course.getItems().addAll(AppointmentController.loadCourses());
        if (!course.getItems().isEmpty())
           course.setValue(course.getItems().get(0));

        course.setLayoutX(250);
        course.setLayoutY(350);
        root.getChildren().add(course);

        Rectangle reasonCommentBox = new Rectangle(190, 150);
        reasonCommentBox.setFill(Color.ALICEBLUE);

        Text reasonCommentLabel = new Text("Provide a reason \n or any comments");
        reasonCommentLabel.setStyle("-fx-font-size: 16px;");
        stackPane5.getChildren().addAll(reasonCommentBox, reasonCommentLabel);
        stackPane5.setLayoutX(14);
        stackPane5.setLayoutY(400);
        root.getChildren().add(stackPane5);

        //Reason
        TextField reason = new TextField();
        reason.setPromptText("Reason (optional)");
        reason.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        reason.setFocusTraversable(false);
        reason.setLayoutX(250);
        reason.setLayoutY(420);
        root.getChildren().add(reason);

        //Comment
        TextField comment = new TextField();
        comment.setPromptText("Comment (optional)");
        comment.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        comment.setFocusTraversable(false);
        comment.setLayoutX(250);
        comment.setLayoutY(500);
        root.getChildren().add(comment);

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

        //Sends all necessary info to AppointmentController
        AppointmentController.attachHandlers(name, date, timeSlot, course, reason, comment, submitButton, backButton, stage);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);

    }
}
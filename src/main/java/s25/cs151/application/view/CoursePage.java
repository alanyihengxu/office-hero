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
import s25.cs151.application.controller.CourseController;
import s25.cs151.application.model.CourseEntry;

public class CoursePage {

    public static void setActive(Stage stage) {
        Pane root = new Pane();

        Rectangle background = new Rectangle(1000, 600);
        background.setFill(Color.KHAKI);
        root.getChildren().add(background);

        Rectangle sidebar = new Rectangle(220, 600);
        sidebar.setFill(Color.DARKKHAKI);
        root.getChildren().add(sidebar);

        Text title = new Text("Course Information");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        title.setLayoutX(260);
        title.setLayoutY(60);
        root.getChildren().add(title);

        // TableView
        TableView<CourseEntry> tableView = new TableView<>();
        tableView.setLayoutX(500);
        tableView.setLayoutY(80);
        tableView.setPrefSize(450, 250);

        TableColumn<CourseEntry, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseCode"));

        TableColumn<CourseEntry, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseName"));

        TableColumn<CourseEntry, String> sectionCol = new TableColumn<>("Section Number");
        sectionCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("sectionNumber"));

        tableView.getColumns().addAll(codeCol, nameCol, sectionCol);

        tableView.setItems(CourseController.loadCourses());

        root.getChildren().add(tableView);

        // Form fields
        TextField courseCode = new TextField();
        courseCode.setPromptText("Enter course code");
        courseCode.setLayoutX(250);
        courseCode.setLayoutY(130);

        TextField courseName = new TextField();
        courseName.setPromptText("Enter course name");
        courseName.setLayoutX(250);
        courseName.setLayoutY(280);

        TextField sectionNumber = new TextField();
        sectionNumber.setPromptText("Enter section number");
        sectionNumber.setLayoutX(250);
        sectionNumber.setLayoutY(415);

        // Labels
        Rectangle codeLabelBox = new Rectangle(190, 50);
        codeLabelBox.setFill(Color.ALICEBLUE);
        StackPane codePane = new StackPane(codeLabelBox, new Text("Course Code:"));
        codePane.setLayoutX(15);
        codePane.setLayoutY(120);

        Rectangle nameLabelBox = new Rectangle(190, 50);
        nameLabelBox.setFill(Color.ALICEBLUE);
        StackPane namePane = new StackPane(nameLabelBox, new Text("Course Name:"));
        namePane.setLayoutX(15);
        namePane.setLayoutY(270);

        Rectangle sectionLabelBox = new Rectangle(190, 50);
        sectionLabelBox.setFill(Color.ALICEBLUE);
        StackPane sectionPane = new StackPane(sectionLabelBox, new Text("Section Number:"));
        sectionPane.setLayoutX(15);
        sectionPane.setLayoutY(400);

        root.getChildren().addAll(codePane, namePane, sectionPane, courseCode, courseName, sectionNumber);

        // Buttons
        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(570);

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
        CourseController.attachHandlers(stage, courseCode, courseName, sectionNumber, submitButton, backButton);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}

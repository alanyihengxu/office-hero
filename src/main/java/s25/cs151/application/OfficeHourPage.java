package s25.cs151.application;

import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OfficeHourPage {

    // Method to show an alert box
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        Text title = new Text("Semester Office Hours");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setLayoutX(250);
        title.setLayoutY(60);
        root.getChildren().add(title);

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
        Image calendar = new Image("calendar1.png");
        ImageView imageView = new ImageView(calendar);
        imageView.setLayoutX(500);
        imageView.setLayoutY(80);
        imageView.setFitWidth(450);
        imageView.setFitHeight(250);

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
        root.getChildren().add(imageView);
        root.getChildren().add(imageView1);
        root.getChildren().add(logoText);
        root.getChildren().add(submitButton);
        root.getChildren().add(backButton);

        //Submit button in action + checks for valid inputs
        submitButton.setOnAction(_->{
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
                try {
                    MainMenuPage.setActive(stage);  // Switch to NewScene
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                String alertTitle = "Error";
                showAlert(alertTitle, errorMessage);
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
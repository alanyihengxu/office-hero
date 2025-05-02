package s25.cs151.application.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import s25.cs151.application.controller.MainMenuController;

import java.time.LocalDate;

public class MainMenuPage {

    public static void setActive(Stage stage) {
        VBox root = new VBox();

        // Title Banner
        Label mainMenuLabel = new Label("Main Menu");
        mainMenuLabel.setFont(new Font(64));

        HBox titleBanner = new HBox();
        titleBanner.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        HBox titleBorder = new HBox();
        titleBorder.setMinWidth(950);
        titleBorder.setMinHeight(100);
        titleBorder.setAlignment(Pos.CENTER);
        titleBorder.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, null, null)));
        titleBorder.getChildren().add(mainMenuLabel);

        titleBanner.setPadding(new Insets(20, 0, 0, 0));
        titleBanner.setAlignment(Pos.CENTER);
        titleBanner.getChildren().add(titleBorder);

        // Buttons
        Button btOfficeHours = new Button("Office Hours");
        Button btNewHours = new Button("New Hours");
        Button btNewCourse = new Button("New Course");
        Button btNewAppointment = new Button("New Appointment");
        Button btEditAppointment = new Button("Update");

        HBox buttonRow = new HBox(120);
        buttonRow.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        buttonRow.setPadding(new Insets(150, 40, 20, 40));
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(btOfficeHours, btNewHours, btNewCourse, btNewAppointment, btEditAppointment);

        // Icon Row
        HBox iconRow = new HBox();
        iconRow.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        iconRow.setAlignment(Pos.CENTER);

        // Individual Icons with spacing
        iconRow.getChildren().addAll(
                createIconBox("OfficeHoursIcon.png", 50, 50, 16),
                createIconBox("clock.png", 50, 50, 145),
                createIconBox("document.png", 50, 50, 155),
                createIconBox("calendar.png", 50, 50, 165),
                createIconBox("edit.png", 45, 45, 157)
        );

        // Lower Section (Calendar + Buttons + Logo)
        HBox lowerSection = new HBox();
        lowerSection.setMaxWidth(1000);
        lowerSection.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        // Calendar
        VBox calendarBox = new VBox();
        calendarBox.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        calendarBox.setPadding(new Insets(50, 40, 80, 40));
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        calendarBox.getChildren().add(popupContent);

        // Search + Report buttons
        Button btSearchAppointments = new Button("Search Appointments");
        Button btDailyReport = new Button("Daily Report (PDF)");

        VBox stackedButtons = new VBox();
        stackedButtons.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        stackedButtons.setPadding(new Insets(100, 40, 100, 150));

        HBox searchButtonBox = new HBox(btSearchAppointments);
        searchButtonBox.setPadding(new Insets(0, 40, 50, 0));
        HBox reportButtonBox = new HBox(btDailyReport);

        stackedButtons.getChildren().addAll(searchButtonBox, reportButtonBox);

        // Office Hero logo
        VBox logoBox = new VBox();
        Image logo = new Image("OfficeManagerLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(150);
        logoView.setFitWidth(150);

        Label officeLabel = new Label(" Office");
        officeLabel.setFont(new Font("Arial", 25));
        Label heroLabel = new Label("           Hero");
        heroLabel.setFont(new Font("Arial", 25));

        logoBox.setPadding(new Insets(20, 0, 0, 100));
        logoBox.getChildren().addAll(officeLabel, heroLabel, logoView);

        // Combine into lower section
        lowerSection.getChildren().addAll(calendarBox, stackedButtons, logoBox);

        // Attach event handlers
        MainMenuController.attachHandlers(
                stage,
                btOfficeHours,
                btNewHours,
                btNewCourse,
                btNewAppointment,
                btEditAppointment,
                btSearchAppointments,
                btDailyReport
        );

        // Build the full root
        root.getChildren().addAll(titleBanner, buttonRow, iconRow, lowerSection);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(logo);
    }

    // Helper method for creating icon boxes
    private static VBox createIconBox(String imagePath, int width, int height, int leftPadding) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        VBox box = new VBox(imageView);
        box.setPadding(new Insets(0, 0, 0, leftPadding));
        return box;
    }
}

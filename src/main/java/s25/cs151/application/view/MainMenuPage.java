package s25.cs151.application.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import s25.cs151.application.controller.MainMenuController;

import java.time.LocalDate;

public class MainMenuPage {

    public static void setActive(Stage stage) {
        // Title
        Label mainMenu = new Label("Main Menu");
        mainMenu.setFont(new Font(64));

        // Labels
        Label office = new Label(" Office");
        office.setFont(new Font("Arial", 25));
        Label hero = new Label("           Hero");
        hero.setFont(new Font("Arial", 25));

        // Buttons
        Button officeHoursButton = new Button("Office Hours");
        Button newHoursButton = new Button("New Hours");
        Button newCourseButton = new Button("New Course");
        Button newAppointmentButton = new Button("New Appointment");
        Button editAppointmentButton = new Button("Update");
        Button searchAppointmentsButton = new Button("Search Appointments");
        Button dailyReportButton = new Button("Daily Report (PDF)");

        // Attach event handlers via controller
        MainMenuController.attachHandlers(
                stage,
                officeHoursButton,
                newHoursButton,
                newCourseButton,
                newAppointmentButton,
                editAppointmentButton,
                searchAppointmentsButton,
                dailyReportButton
        );

        // Layout setup
        HBox titleBox = new HBox();
        titleBox.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        HBox titleBorder = new HBox();
        titleBorder.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, null, null)));
        titleBorder.setMinSize(950, 100);
        titleBorder.setAlignment(Pos.CENTER);
        titleBorder.getChildren().add(mainMenu);
        titleBox.setPadding(new Insets(20, 0, 0, 0));
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(titleBorder);

        HBox buttonsRow = new HBox(120);
        buttonsRow.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        buttonsRow.setPadding(new Insets(150, 40, 20, 40));
        buttonsRow.getChildren().addAll(officeHoursButton, newHoursButton, newCourseButton, newAppointmentButton, editAppointmentButton);

        // Calendar
        VBox calendarBox = new VBox();
        calendarBox.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        calendarBox.setPadding(new Insets(50, 40, 80, 40));
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        calendarBox.getChildren().add(datePickerSkin.getPopupContent());

        // Side Buttons
        VBox sideButtons = new VBox();
        sideButtons.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        sideButtons.setPadding(new Insets(100, 40, 100, 150));
        HBox searchBtnBox = new HBox();
        searchBtnBox.setPadding(new Insets(0, 40, 50, 0));
        searchBtnBox.getChildren().add(searchAppointmentsButton);
        HBox reportBtnBox = new HBox();
        reportBtnBox.getChildren().add(dailyReportButton);
        sideButtons.getChildren().addAll(searchBtnBox, reportBtnBox);

        // Logo
        VBox logoBox = new VBox();
        Image logo = new Image("OfficeManagerLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(150);
        logoView.setFitWidth(150);
        logoBox.setPadding(new Insets(20, 0, 0, 100));
        logoBox.getChildren().addAll(office, hero, logoView);

        HBox bottomRow = new HBox();
        bottomRow.getChildren().addAll(calendarBox, sideButtons, logoBox);
        bottomRow.setMaxWidth(1000);
        bottomRow.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        VBox root = new VBox();
        root.getChildren().addAll(titleBox, buttonsRow, bottomRow);

        // Final scene setup
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(logo);
    }
}

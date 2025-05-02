package s25.cs151.application.controller;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import s25.cs151.application.view.*;

import java.io.IOException;

public class MainMenuController {

    public static void attachHandlers(
            Stage stage,
            Button officeHoursButton,
            Button newHoursButton,
            Button newCourseButton,
            Button newAppointmentButton,
            Button editAppointmentButton,
            Button searchAppointmentsButton,
            Button dailyReportButton
    ) {
        officeHoursButton.setOnAction(e -> {
            try {
                handleOfficeHours(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        newHoursButton.setOnAction(e -> {
            try {
                handleNewHours(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        newCourseButton.setOnAction(e -> {
            try {
                handleNewCourse(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        newAppointmentButton.setOnAction(e -> handleNewAppointment(stage));
        editAppointmentButton.setOnAction(e -> handleEditAppointment(stage));
        searchAppointmentsButton.setOnAction(e -> handleSearchAppointments(stage));
        dailyReportButton.setOnAction(e -> handleDailyReport(stage));
    }

    private static void handleOfficeHours(Stage stage) throws IOException {
        OfficeHourPage.setActive(stage);
    }

    private static void handleNewHours(Stage stage) throws IOException {
        TimeSlotPage.setActive(stage);
    }

    private static void handleNewCourse(Stage stage) throws IOException {
        CoursePage.setActive(stage);
    }

    private static void handleNewAppointment(Stage stage) {
        try {
            AppointmentPage.setActive(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleEditAppointment(Stage stage) {
        try {
            EditSearchPage.setActive(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleSearchAppointments(Stage stage) {
        try {
            SearchPage.setActive(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleDailyReport(Stage stage) {
        // Placeholder for future daily report functionality
        System.out.println("Daily Report feature coming soon.");
    }
}

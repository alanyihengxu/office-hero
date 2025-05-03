module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.controller;
    opens s25.cs151.application.controller to javafx.fxml;
    exports s25.cs151.application.view;
    opens s25.cs151.application.view to javafx.fxml;
    exports s25.cs151.application.controller.sort;
    opens s25.cs151.application.controller.sort to javafx.fxml;
    exports s25.cs151.application.model.entry;
    opens s25.cs151.application.model.entry to javafx.fxml;
}
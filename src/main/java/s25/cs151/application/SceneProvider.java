package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class SceneProvider {
    // Disable instantiation
    private SceneProvider() {}

    public static Scene getMainScene() {
        //Main Menu Title
        Label mainMenu = new Label("Main Menu");
        mainMenu.setFont(new Font( 64));

        Label office = new Label(" Office");
        office.setFont(new Font("Arial", 25));
        Label hero  = new Label("           Hero");
        hero.setFont(new Font("Arial", 25));

        //Buttons
        Button bt1 = new Button("Office Hours");

        Button bt2 = new Button("New Hours");
        Button bt3 = new Button("New Course");
        Button bt4 = new Button("New Appointment");
        Button bt5 = new Button("Update");

        //Buttons for Bottom
        Button stackBt6 = new Button("Search Appointments");
        Button stackBt7 = new Button("Daily Report (PDF)");

        HBox title = new HBox();
        title.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        HBox titleBorder = new HBox();

        title.setPadding(new Insets(20,0,0,0));
        titleBorder.setMinWidth(950);
        titleBorder.setMinHeight(100);
        titleBorder.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, null, null)));
        title.setAlignment(Pos.BASELINE_CENTER);
        titleBorder.setAlignment(Pos.BASELINE_CENTER);
        titleBorder.getChildren().add(mainMenu);

        title.getChildren().add(titleBorder);

        HBox Buttons = new HBox(120);
        Buttons.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        Buttons.setPadding(new Insets(150,40,20,40));
        Buttons.getChildren().addAll(bt1,bt2,bt3,bt4,bt5);

        HBox allIcons = new HBox();
        allIcons.setBackground(new Background(new BackgroundFill(Color.KHAKI,null,null)));

        HBox icon1 = new HBox();
        Image book = new Image("s25/cs151/application/OfficeHoursIcon.png");
        ImageView imageView1 = new ImageView(book);
        imageView1.setFitHeight(50);
        imageView1.setFitWidth(50);
        icon1.getChildren().add(imageView1);
        icon1.setPadding(new Insets(0,40,0,52));

        HBox icon2 = new HBox();
        Image clock = new Image("s25/cs151/application/clock.png");
        ImageView imageView2 = new ImageView(clock);
        imageView2.setFitHeight(50);
        imageView2.setFitWidth(50);
        icon2.getChildren().add(imageView2);
        icon2.setPadding(new Insets(0,0,0,108));

        HBox icon3 = new HBox();
        Image doc = new Image("s25/cs151/application/document.png");
        ImageView imageView3 = new ImageView(doc);
        imageView3.setFitHeight(50);
        imageView3.setFitWidth(50);
        icon3.getChildren().add(imageView3);
        icon3.setPadding(new Insets(0,0,0,155));

        HBox icon4 = new HBox();
        Image calendar = new Image("s25/cs151/application/calendar.png");
        ImageView imageView4 = new ImageView(calendar);
        imageView4.setFitHeight(50);
        imageView4.setFitWidth(50);
        icon4.getChildren().add(imageView4);
        icon4.setPadding(new Insets(0,0,0,165));

        HBox icon5 = new HBox();
        Image edit = new Image("s25/cs151/application/edit.png");
        ImageView imageView5 = new ImageView(edit);
        imageView5.setFitHeight(45);
        imageView5.setFitWidth(45);
        icon5.getChildren().add(imageView5);
        icon5.setPadding(new Insets(0,0,0,157));

        //DONE WITH ICONS NOW
        //Last HBox, containng the calendar and two more buttons

        HBox lastBox = new HBox();
        lastBox.setMaxWidth(1000);
        lastBox.setBackground(new Background(new BackgroundFill(Color.KHAKI ,null,null)));

        //CALENDAR VBOX
        VBox calendarPopUp = new VBox();
        calendarPopUp.setBackground(new Background(new BackgroundFill(Color.KHAKI,null,null)));
        calendarPopUp.setPadding(new Insets(50,40,80,40));
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        calendarPopUp.getChildren().add(popupContent);

        //TWO BUTTON VBOX
        VBox stackedButtons  = new VBox();
        stackedButtons.setBackground(new Background(new BackgroundFill(Color.KHAKI,null,null)));

        stackedButtons.setPadding(new Insets(100,40,100,150));

        HBox SButton1 = new HBox();
        SButton1.getChildren().add(stackBt6);
        SButton1.setPadding(new Insets(0,40,50,0));

        HBox SButton2 = new HBox();
        SButton2.getChildren().add(stackBt7);

        stackedButtons.getChildren().addAll(SButton1,SButton2);

        VBox mainlogo = new VBox();
        Image logo = new Image("s25/cs151/application/OfficeManagerLogo.png");
        ImageView imageView6 = new ImageView(logo);
        imageView6.setFitHeight(150);
        imageView6.setFitWidth(150);
        mainlogo.setPadding(new Insets(20,0,0,100));
        mainlogo.getChildren().add(office);
        mainlogo.getChildren().add(hero);
        mainlogo.getChildren().add(imageView6);

        lastBox.getChildren().addAll(calendarPopUp, stackedButtons, mainlogo);

        // Image
        allIcons.getChildren().addAll(icon1, icon2, icon3, icon4, icon5);

        VBox root = new VBox();
        root.getChildren().addAll(title, Buttons, allIcons, lastBox);

        return new Scene(root, Constants.WIDTH, Constants.HEIGHT);
    }

    public static Scene getOfficeHoursScene() {
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

        Image calendar = new Image("s25/cs151/application/calendar1.png");
        ImageView imageView = new ImageView(calendar);
        imageView.setLayoutX(500);
        imageView.setLayoutY(80);
        imageView.setFitWidth(450);
        imageView.setFitHeight(250);

        Image logo = new Image("s25/cs151/application/logo.png");
        ImageView imageView1 = new ImageView(logo);
        imageView1.setLayoutX(700);
        imageView1.setLayoutY(380);
        imageView1.setFitWidth(200);
        imageView1.setFitHeight(200);

        Text logoText = new Text("Office Hero");
        logoText.setStyle("-fx-font-size: 36px;-fx-font-style: italic;");
        logoText.setLayoutX(730);
        logoText.setLayoutY(370);

        Button submit = new Button("Submit");
        submit.setLayoutX(250);
        submit.setLayoutY(570);

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
        root.getChildren().add(submit);

        return new Scene(root, Constants.WIDTH, Constants.HEIGHT);
    }
}

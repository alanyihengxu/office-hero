package s25.cs151.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;


public class MainMenuPage extends Application {

    @Override
    public void start(Stage stage) throws IOException {
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

        bt1.setOnAction(e-> {
            OfficeHourPage newScene = new OfficeHourPage();
            try {
                newScene.start(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

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
        Image book = new Image("OfficeHoursIcon.png");
        ImageView imageView1 = new ImageView(book);
        imageView1.setFitHeight(50);
        imageView1.setFitWidth(50);
        icon1.getChildren().add(imageView1);
        icon1.setPadding(new Insets(0,40,0,52));

        HBox icon2 = new HBox();
        Image clock = new Image("clock.png");
        ImageView imageView2 = new ImageView(clock);
        imageView2.setFitHeight(50);
        imageView2.setFitWidth(50);
        icon2.getChildren().add(imageView2);
        icon2.setPadding(new Insets(0,0,0,108));

        HBox icon3 = new HBox();
        Image doc = new Image("document.png");
        ImageView imageView3 = new ImageView(doc);
        imageView3.setFitHeight(50);
        imageView3.setFitWidth(50);
        icon3.getChildren().add(imageView3);
        icon3.setPadding(new Insets(0,0,0,155));

        HBox icon4 = new HBox();
        Image calendar = new Image("calendar.png");
        ImageView imageView4 = new ImageView(calendar);
        imageView4.setFitHeight(50);
        imageView4.setFitWidth(50);
        icon4.getChildren().add(imageView4);
        icon4.setPadding(new Insets(0,0,0,165));

        HBox icon5 = new HBox();
        Image edit = new Image("edit.png");
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
        Image logo = new Image("OfficeManagerLogo.png");
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

        Scene scene = new Scene(root, Constants.WIDTH,Constants.HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Office Hero - Welcome!");


        Image iconA = new Image("OfficeManagerLogo.png");
        stage.getIcons().add(iconA);
        stage.show();
    }
}
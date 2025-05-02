package s25.cs151.application.view;

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



public class MainMenuPage {

    /**
     * Returns a stage that becomes active. This stage houses the main menu UI, containing all
     * buttons for redirecting the user to different pages/stages.This stage also contains an
     * active calander view to facilitate users with planning.
     * @param: Stage (stage object)
     * @return: Void
     *
     */
    public static void setActive(Stage stage) throws IOException {
        //Main Menu Title for window
        Label mainMenu = new Label("Main Menu");
        mainMenu.setFont(new Font( 64));

        //All labels utilized throughout Main menu
        Label office = new Label(" Office");
        office.setFont(new Font("Arial", 25));
        Label hero  = new Label("           Hero");
        hero.setFont(new Font("Arial", 25));

        //Buttons for the user to be redirected to other scenes
        Button bt1 = new Button("Office Hours");
        Button bt2 = new Button("New Hours");
        Button bt3 = new Button("New Course");
        Button bt4 = new Button("New Appointment");
        Button bt5 = new Button("Update");

        //Buttons for user to enter search and export their list (At Bottom)
        Button stackBt6 = new Button("Search Appointments");
        Button stackBt7 = new Button("Daily Report (PDF)");

        //Office Hours Button event on click
        bt1.setOnAction(e-> {
            try {
                OfficeHourPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //New Hours Button event on click
        bt2.setOnAction(e-> {
            try {
                TimeSlotPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //New Course Button event on click
        bt3.setOnAction(e-> {
            try {
                CoursePage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //New Appointment Button event on click
        bt4.setOnAction(e-> {
            try {
                AppointmentPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Search Appointment Button event on click
        bt5.setOnAction(e-> {
            try {
                EditSearchPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Search Appointment Button event on click
        stackBt6.setOnAction(e-> {
            try {
                SearchPage.setActive(stage);  // Switch to NewScene
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //First Hbox at the top containning the background color for the Main Menu Heading
        HBox title = new HBox();
        title.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));

        //Second Hbox at the top layered on top of HBox title creating the contrast in colors
        HBox titleBorder = new HBox();

        title.setPadding(new Insets(20,0,0,0));
        titleBorder.setMinWidth(950);
        titleBorder.setMinHeight(100);
        titleBorder.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, null, null)));
        title.setAlignment(Pos.BASELINE_CENTER);
        titleBorder.setAlignment(Pos.BASELINE_CENTER);
        titleBorder.getChildren().add(mainMenu);

        title.getChildren().add(titleBorder);

        //Hbox containg all the buttons that are spaced out horizontally
        HBox Buttons = new HBox(120);
        Buttons.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
        Buttons.setPadding(new Insets(150,40,20,40));
        Buttons.getChildren().addAll(bt1,bt2,bt3,bt4,bt5);

        //Another Hbox under the button Hbox that holds all Icons to be displayed below the buttons
        HBox allIcons = new HBox();
        allIcons.setBackground(new Background(new BackgroundFill(Color.KHAKI,null,null)));

        //Every Ion has their own individual Hbox

        //First Icon created
        HBox icon1 = new HBox();
        Image book = new Image("OfficeHoursIcon.png");
        ImageView imageView1 = new ImageView(book);
        imageView1.setFitHeight(50);
        imageView1.setFitWidth(50);
        icon1.getChildren().add(imageView1);
        icon1.setPadding(new Insets(0,40,0,52));

        //second icon created
        HBox icon2 = new HBox();
        Image clock = new Image("clock.png");
        ImageView imageView2 = new ImageView(clock);
        imageView2.setFitHeight(50);
        imageView2.setFitWidth(50);
        icon2.getChildren().add(imageView2);
        icon2.setPadding(new Insets(0,0,0,108));

        //Third Icon created
        HBox icon3 = new HBox();
        Image doc = new Image("document.png");
        ImageView imageView3 = new ImageView(doc);
        imageView3.setFitHeight(50);
        imageView3.setFitWidth(50);
        icon3.getChildren().add(imageView3);
        icon3.setPadding(new Insets(0,0,0,155));

        //Fourth Icon created
        HBox icon4 = new HBox();
        Image calendar = new Image("calendar.png");
        ImageView imageView4 = new ImageView(calendar);
        imageView4.setFitHeight(50);
        imageView4.setFitWidth(50);
        icon4.getChildren().add(imageView4);
        icon4.setPadding(new Insets(0,0,0,165));

        //Fifth Icon created
        HBox icon5 = new HBox();
        Image edit = new Image("edit.png");
        ImageView imageView5 = new ImageView(edit);
        imageView5.setFitHeight(45);
        imageView5.setFitWidth(45);
        icon5.getChildren().add(imageView5);
        icon5.setPadding(new Insets(0,0,0,157));

        //combining all HBoxes created into the first Hbox named "allIcons"
        allIcons.getChildren().addAll(icon1, icon2, icon3, icon4, icon5);

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

        //Hbox Created for first Button "Search Appointments" and button added
        HBox SButton1 = new HBox();
        SButton1.getChildren().add(stackBt6);
        SButton1.setPadding(new Insets(0,40,50,0));

        //Hbox Created for second Button "Daily Report PDF" and button added
        HBox SButton2 = new HBox();
        SButton2.getChildren().add(stackBt7);

        //Adding both Hboxes to the VBox that will hold the buttons
        stackedButtons.getChildren().addAll(SButton1,SButton2);

        //VBox for logo desplayed in the bottom right of main page
        VBox mainlogo = new VBox();
        Image logo = new Image("OfficeManagerLogo.png");
        ImageView imageView6 = new ImageView(logo);
        imageView6.setFitHeight(150);
        imageView6.setFitWidth(150);
        mainlogo.setPadding(new Insets(20,0,0,100));
        mainlogo.getChildren().add(office);
        mainlogo.getChildren().add(hero);
        mainlogo.getChildren().add(imageView6);



        //Adding all Boxes created to the main Hbox for the entire bottom area
        lastBox.getChildren().addAll(calendarPopUp, stackedButtons, mainlogo);


        //Primary Vbox Where everything lies on top off
        VBox root = new VBox();
        root.getChildren().addAll(title, Buttons, allIcons, lastBox);

        //Setting up scene
        Scene scene = new Scene(root, 1000,600);
        stage.setScene(scene);
        stage.setResizable(false);


        Image iconA = new Image("OfficeManagerLogo.png");
        stage.getIcons().add(iconA);
    }
}
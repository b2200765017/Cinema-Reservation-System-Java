import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import static javafx.util.Duration.*;

// for building the scenes and making scene changes
public class SceneSetter {

    static Image usericon= new Image(new File("assets\\icons\\user.png").toURI().toString());
    static Image playImg= new Image(new File("assets\\icons\\pause.png").toURI().toString());
    static Image stopImg= new Image(new File("assets\\icons\\play.png").toURI().toString());
    static Image begin= new Image(new File("assets\\icons\\begin.png").toURI().toString());
    static Image forwardImg= new Image(new File("assets\\icons\\forward.png").toURI().toString());
    static Image backwardImg= new Image(new File("assets\\icons\\backward.png").toURI().toString());
    static ArrayList<Seat> selSeat = new ArrayList<>();

    static Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 13);
    static Font font_normal = Font.font("Arial", FontWeight.BOLD, 13);
    static Font font_big = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16);
    static Integer tryPass = 0;

    public static void firstScene(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox();
        int deneme = Integer.parseInt(Main.properties.getProperty("maximum-error-without-getting-blocked"));


        Label topLabel, username, password, error;
        Button signup, login;
        TextField textUser;
        PasswordField textPassword;
        AtomicReference<String> errortext = new AtomicReference<>();

        // creating the Buttons and labels
        topLabel = new Label("Welcome to the HUCS Cinema Reservation System!" +
                "\nPlease enter your credentials below and click LOGIN.\n" +
                "You can create a new account by clicking SIGN UP button.");
        username = new Label("Username:");
        password = new Label("Password:");
        signup = new Button("SIGN UP");
        login = new Button("LOG IN");
        textUser = new TextField();
        textPassword = new PasswordField();
        error = new Label(errortext.get());

        login.setDefaultButton(true);
        Styles.button(signup);
        Styles.button(login);

        topLabel.setFont(font);
        password.setFont(font_normal);
        username.setFont(font_normal);
        error.setFont(font_normal);
        textUser.setFont(font);
        textPassword.setFont(font);

        textUser.setPrefWidth(200);
        textPassword.setPrefWidth(200);


        pane1.getChildren().add(topLabel);
        pane2.getChildren().addAll(username, textUser);
        pane3.getChildren().addAll(password, textPassword);
        pane4.getChildren().addAll(signup, login);
        pane5.getChildren().addAll(error);

        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);
        pane4.setAlignment(Pos.CENTER);
        pane5.setAlignment(Pos.CENTER);
        pane1.setPadding(new Insets(0,0,15,0));
        pane4.setPadding(new Insets(25,0,0,0));
        pane5.setPadding(new Insets(5,0,0,0));
        pane2.setSpacing(148);
        pane3.setSpacing(150);
        pane4.setSpacing(200);


        vBox.getChildren().addAll(pane1, pane2, pane3, pane4, pane5);
        vBox.setPadding(new Insets(30, 15,10,15));
        vBox.setSpacing(20);

        Scene signScene = new Scene(vBox , 600, 330);
        window.setScene(signScene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - signScene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - signScene.getHeight ()) / 2);

        final int[] timer = {0};
        int blockTime = Integer.parseInt(Main.properties.getProperty("block-time"));

        Timeline counter = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> timer[0] -= 1));

        //action functions
        signup.setOnAction(event -> signUpScene(window));
        login.setOnAction(event -> {
            String userName = textUser.getText();
            String password1 = textPassword.getText();
            if (timer[0] == 0){
                if (User.match(userName, password1)){
                    FileOperations.playLogin();
                    welcomeScene(window);
                    tryPass = 0;
                }
                else{
                    tryPass++;
                    if(tryPass == deneme){
                        FileOperations.playBan();
                        error.setText("Maximum error reached. You can't log in for "+blockTime+" seconds");
                        tryPass = 0;
                        timer[0] = blockTime;
                        counter.setCycleCount(blockTime);
                        counter.play();
                        return;
                    }
                    FileOperations.playError();
                    error.setText("Error: Wrong username or password!");
                }
            }
            else{
                FileOperations.playError();
                error.setText("Error: You are banned for " + timer[0] + " seconds.");
            }
        } );
    }
    public static void signUpScene(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox(), pane6 = new HBox();
        //vBox.setBackground(bg);

        Button signup, login;
        Label topLabel, username, password, password2, error;
        TextField textUser;
        PasswordField textPassword, textPassword2;


        topLabel = new Label("Welcome to the HUCS Cinema Reservation System!" +
                "\nFill the form below to create a new account.\n" +
                "You can go to Log in page by clicking LOG IN Button.");

        username = new Label("Username:");
        password = new Label("Password:");
        password2 = new Label("Password:");
        error = new Label();
        signup = new Button("SIGN UP");
        login = new Button("LOG IN");
        textUser = new TextField();
        textPassword = new PasswordField();
        textPassword2 = new PasswordField();

        textUser.setPrefWidth(200);
        textPassword.setPrefWidth(200);
        textPassword2.setPrefWidth(200);

        topLabel.setFont(font);
        textUser.setFont(font);
        textPassword.setFont(font);
        textPassword2.setFont(font);
        username.setFont(font_normal);
        password.setFont(font_normal);
        password2.setFont(font_normal);
        error.setFont(font_normal);
        Styles.button(signup);
        Styles.button(login);


        pane1.getChildren().add(topLabel);
        pane2.getChildren().addAll(username, textUser);
        pane3.getChildren().addAll(password, textPassword);
        pane4.getChildren().addAll(password2, textPassword2);
        pane5.getChildren().addAll(login, signup);
        pane6.getChildren().add(error);

        pane2.setSpacing(223);
        pane3.setSpacing(225);
        pane4.setSpacing(223);
        pane5.setSpacing(335);

        pane1.setPadding(new Insets(0,0,15,0));
        pane2.setPadding(new Insets(-8,0,-10,0));
        pane3.setPadding(new Insets(0,0,-10,0));
        pane4.setPadding(new Insets(0,0,10,0));
        pane5.setPadding(new Insets(0,0,5,0));

        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);
        pane4.setAlignment(Pos.CENTER);
        pane5.setAlignment(Pos.CENTER);
        pane6.setAlignment(Pos.CENTER);


        vBox.getChildren().addAll(pane1, pane2, pane3, pane4, pane5, pane6);
        vBox.setPadding(new Insets(20, 15,10,15));
        vBox.setSpacing(20);

        Scene newScene = new Scene(vBox, 600, 330, Color.YELLOW);
        window.setScene(newScene);


        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - newScene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - newScene.getHeight ()) / 2);



        login.setOnAction(event -> firstScene(window));

        signup.setDefaultButton(true);
        signup.setOnAction(event -> {
            String userName = textUser.getText();
            String pass = textPassword.getText();
            String pass2 = textPassword2.getText();
            int cs = Controller.signUp(userName, pass, pass2);
            switch (cs){
                case 0: // username cannot be empty
                    FileOperations.playError();
                    error.setText("ERROR: Username cannot be empty!");
                    break;
                case 1: // passwords empty
                    FileOperations.playError();
                    error.setText("ERROR: Password cannot be empty!");
                    break;
                case 2: // password dont match
                    FileOperations.playError();
                    error.setText("ERROR: Passwords do not match!");
                    break;
                case 3: // user already exist
                    FileOperations.playError();
                    error.setText("ERROR: This username already exists!");
                    break;
                case -1: // user has been succesfully signed up
                    FileOperations.playSuccess();
                    AlertBox.display("Congratulations!", "SUCCESS: You have successfully registered \nwith your new credentials!");
                    welcomeScene(window);
                    break;
            }
        });
    }
    public static void welcomeScene(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox();

        User user = User.user;
        Label welcomeText;
        Button ok, logout, addFilm, removeFilm, editUsers;

        // after login in or signing in it should send the user as a message to here and use that user to get the info
        welcomeText = new Label();
        ok = new Button("OK");
        logout = new Button("LOG OUT");
        addFilm = new Button("ADD FILM");
        removeFilm = new Button("REMOVE FILM");
        editUsers = new Button("EDIT USERS");
        ImageView userIcon = new ImageView(usericon);

        userIcon.setFitHeight(50);
        userIcon.setFitWidth(50);

        Styles.button(ok);
        Styles.button(logout);
        Styles.button(addFilm);
        Styles.button(removeFilm);
        Styles.button(editUsers);
        welcomeText.setFont(font);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<Object> films = Main.data.get(1);
        for (Object ob: films){
            Film film = (Film) ob;
            choiceBox.getItems().add(film.getName());
        }
        try{
            Film value = (Film) films.get(0);
            choiceBox.setValue(value.getName());
            choiceBox.setPrefSize(400, 50);
        }catch (Exception ignored){}



        pane1.getChildren().addAll(userIcon, welcomeText);
        pane2.getChildren().addAll(choiceBox, ok);

        vBox.setPadding(new Insets(40, 10,10,10));
        vBox.setSpacing(20);
        pane3.setPadding(new Insets(40, 10, 0, 0));
        pane1.setPadding(new Insets(0, 0, 20, 50));
        pane1.setSpacing(50);

        pane2.setSpacing(30);
        pane3.setSpacing(30);
        pane1.setAlignment(Pos.CENTER_LEFT);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);


        if (user.isAdmin()){
            String status = user.isClubMember() ? "(Admin-Club member)":"( Admin)";
            welcomeText.setText("Welcome " + user.getUserName() + status + "!\nYou can either select film below or do edits.");
            pane3.getChildren().addAll(logout, editUsers, addFilm, removeFilm);
        } else{
            String status = (user.isClubMember()) ? "(Club member)":"";
            welcomeText.setText("Welcome " + user.getUserName() + status + "!\nSelect a film and then click OK to continue.");
            pane3.getChildren().add(logout);
        }

        ok.setDefaultButton(true);
        ok.setOnAction(event -> {
            String item = choiceBox.getSelectionModel().getSelectedItem();
            if (item == null){
                return;
            }
            for (Object ob: films){
                Film film = (Film) ob;
                if (film.getName().equals(item)){
                    Film.film = film;
                }
            }
            filmWindow(window);
        });

        logout.setOnAction(event -> firstScene(window));
        removeFilm.setOnAction(event -> removeFilm(window));
        addFilm.setOnAction(event -> addFilm(window));
        editUsers.setOnAction(event -> editUsers(window));

        vBox.getChildren().addAll(pane1, pane2, pane3);
        Scene scene = new Scene(vBox, 600, 330);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

    }
    public static void filmWindow(Stage window){
        window.setResizable(false);
        VBox pane = new VBox();
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox();
        ImageView forw, bakw, beg, sto, pla;
        //vBox.setBackground(bg);

        Button back, ok, add_hall, remove_hall, forward, backward, beginning, stop;
        Label welcomeText;

        String mediaPath = "assets\\trailers\\" + Film.film.getTrailerPath();
        welcomeText = new Label(Film.film.getName() + "(" + Film.film.getDuration() + "minutes)");
        ok = new Button("OK");
        back = new Button("<BACK");
        add_hall = new Button("Add Hall");
        remove_hall = new Button("Remove Hall");

        Media trailer = new Media(new File(mediaPath).toURI().toString());
        MediaPlayer video = new MediaPlayer(trailer);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(video);
        mediaView.setFitWidth(700);
        mediaView.setFitHeight(500);
        mediaView.setOnError(event -> {
            FileOperations.playError();
            AlertBox.display("Error!", "Couldn't load the trailer video please try again.");
            welcomeScene(window);
        });


        forward = new Button();
        backward = new Button();
        beginning = new Button();
        stop = new Button();
        stop.setAccessibleText(">");

        forw = new ImageView(forwardImg);
        bakw = new ImageView(backwardImg);
        beg = new ImageView(begin);
        sto = new ImageView(stopImg);
        pla = new ImageView(playImg);

        forw.setFitWidth(20);
        forw.setFitHeight(20);
        pla.setFitWidth(20);
        pla.setFitHeight(20);
        bakw.setFitWidth(20);
        bakw.setFitHeight(20);
        beg.setFitWidth(20);
        beg.setFitHeight(20);
        sto.setFitWidth(20);
        sto.setFitHeight(20);

        forward.setGraphic(forw);
        backward.setGraphic(bakw);
        beginning.setGraphic(beg);
        stop.setGraphic(sto);



        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<Object> halls = Main.data.get(2);
        ArrayList<Hall> filmHalls = new ArrayList<>();
        for (Object ob: halls){
            Hall hall = (Hall) ob;
            if (hall.getFilmName().equals(Film.film.getName())){
                choiceBox.getItems().add(hall.getHallName());
                filmHalls.add(hall);
            }
        }
        try {
            Hall value = filmHalls.get(0);
            choiceBox.setValue(value.getHallName());
        }catch (Exception ignored){

        }
        Slider slVolume = new Slider();
        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
        slVolume.setValue(50);
        slVolume.setPadding(new Insets(0,0,81,0));
        slVolume.setMinWidth(130);
        slVolume.setMinHeight(150);
        slVolume.setRotate(-90);
        video.volumeProperty().bind(slVolume.valueProperty().divide(100));

        Styles.button(ok);
        Styles.button(back);
        Styles.button(add_hall);
        Styles.button(remove_hall);
        Styles.cButton(forward, 25);
        Styles.cButton(backward, 25);
        Styles.cButton(stop, 25);
        Styles.cButton(beginning, 25);
        welcomeText.setFont(font);


        vBox.getChildren().addAll(backward,forward,stop, beginning, slVolume);
        vBox.setSpacing(10);

        pane.setPadding(new Insets(25,0,15,25));
        pane1.getChildren().add(welcomeText);
        pane1.setPadding(new Insets(0,0,20,0));
        pane2.getChildren().addAll(mediaView, vBox);
        pane2.setSpacing(25);
        pane1.setAlignment(Pos.CENTER);
        pane3.setSpacing(25);
        pane3.setPadding(new Insets(30,0,0,55));
        choiceBox.setPrefSize(190,35);
        pane4.setSpacing(25);
        pane4.setPadding(new Insets(30,0,0,175));


        if(User.user.isAdmin()){
            pane3.getChildren().addAll(back, add_hall, remove_hall, choiceBox, ok);
            pane.getChildren().addAll(pane1, pane2, pane3);
        }
        else{
            pane4.getChildren().addAll(back, choiceBox, ok);
            pane.getChildren().addAll(pane1, pane2, pane4);
        }

        Scene scene = new Scene(pane, 825, 560);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);




        ok.setOnAction(event -> {
            try{
                String hallName = choiceBox.getSelectionModel().getSelectedItem();
                if(!hallName.isEmpty()){
                    for(Object ob : halls){
                        Hall hall = (Hall) ob;
                        if(hall.getHallName().equals(hallName)){
                            Hall.hall = hall;
                        }
                    }
                    if(stop.getAccessibleText().equals("||")) {
                        stop.setAccessibleText(">");
                        video.pause();
                    }
                    hallScene(window);
                }
            }catch (Exception ignored){}
        });
        back.setOnAction(event -> {
            welcomeScene(window);
            if(stop.getAccessibleText().equals("||")) {
                stop.setAccessibleText(">");
                video.pause();
            }

        });
        remove_hall.setOnAction(event -> {
            if(choiceBox.getItems().size() > 0){
                removeHall(window);
                if(stop.getAccessibleText().equals("||")) {
                    stop.setAccessibleText(">");
                    video.pause();
                }
            }
        });
        add_hall.setOnAction(event -> {
            String hallName = choiceBox.getSelectionModel().getSelectedItem();
            for(Object ob : halls){
                Hall hall = (Hall) ob;
                if(hall.getHallName().equals(hallName)){
                    Hall.hall = hall;
                }
            }
            addHall(window);
            if(stop.getAccessibleText().equals("||")) {
                stop.setAccessibleText(">");
                video.pause();
            }
        });
        stop.setOnAction(event -> {
            if (stop.getAccessibleText().equals(">")){
                stop.setAccessibleText("||");
                stop.setGraphic(pla);
                video.play();
            }
            else {
                stop.setAccessibleText(">");
                stop.setGraphic(sto);
                video.pause();
            }
        });
        backward.setOnAction(event -> video.seek(video.getCurrentTime().add(seconds(-5))));
        forward.setOnAction(event -> video.seek(video.getCurrentTime().add(seconds(5))));
        beginning.setOnAction(event -> video.seek(ZERO));


    }
    public static void hallScene(Stage window){
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox();

        Image img, img2, img3;
        Label welcomeText, informationText, buyText;
        Button back, finish;
        ArrayList<Object> seats = Main.data.get(3);
        ArrayList<Object> users = Main.data.get(0);
        Seat[][] hallSeats = new Seat[10][10];


        img = new Image(new File("assets\\icons\\empty_seat.png").toURI().toString());
        img2 = new Image(new File("assets\\icons\\reserved_seat.png").toURI().toString());
        img3 = new Image(new File("assets\\icons\\checked_seat.png").toURI().toString());
        welcomeText = new Label(Film.film.getName() + "(" + Film.film.getDuration() + "minutes) " +
                "Hall: " + Hall.hall.getHallName());
        back = new Button("<BACK");
        finish = new Button("FINISH");
        informationText = new Label();
        buyText = new Label();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (Object ob2: users){
            User user = (User) ob2;
            choiceBox.getItems().add(user.getUserName());
        }
        User value = (User) users.get(0);
        choiceBox.setValue(value.getUserName());

        welcomeText.setFont(font);
        informationText.setFont(font_normal);
        buyText.setFont(font_normal);
        Styles.button(back);
        Styles.button(finish);

        pane1.getChildren().add(welcomeText);
        pane2.getChildren().add(choiceBox);
        pane3.getChildren().add(informationText);
        pane4.getChildren().add(buyText);
        if (!User.user.isAdmin()){
            pane5.getChildren().addAll(back, finish);
            pane5.setSpacing(25);
        }
        else{
            pane5.getChildren().addAll(back);
        }


        vBox.getChildren().add(pane1);

        for (Object ob : seats){
            Seat seat = (Seat) ob;
            if (seat.getHallName().equals(Hall.hall.getHallName())) {
                hallSeats[seat.getRow()][seat.getCol()] = seat;
            }
        }
        for(Seat[] seatLine: hallSeats){
            HBox line = new HBox();
            line.setAlignment(Pos.CENTER);
            line.setSpacing(10);
            for (Seat seat: seatLine){
                if(seat == null)break;
                Button seatButton = new Button();
                line.getChildren().add(seatButton);
                ImageView emptySeat = new ImageView(img);
                ImageView reservedSeat = new ImageView(img2);
                ImageView checkedSeat = new ImageView(img3);
                emptySeat.setScaleX(0.09);
                emptySeat.setScaleY(0.09);
                reservedSeat.setScaleX(0.09);
                reservedSeat.setScaleY(0.09);
                checkedSeat.setScaleX(0.09);
                checkedSeat.setScaleY(0.09);
                seatButton.setMaxWidth(55);
                seatButton.setMinWidth(55);
                seatButton.setMaxHeight(55);
                seatButton.setMinHeight(55);
                if (seat.getOwner() == null){
                    seatButton.setGraphic(emptySeat);
                }
                else{
                    seatButton.setGraphic(reservedSeat);
                    if(!User.user.isAdmin() && !seat.getOwner().equals(User.user)){
                        seatButton.setDisable(true);
                    }
                }

                seatButton.setAccessibleText(seat.getRow() + " " + seat.getCol() + " " + seat.getOwnerS() + " " + seat.getPrice());
                seatButton.setOnMouseEntered(event -> {
                    if (User.user.isAdmin()){
                        User receiver = null;
                        String nametext =  choiceBox.getSelectionModel().getSelectedItem();
                        for (Object ob3: users){
                            User user = (User) ob3;
                            if(user.getUserName().equals(nametext)){
                                receiver = user;
                            }
                        }
                        String[] text = seatButton.getAccessibleText().split(" ");
                        if (text[2].equals("null")){
                            informationText.setText("Not bought yet!");
                        }
                        else{
                            informationText.setText("Bought by " + text[2] + " for " + text[3] + " TL!");
                        }
                    }
                });
                seatButton.setOnAction(event -> {
                    String[] text = seatButton.getAccessibleText().split(" ");
                    User receiver = User.user;
                    String nametext =  choiceBox.getSelectionModel().getSelectedItem();
                    for(Object ob1: Main.data.get(3)) {
                        Seat seat1 = (Seat) ob1;
                        if (Hall.hall.equals(seat1.getHall()) && seat1.getRow() == Integer.parseInt(text[0])
                                && seat1.getCol() == Integer.parseInt(text[1])) {
                            if (!text[2].equals("null") || (seatButton.getGraphic() == checkedSeat)) {
                                seat1.setOwner(null);
                                seat1.setOwnerS("null");
                                seat1.setPrice(0);
                                if (User.user.isAdmin()){
                                    buyText.setText("Seat at " + (Integer.parseInt(text[0]) + 1) + "-" + (Integer.parseInt(text[1]) + 1) + " is refunded successfully!");
                                }
                                else{
                                    selSeat.remove(seat1);
                                    if (seatButton.getGraphic() == checkedSeat){
                                        buyText.setText("Seat at " + (Integer.parseInt(text[0]) + 1) + "-" + (Integer.parseInt(text[1]) + 1) + " is out of your seat list!");
                                    }
                                    else{
                                        buyText.setText("Seat at " + (Integer.parseInt(text[0]) + 1) + "-" + (Integer.parseInt(text[1]) + 1) + " is refunded successfully!");                                    }
                                }
                                seatButton.setGraphic(emptySeat);
                                seatButton.setAccessibleText(seat1.getRow() + " " + seat1.getCol() + " " + seat1.getOwnerS() + " " + seat1.getPrice() );
                                break;
                            }
                            if (User.user.isAdmin()){
                                for (Object ob3 : users) {
                                    User user = (User) ob3;
                                    if (user.getUserName().equals(nametext)) {
                                        receiver = user;
                                    }
                                }
                            }
                            else{
                                receiver = User.user;
                                selSeat.add(seat1);
                            }
                            int price = receiver.isClubMember() ? Hall.hall.getDiscountPrice() : Hall.hall.getSeatPrice();
                            if (User.user.isAdmin()){
                                seatButton.setGraphic(reservedSeat);
                                buyText.setText("Seat at " + (Integer.parseInt(text[0]) + 1) + "-" + (Integer.parseInt(text[1]) + 1) + " is bought for " + receiver.getUserName() + " for " + price + " TL successfully!");
                                seat1.setOwner(receiver);
                                seat1.setOwnerS(receiver.getUserName());
                                seat1.setPrice(price);
                                seatButton.setAccessibleText(seat1.getRow() + " " + seat1.getCol() + " " + seat1.getOwnerS() + " " + price);
                            }
                            else{
                                seatButton.setGraphic(checkedSeat);
                                buyText.setText("Seat at " + (Integer.parseInt(text[0]) + 1) + "-" + (Integer.parseInt(text[1]) + 1) + " is selected " + "for " + price + " TL successfully!");
                            }
                            break;
                        }
                    }
                });
            }
            vBox.getChildren().add(line);
    }
        vBox.setMinSize(450,450);
        vBox.setPadding(new Insets(20,40,20,40));
        pane1.setPadding(new Insets(0,0,15,0));
        pane2.setPadding(new Insets(15,0,15,0));
        pane3.setPadding(new Insets(0,0,15,0));
        pane4.setPadding(new Insets(0,0,15,0));
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);
        pane4.setAlignment(Pos.CENTER);
        pane5.setAlignment(Pos.CENTER);
        welcomeText.setAlignment(Pos.CENTER);

        if( User.user.isAdmin()){
            vBox.getChildren().addAll(pane2, pane3, pane4, pane5);
        }else{
            vBox.getChildren().addAll(pane3, pane4, pane5);
        }

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.setResizable(true);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        back.setOnAction(event -> {
            selSeat.clear();
            filmWindow(window);
        });
        finish.setOnAction(event -> {
            if(selSeat.size() == 0){
                FileOperations.playError();
                AlertBox.display("ERROR", "Please select the seats you want to buy!");
            }
            else payment(window, selSeat);
        });


    }
    public static void addFilm(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox(), pane6 = new HBox();

        Label topLabel, username, path, duration, error;
        Button back, ok;
        TextField textname, textpath, textduration;


        back = new Button("< BACK");
        ok = new Button("OK");
        topLabel = new Label("Please give name, relative path of the trailer and duration of the film.");
        username = new Label("Name:");
        path = new Label("Trailer (Path):");
        duration = new Label("Duration (m):");
        error = new Label();
        textname = new TextField();
        textpath = new TextField();
        textduration = new TextField();

        textname.setPrefWidth(300);
        textpath.setPrefWidth(300);
        textduration.setPrefWidth(300);

        ok.setDefaultButton(true);
        Styles.button(back);
        Styles.button(ok);
        topLabel.setFont(font);
        username.setFont(font_normal);
        path.setFont(font_normal);
        duration.setFont(font_normal);
        error.setFont(font_normal);


        vBox.setPadding(new Insets(15, 15, 15, 0));
        pane1.setPadding(new Insets(25,0,40,80));
        pane2.setPadding(new Insets(0,0,15,50));
        pane2.setSpacing(164);
        pane3.setPadding(new Insets(0,0,15,50));
        pane3.setSpacing(118);
        pane4.setPadding(new Insets(0,0,30,50));
        pane4.setSpacing(120);
        pane5.setPadding(new Insets(0,0,20,0));
        pane5.setSpacing(275);

        pane1.getChildren().add(topLabel);
        pane2.getChildren().addAll(username, textname);
        pane3.getChildren().addAll(path, textpath);
        pane4.getChildren().addAll(duration, textduration);
        pane5.getChildren().addAll(back, ok);
        pane6.getChildren().addAll(error);

        pane1.setAlignment(Pos.CENTER_LEFT);
        pane2.setAlignment(Pos.CENTER_LEFT);
        pane3.setAlignment(Pos.CENTER_LEFT);
        pane4.setAlignment(Pos.CENTER_LEFT);
        pane5.setAlignment(Pos.CENTER);
        pane6.setAlignment(Pos.CENTER);


        vBox.getChildren().addAll(pane1, pane2, pane3, pane4, pane5, pane6);
        Scene newScene = new Scene(vBox, 600, 330);
        window.setScene(newScene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - newScene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - newScene.getHeight ()) / 2);


        back.setOnAction(event -> welcomeScene(window));
        ok.setOnAction(event -> {
            String name = textname.getText();
            String paths = textpath.getText();
            String durations = textduration.getText();
            int cs;
            try{
                cs = Controller.addCheck(name, paths, durations);
            }catch (Exception e){
                cs  = -1;
            }

            switch (cs){
                case 0: // username cannot be empty
                    error.setText("ERROR: Name cannot be empty!");
                    FileOperations.playError();
                    break;
                case 1: // passwords empty
                    error.setText("ERROR: Trailer Path cannot be empty!");
                    FileOperations.playError();
                    break;
                case 2: // password dont match
                    error.setText("ERROR: Duration must be positive!");
                    FileOperations.playError();
                    break;
                case 3: // user already exist
                    error.setText("ERROR: File does not exists!");
                    FileOperations.playError();
                    break;
                case 4: // user already exist
                    error.setText("ERROR: This Film already exists!");
                    FileOperations.playError();
                    break;
                case 5: // user already exist
                    FileOperations.playSuccess();
                    welcomeScene(window);
                    break;
                case 6: // user already exist
                    FileOperations.playError();
                    error.setText("ERROR: Duration Must be Integer!");
                    break;
                case -1: // user has been succesfully signed up
                    FileOperations.playError();
                    error.setText("ERROR: Duration cannot be empty!");
                    break;
            }
        });
    }
    public static void removeFilm(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox();
        Button back, ok;
        Label welcomeText;
        //vBox.setBackground(bg);


        welcomeText = new Label("Select the film that you desire to remove and then click OK.");
        back = new Button("< BACK");
        ok = new Button("OK");

        welcomeText.setFont(font);
        Styles.button(back);
        Styles.button(ok);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<Object> films = Main.data.get(1);
        for (Object ob: films){
            Film film = (Film) ob;
            choiceBox.getItems().add(film.getName());
        }try {
            Film value = (Film) films.get(0);
            choiceBox.setValue(value.getName());
            choiceBox.setPrefSize(400, 50);
        }catch (Exception ignored){}


        vBox.setPadding(new Insets(15, 15, 15, 15));
        pane1.setPadding(new Insets(25,0,40,15  ));
        pane2.setPadding(new Insets(0,0,40,15));
        pane1.getChildren().add(welcomeText);
        pane2.getChildren().add(choiceBox);
        pane3.getChildren().addAll(back, ok);
        pane3.setSpacing(200);
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(pane1, pane2, pane3);
        Scene scene = new Scene(vBox, 600, 330);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        ok.setOnAction(event -> {
            String item = choiceBox.getSelectionModel().getSelectedItem();
            for (Object ob: Main.data.get(1)){
                Film film = (Film) ob;
                if (film.getName().equals(item)){
                    ArrayList<Object> seats = new ArrayList<>(Main.data.get(3));
                    for(Object ob2: seats){
                        Seat seat = (Seat) ob2;
                        if (Objects.equals(seat.getFilmName(), film.getName())){
                            Main.data.get(3).remove(seat);
                        }
                    }
                    ArrayList<Object> halls = new ArrayList<>(Main.data.get(2));
                    for(Object ob3: halls){
                        Hall hall = (Hall) ob3;
                        if (Objects.equals(hall.getFilmName(), film.getName())){
                            Main.data.get(2).remove(hall);
                        }
                    }
                    Main.data.get(1).remove(film);
                    FileOperations.playSuccess();
                    welcomeScene(window);
                    break;
                }

            }
        });
        back.setOnAction(event -> welcomeScene(window));
    }
    public static void editUsers(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox();

        Button back, clubMember, admin;
        TableView<User> tableView;
        TableColumn<User, String> nameColumn, isAdmin, club_member;

        // after login in or signing in it should send the user as a message to here and use that user to get the info
        back = new Button("<BACK");
        clubMember = new Button("Promote/Demote Club Member");
        admin = new Button("Promote/Demote Admin");
        tableView = new TableView<>();

        Styles.button(back,50);
        Styles.button(clubMember, 10);
        Styles.button(admin, 10);


        nameColumn = new TableColumn<>("Username");
        isAdmin = new TableColumn<>("Admin");
        club_member = new TableColumn<>("Club Member");

        nameColumn.setMinWidth(200);
        isAdmin.setMinWidth(150);
        club_member.setMinWidth(150);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        isAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        club_member.setCellValueFactory(new PropertyValueFactory<>("clubMember"));

        tableView.setItems(getUser());
        tableView.getColumns().addAll(nameColumn, club_member, isAdmin);
        tableView.getSelectionModel().select(0);



        vBox.setPadding(new Insets(20,20,20,20));
        pane1.setPadding(new Insets(0,0,20,0));
        pane1.getChildren().add(tableView);
        pane2.getChildren().addAll(back, clubMember, admin);
        pane2.setSpacing(10);

        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);


        vBox.getChildren().addAll(pane1, pane2);
        Scene scene = new Scene(vBox, 650, 400);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        back.setOnAction(event -> welcomeScene(window));
        clubMember.setOnAction(event -> {
            User user;
            user = tableView.getSelectionModel().getSelectedItem();
            user.setClubMember(!user.isClubMember());
            tableView.refresh();
        });
        admin.setOnAction(event -> {
            User user;
            user = tableView.getSelectionModel().getSelectedItem();
            user.setAdmin(!user.isAdmin());
            tableView.refresh();
        });
    }
    public static void addHall(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox(), pane6 = new HBox(), pane7 = new HBox();
        //vBox.setBackground(bg);

        Button back, ok;
        Label welcomeText, row, col, name, price, error;
        TextField textName, textPrice;
        ChoiceBox<Integer> choiceBox, choiceBox2;

        // after login in or signing in it should send the user as a message to here and use that user to get the info
        welcomeText = new Label(Film.film.getName() + "(" + Film.film.getDuration() + " minutes)");
        row = new Label("Row:");
        col = new Label("Column:");
        name = new Label("Name:");
        price = new Label("Price:");
        error = new Label();

        textName = new TextField();
        textPrice = new TextField();

        back = new Button("<BACK");
        ok = new Button("OK");


        ok.setDefaultButton(true);
        Styles.button(back);
        Styles.button(ok);
        row.setFont(font);
        col.setFont(font);
        name.setFont(font);
        price.setFont(font);
        error.setFont(font);
        welcomeText.setFont(font_normal);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(3,4,5,6,7,8,9,10);
        choiceBox.setValue(3);
        choiceBox2 = new ChoiceBox<>();
        choiceBox2.getItems().addAll(3,4,5,6,7,8,9,10);
        choiceBox2.setValue(3);

        pane1.getChildren().add(welcomeText);
        pane2.getChildren().addAll(row, choiceBox);
        pane3.getChildren().addAll(col, choiceBox2);
        pane4.getChildren().addAll(name, textName);
        pane5.getChildren().addAll(price, textPrice);
        pane6.getChildren().addAll(back, ok);
        pane7.getChildren().add(error);
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER_LEFT);
        pane3.setAlignment(Pos.CENTER_LEFT);
        pane4.setAlignment(Pos.CENTER_LEFT);
        pane5.setAlignment(Pos.CENTER_LEFT);
        pane6.setAlignment(Pos.CENTER_LEFT);
        pane7.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(25,25,25,140));
        pane2.setPadding(new Insets(25,0,10,0));
        pane3.setPadding(new Insets(0,0,10,0));
        pane4.setPadding(new Insets(0,0,10,0));
        pane5.setPadding(new Insets(0,0,15,0));
        pane6.setPadding(new Insets(0,0,15,0));
        pane7.setPadding(new Insets(0,90,0,0));
        pane1.setPadding(new Insets(0,90,0,0));
        pane6.setSpacing(240);
        pane2.setSpacing(161);
        pane3.setSpacing(140);
        pane4.setSpacing(154);
        pane5.setSpacing(157);

        vBox.getChildren().addAll(pane1, pane2, pane3, pane4, pane5, pane6, pane7);

        ok.setOnAction(event ->{
            String nameS = (textName.getText());
            String priceS = (textPrice.getText());
            int row1 = choiceBox.getSelectionModel().getSelectedItem();
            int col1 = choiceBox.getSelectionModel().getSelectedItem();
            int cs = Controller.hallCheck(nameS, priceS,row1, col1);
            switch (cs){
                case 0:
                    error.setText("ERROR: Hall name could not be empty!");
                    break;
                case 1:
                    error.setText("ERROR: Seat price could not be empty!");
                    break;
                case 2:
                    error.setText("ERROR: Hall name must be unique from other halls and films!");
                    break;
                case 3:
                    error.setText("ERROR: Price must be positive number!");
                    break;
                case 4:
                    FileOperations.playSuccess();
                    error.setText("SUCCESS: Hall sucessfully created!");
                    break;
                case -1:
                    FileOperations.playError();
                    error.setText("ERROR: Something went wrong");
                    break;

            }
        } );
        back.setOnAction(event -> filmWindow(window));

        Scene scene = new Scene(vBox, 650, 300);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

    }
    public static void removeHall(Stage window){
        window.setResizable(false);
        VBox vBox = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox();
        //vBox.setBackground(bg);

        Button back, ok;
        Label welcomeText;

        welcomeText = new Label("Select the hall you desire to remove from \n"+ Film.film.getName() +" and then click OK.");
        back = new Button("<BACK");
        ok = new Button("OK");

        Styles.button(back);
        Styles.button(ok);
        welcomeText.setFont(font);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<Object> halls = Main.data.get(2);
        ArrayList<Hall> filmhalls = new ArrayList<>();
        for (Object ob: halls){
            Hall hall = (Hall) ob;
            if (hall.getFilmName().equals(Film.film.getName())){
                choiceBox.getItems().add(hall.getHallName());
                filmhalls.add(hall);
            }
        }
        Hall value =  filmhalls.get(0);
        choiceBox.setValue(value.getHallName());

        choiceBox.setPrefSize(400, 50);

        pane1.getChildren().add(welcomeText);
        pane2.getChildren().add(choiceBox);
        pane3.getChildren().addAll(back, ok);
        vBox.setPadding(new Insets(20,20,20,20));
        pane1.setPadding(new Insets(0,0,15,0));
        pane2.setPadding(new Insets(0,0,15,0));
        pane3.setPadding(new Insets(0,0,15,0));
        pane3.setSpacing(300);
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(pane1, pane2, pane3);
        Scene scene = new Scene(vBox, 600, 200);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        ok.setOnAction(event -> {
            String item = choiceBox.getSelectionModel().getSelectedItem();
            ArrayList<Object> seats = new ArrayList<>(Main.data.get(3));
            Hall hall = null;
            for(Object ob2: seats){
                Seat seat = (Seat) ob2;
                if (Objects.equals(seat.getHallName(), item)){
                    hall = seat.getHall();
                    Main.data.get(3).remove(seat);
                }
            }
            Main.data.get(2).remove(hall);
            FileOperations.playSuccess();
            filmWindow(window);
        });
        back.setOnAction(event -> filmWindow(window));
    }
    public static void payment(Stage window, ArrayList<Seat> seats){
        window.setResizable(false);
        VBox vBox = new VBox(), infoBox  = new VBox(), pane15 = new VBox(), pane16 = new VBox();
        HBox pane1 = new HBox(), pane2 = new HBox(), pane3 = new HBox(), pane4 = new HBox(), pane5 = new HBox()
                , pane6 = new HBox(), pane7 = new HBox(), pane8 = new HBox(), pane9 = new HBox()
                , pane10 = new HBox(), pane11 = new HBox(), pane12 = new HBox(), pane13 = new HBox(), pane14 = new HBox()
                , pane17 = new HBox(), pane18 = new HBox();
        Button back, ok;
        Label welcomeText, seatLabel, priceLabel, addres, nameSurname, phone, card, expDate, secCode, cardName, persInfo, cardInfo, error;
        TextField  nameSurnameT, phoneT, cardT, secCodeT, cardNameT, expDateT;
        TextArea addresT;

        welcomeText = new Label("Payment Page");
        persInfo = new Label("Personal Information");
        cardInfo = new Label("Card Information");
        back = new Button("<BACK");
        ok = new Button("Pay");
        seatLabel = new Label();
        priceLabel = new Label();
        addres = new Label("Address");
        nameSurname = new Label("First & Last name");
        phone = new Label("Telephone number");
        card = new Label("Card number");
        expDate = new Label("Expiration Date");
        secCode = new Label("Security Code");
        error = new Label();
        cardName = new Label("Card Owner");
        RadioButton radioButton = new RadioButton("Pay from user account");

        Line line1 = new Line(10, 50, 450, 50);
        Line line2 = new Line(10, 250, 450, 250);

        addresT = new TextArea();
        nameSurnameT = new TextField();
        phoneT = new TextField();
        cardT = new TextField();
        secCodeT = new TextField();
        cardNameT = new TextField();
        expDateT = new TextField();

        selSeat.sort(Comparator.comparing(a -> a.getRow() + a.getCol()));
        ok.setDefaultButton(true);
        error.setFont(font_normal);
        radioButton.setFont(font_normal);
        expDateT.setPromptText("MM/YY");
        secCodeT.setPromptText("CVV");
        phoneT.setPromptText("(0___) _______");
        nameSurnameT.setPrefWidth(450);
        cardT.setPrefWidth(450);
        secCodeT.setPrefWidth(220);
        expDateT.setPrefWidth(200);
        phoneT.setPrefWidth(450);
        cardNameT.setPrefWidth(450);
        addresT.setPrefSize(450,80);
        addresT.setWrapText(true);

        Styles.button(back);
        Styles.button(ok);
        welcomeText.setFont(font);
        persInfo.setFont(font_big);
        cardInfo.setFont(font_big);
        seatLabel.setFont(font_normal);
        priceLabel.setFont(font_normal);
        addres.setFont(font_normal);
        nameSurname.setFont(font_normal);
        phone.setFont(font_normal);
        card.setFont(font_normal);
        expDate.setFont(font_normal);
        secCode.setFont(font_normal);
        cardName.setFont(font_normal);

        String seatNo = "";
        int price = User.user.isClubMember() ? Hall.hall.getDiscountPrice() : Hall.hall.getSeatPrice();
        price = price * seats.size();
        for (Seat seat: seats){
            seatNo += " " + (seat.getRow()+1) + "-" + (seat.getCol()+1) + ",";
        }
        seatNo = seatNo.substring(0,seatNo.length()-1);

        seatLabel.setText("Seats :" + seatNo);
        priceLabel.setText("Total :" + price);

        pane1.getChildren().add(welcomeText);
        pane2.getChildren().add(nameSurname);
        pane3.getChildren().add(nameSurnameT);
        pane4.getChildren().add(addres);
        pane5.getChildren().add(addresT);
        pane6.getChildren().add(phone);
        pane7.getChildren().add(phoneT);
        pane8.getChildren().add(card);
        pane9.getChildren().add(cardT);
        pane10.getChildren().addAll(expDate, secCode);
        pane11.getChildren().addAll(expDateT, secCodeT);
        pane12.getChildren().addAll(cardName);
        pane13.getChildren().addAll(cardNameT);
        pane14.getChildren().addAll(back, ok);
        pane15.getChildren().addAll(persInfo, line1);
        pane16.getChildren().addAll(cardInfo, line2);
        pane17.getChildren().add(radioButton);
        pane18.getChildren().add(error);
        infoBox.getChildren().addAll(seatLabel, priceLabel);
        vBox.setPadding(new Insets(20,20,20,20));
        pane1.setPadding(new Insets(0,0,15,0));
        pane2.setPadding(new Insets(0,0,5,0));
        pane3.setPadding(new Insets(0,0,15,0));
        pane4.setPadding(new Insets(0,0,5,0));
        pane5.setPadding(new Insets(0,0,15,0));
        pane6.setPadding(new Insets(0,0,5,0));
        pane7.setPadding(new Insets(0,0,15,0));
        pane8.setPadding(new Insets(0,0,5,0));
        pane9.setPadding(new Insets(0,0,15,0));
        pane10.setPadding(new Insets(0,0,5,0));
        pane11.setPadding(new Insets(0,0,15,0));
        pane12.setPadding(new Insets(0,0,5,0));
        pane13.setPadding(new Insets(0,0,15,0));
        pane14.setPadding(new Insets(10,0,0,0));
        pane15.setPadding(new Insets(5,0,5,0));
        pane16.setPadding(new Insets(5,0,5,0));
        pane17.setPadding(new Insets(0,0,15,0));
        pane18.setPadding(new Insets(0,0,7,0));
        pane14.setSpacing(300);
        pane10.setSpacing(135);
        pane11.setSpacing(30);

        pane1.setAlignment(Pos.CENTER);
        pane15.setAlignment(Pos.CENTER_LEFT);
        pane16.setAlignment(Pos.CENTER_LEFT);
        pane18.setAlignment(Pos.CENTER);


        vBox.getChildren().addAll(pane1, pane15, pane2, pane3, pane4, pane5, pane6, pane7, pane16, pane8, pane9, pane10, pane11, pane12, pane13, pane17, infoBox,pane18, pane14);
        Scene scene = new Scene(vBox, 500, 700);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        ok.setOnAction(event -> {
            String nameS = nameSurnameT.getText();
            String addresS = addresT.getText();
            String phoneS = phoneT.getText();
            if (radioButton.isSelected()){
                if (Controller.ticketCheck(nameS, addresS, phoneS, error)){
                    FileOperations.playSuccess();
                    AlertBox.display("Congratulations", "You have successfully bought your tickets");
                    int priceS = User.user.isClubMember() ? Hall.hall.getDiscountPrice() : Hall.hall.getSeatPrice();
                    for(Seat seat1:selSeat){
                        seat1.setOwner(User.user);
                        seat1.setOwnerS(User.user.getUserName());
                        seat1.setPrice(priceS);
                    }
                    selSeat.clear();
                    hallScene(window);
                }
                else{
                    FileOperations.playError();
                }
            }
            else {
                String cardS = cardT.getText();
                String expT = expDateT.getText();
                String secT = secCodeT.getText();
                String cardownerT = cardNameT.getText();
                if (Controller.ticketCheck(nameS, addresS, phoneS, cardS, expT, secT, cardownerT, error)){
                    FileOperations.playSuccess();
                    AlertBox.display("Congratulations", "You have successfully bought your tickets");
                    int priceS = User.user.isClubMember() ? Hall.hall.getDiscountPrice() : Hall.hall.getSeatPrice();
                    for(Seat seat1:selSeat){
                        seat1.setOwner(User.user);
                        seat1.setOwnerS(User.user.getUserName());
                        seat1.setPrice(priceS);
                    }
                    hallScene(window);
                }
                else{
                    FileOperations.playError();
                }
            }
        });
        back.setOnAction(event -> {
            selSeat.clear();
            hallScene(window);
        });
    }

    public static ObservableList<User> getUser()    {
        ObservableList<User> users = FXCollections.observableArrayList();
        ArrayList<Object> data_users = Main.data.get(0);
        for(Object ob: data_users){
            User user = (User) ob;
            users.addAll(user);
        }
        return users;
    }
} // end of class

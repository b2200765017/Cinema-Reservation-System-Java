import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class FileOperations {
    static File audio = new File("assets\\effects\\error.mp3");
    static Media media = new Media(audio.toURI().toString());
    static MediaPlayer error = new MediaPlayer(media);

    static File log = new File("assets\\effects\\login.wav");
    static Media logd = new Media(log.toURI().toString());
    static MediaPlayer login = new MediaPlayer(logd);

    static File err = new File("assets\\effects\\error2.mp3");
    static Media err2 = new Media(err.toURI().toString());
    static MediaPlayer error2 = new MediaPlayer(err2);


    static File basari = new File("assets\\effects\\success.mp3");
    static Media suc = new Media(basari.toURI().toString());
    static MediaPlayer success = new MediaPlayer(suc);



    public static Properties properties() {
        // opens properties.dat file and takes the information inside.
        Reader reader = null;
        try {
            reader = new FileReader("assets\\data\\properties.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    public static ArrayList<ArrayList<Object>> initialize(){

        ArrayList<ArrayList<Object>> data = new ArrayList<>();

        ArrayList<Object> users = new ArrayList<>();
        ArrayList<Object> films = new ArrayList<>();
        ArrayList<Object> halls = new ArrayList<>();
        ArrayList<Object> seats = new ArrayList<>();

        try {
            FileReader reader = new FileReader("assets\\data\\backup.dat");
        Scanner sc = new Scanner(reader);

        while (sc.hasNext()){
            String[] line = sc.nextLine().trim().split("\t");
            switch (line[0]){
                case "user":
                    boolean isAdmin = line[4].equals("true");
                    boolean clubMember = line[3].equals("true");
                    users.add(new User(line[1], line[2], clubMember, isAdmin));
                    break;
                case "film":
                    films.add(new Film(Integer.parseInt(line[3]), line[1], line[2]));
                    break;
                case "hall":
                    halls.add(new Hall(line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                    break;
                case "seat":
                    seats.add(new Seat(line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), line[5], Integer.parseInt(line[6]), halls, users));
                    break;
            }
        }
        } catch (FileNotFoundException e) {
            users.add(new User("admin", "X03MO1qnZdYdgyfeuILPmQ==", true, true));
        }
        data.add(users);
        data.add(films);
        data.add(halls);
        data.add(seats);
        return data;
    }

    public static void playError(){ // i had an issue playing a media sound
        error.seek(Duration.ZERO);
        error.play();
    }
    public static void playSuccess(){ // i had an issue playing a media sound
        success.seek(Duration.ZERO);
        success.play();
    }
    public static void playLogin(){ // i had an issue playing a media sound
        login.seek(Duration.ZERO);
        login.play();
    }
    public static void playBan(){ // i had an issue playing a media sound
        error2.seek(Duration.ZERO);
        error2.play();
    }

} // end of class

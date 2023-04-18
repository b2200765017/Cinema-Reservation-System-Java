import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    public static int signUp(String username, String password, String password2) {
        if (username.isEmpty()) {
            return 0;
        } else if (password.isEmpty() | password2.isEmpty()) {
            return 1;
        } else if (!password.equals(password2)) {
            return 2;
        } else {
            ArrayList<Object> users = Main.data.get(0);
            for (Object ob : users) {
                User user = (User) ob;
                if (user.getUserName().equals(username)) {
                    return 3;
                }
            }
            User.user = new User(username, password);
            Main.data.get(0).add(User.user);
        }
        return -1;
    }
    public static int addCheck(String name, String path, String duration) {
        String path2 = path;
        int dur;
        if (name.isEmpty()) {
            FileOperations.playError();
            return 0;
        }
        else if (path.isEmpty()) {
            FileOperations.playError();
            return 1;
        }
        try {
            dur = Integer.parseInt(duration);
        } catch (Exception e) {
            return 6;
        }
        if (dur <= 0) {
            FileOperations.playError();
            return 2;
        } else {
            path = "assets\\trailers\\" + path;
            File tempFile = new File(path);
            if (!tempFile.isFile()) {
                FileOperations.playError();
                return 3; // the file doesnt exist;
            } else {
                ArrayList<Object> films = Main.data.get(1);
                for (Object ob : films) {
                    Film film = (Film) ob;
                    if (film.getName().equals(name) | film.getDuration() == dur) {
                        FileOperations.playError();
                        return 4;
                    } else if (!film.getName().equals(name)) {
                        Film.film = film;
                        Main.data.get(1).add(new Film(dur, name, path2));
                        return 5;
                    }
                }
                if (films.size() == 0){
                    Main.data.get(1).add(new Film(dur, name, path2));
                    return 5;
                }
            }
        }
        return -1;
    }
    public static int hallCheck(String name, String price, int row, int col) {
        ArrayList<Object> films = Main.data.get(1);
        ArrayList<Object> halls = Main.data.get(2);
        ArrayList<Object> seats = Main.data.get(3);
        if (name.isEmpty()) {
            FileOperations.playError();
            return 0;
        } else if (price.isEmpty()) {
            FileOperations.playError();
            return 1;
        } else {
            for (Object ob : films) {
                Film film = (Film) ob;
                if (film.getName().equals(name)) {
                    return 2;
                }
            }
            for (Object ob : halls) {
                Hall hall = (Hall) ob;
                if (hall.getHallName().equals(name)) {
                    return 2;
                }
            }
            try {
                if (Integer.parseInt(price) < 0) {
                    return 3;
                }
                else {
                    Hall.hall = new Hall(Film.film.getName(), name, Integer.parseInt(price), row, col);
                    halls.add(Hall.hall);
                    for (int i = 0; i < row; i++){
                        for (int j = 0; j < col; j++){
                            seats.add(new Seat(Film.film.getName(), Hall.hall.getHallName(), i, j, "null", Integer.parseInt(price), Main.data.get(2), Main.data.get(0)));
                        }
                    }
                    return 4;
                }
            }catch (Exception e){
                return -1;
            }
        }

    }
    public static boolean ticketCheck(String nameS, String addresS, String phoneS, Label error) {
        if (nameS.isEmpty()){
            error.setText("ERROR: Name can't be empty!");
            return false;
        }
        else if (addresS.isEmpty()){
            error.setText("ERROR: Address can't be empty!");
            return false;
        }
        else if (phoneS.isEmpty()){
            error.setText("ERROR: Phone number can't be empty!");
            return false;
        }
        else if (phoneS.length() != 11){
            error.setText("ERROR: Incorrect phone number!");
            return false;
        }
        else{
            try{
                Long.parseLong(phoneS);
            }catch (Exception e){
                error.setText("ERROR: Phone number must be Integer!");
                return false;
            }
            return true;
        }
    }
    public static boolean ticketCheck(String nameS, String addresS, String phoneS, String cardS, String expT, String secT, String cardownerT, Label error) {
        boolean no = ticketCheck(nameS, addresS, phoneS,error);
        if (no){
            if (cardS.isEmpty()){
                error.setText("ERROR: Card number can't be empty!");
                return false;
            }
            else if (expT.isEmpty()){
                error.setText("ERROR: Expiration date can't be empty!");
                return false;
            }
            else if (secT.isEmpty()){
                error.setText("ERROR: Security code can't be empty!");
                return false;
            }
            else if (cardownerT.isEmpty()){
                error.setText("ERROR: Card Owner can't be empty!");
                return false;
            }
            else if (cardS.length() != 16){
                error.setText("ERROR: Invalid card number!");
                return false;
            }

            else if (secT.length() != 3){
                error.setText("ERROR: Invalid security number!");
                return false;
            }
            else {
                try{
                    Long.parseLong(cardS);
                }catch (Exception e){
                    error.setText("ERROR: Card number must be Integer!");
                    return false;
                }
                try{
                    String[] cnt = expT.split("/");
                    int month = Integer.parseInt(cnt[0]);
                    int year = Integer.parseInt(cnt[1]);
                    if (month < 0 || month > 12){
                        error.setText("ERROR: Month must be valid");
                        return false;
                    }
                    else if(year < 21 || year > 75) {
                        error.setText("ERROR: Year must be valid");
                        return false;
                    }
                }catch (Exception e){
                    error.setText("ERROR: Wrong Expiration Date!");
                    return false;
                }
                try{
                     Long.parseLong(cardS);
                }catch (Exception e){
                    error.setText("ERROR: Security number must be Integer!");
                    return false;
                }
            }
        }
        else {
            return false;
        }
    return true;
    }
}



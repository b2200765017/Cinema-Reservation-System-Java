import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Main extends Application {
    Stage window;
    static Properties properties = FileOperations.properties();
    static ArrayList<ArrayList<Object>> data = FileOperations.initialize();
    static Image icon = new Image("assets/icons/logo.png");

    public static void main(String[] args) {
        launch(args);
    } // end of main


    @Override
    public void start(Stage stage) {
        String title = properties.getProperty("title");
        window = stage;
        window.setTitle(title);
        window.getIcons().add(icon);

        SceneSetter.firstScene(window);
        window.show();
        window.setOnCloseRequest(e -> closeProgram(data));



    } // end of start

    public void closeProgram( ArrayList<ArrayList<Object>> data){
        try {
            FileWriter fw = new FileWriter("assets\\data\\backup.dat"); //burası properties.dat a çevirilecek en sonunda
            for (ArrayList<Object> list: data){
                for (Object ob : list){
                fw.write(ob.toString());
                }
            }
            fw.close();
            window.close();
        } catch (IOException ignored) {
        }
    }
} // end of class


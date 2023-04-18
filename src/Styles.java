import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Styles {
    static Rectangle button = new Rectangle(0,0,110,110);
    static Font buttonFont = Font.font("Lato", FontWeight.BOLD, 15);

    public static void button(Button bt){
        button.setArcHeight(100);
        button.setArcWidth(30);
        bt.setShape(button);
        bt.setFont(buttonFont);
        bt.setStyle("-fx-background-color: #09aeae; ");
        bt.setTextFill(Color.WHITE);
    }
    public static void button(Button bt, int arcW){
        button.setArcHeight(100);
        button.setArcWidth(arcW);
        bt.setShape(button);
        bt.setFont(buttonFont);
        bt.setStyle("-fx-background-color: #09aeae; ");
        bt.setTextFill(Color.WHITE);
    }
    public static void cButton(Button bt, int r){
        Circle circle = new Circle(r);
        bt.setShape(circle);
        bt.setFont(buttonFont);
        bt.setStyle("-fx-background-color: #09aeae; ");
        bt.setTextFill(Color.WHITE);
        bt.setMinSize(2*r, 2*r);
        bt.setMaxSize(2*r, 2*r);
    }
}

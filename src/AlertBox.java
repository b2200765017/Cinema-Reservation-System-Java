import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class AlertBox {
    public static void display(String title, String text){
        Stage window = new Stage();
        Label label;
        Button button;

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setFullScreen(false);
        window.getIcons().add(Main.icon);
        VBox pane = new VBox();

        label = new Label(text);
        button = new Button("OK");

        pane.setPadding(new Insets(25,25,25,25));
        VBox.setMargin(label, new Insets(0, 0, 0,0));


        label.setFont(SceneSetter.font_normal);
        label.setAlignment(Pos.CENTER);
        pane.setAlignment(Pos.CENTER);

        Styles.button(button);
        button.setAlignment(Pos.CENTER);


        button.setOnAction(event -> window.close());

        pane.getChildren().addAll(label, button);
        Scene scene = new Scene(pane, 400, 50);
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds ();
        window.setX((screenBounds.getWidth() - scene.getWidth ()) / 2);
        window.setY((screenBounds.getHeight () - scene.getHeight ()) / 2);

        window.showAndWait();

    }
}

package sample;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TrafficLight extends Application{

    private static final double RADIUS = 50;
    private static final double PAUSE = 1;
    private Circle  circleRed,  circleYellow,  circleGreen;
    private Color[] colors = {Color.RED, Color.YELLOW, Color.GREEN};
    private int onColor = 0;

    @Override
    public void start(Stage primaryStage) {

        circleRed = new Circle(RADIUS);
        circleRed.setFill(Color.WHITE);
        circleGreen = new Circle(RADIUS);
        circleGreen.setFill(Color.WHITE);
        circleYellow = new Circle(RADIUS);
        circleYellow.setFill(Color.WHITE);

        TilePane light = new TilePane(circleGreen, circleYellow, circleRed);
        light.setPadding( new Insets(20, 20, 20, 20) );
        Scene scene = new Scene(light, RADIUS*8, RADIUS*3);

        primaryStage.setTitle("Traffic Light Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        update();
    }

    private void update() {

        PauseTransition pause = new PauseTransition(Duration.seconds(PAUSE));
        pause.setOnFinished(event ->{
            circleRed.setFill((onColor == 0) ?  colors[onColor] :Color.WHITE );
            circleYellow.setFill((onColor == 1) ? colors[onColor] :Color.WHITE);
            circleGreen.setFill((onColor == 2) ? colors[onColor] :Color.WHITE);
            onColor = ((onColor +1) >= colors.length) ? 0 : onColor+1;

            pause.play();
        });

        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

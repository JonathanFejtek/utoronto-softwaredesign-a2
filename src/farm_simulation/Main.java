package farm_simulation;

import farmyard.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Jon's take on the 'classic' game FarmVille.
 */

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @SuppressWarnings("unused")
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FarmVille");

        // the width of the graphics context
        int frame_width = 1024;
        // the height of the graphics context
        int frame_height = 720;
        // number of columns
        int width_divisions = 128;
        // number of rows
        int height_divisions = 128;

        FarmYard.createInstance(frame_width, frame_height, width_divisions, height_divisions);
        FarmYard farmyard = FarmYard.getInstance();

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(frame_width, frame_height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //set up play area
        new Human(50, 50);
        new Human(67, 70);

        new AnimalFood(80, 80);
        new AnimalFood(110, 80);
        new AnimalFood(70, 40);
        new AnimalFood(54, 68);

        new Chicken(6, 12);
        new Chicken(15, 19);

        new Pig(16, 10);
        new Pig(20, 12);

        new FarmersField(10, 10, 50, 30);
        new FarmersField(65, 10, 50, 30);
        new FarmersField(10, 45, 50, 30);
        new FarmersField(65, 45, 50, 30);


        drawShapes(gc);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.3),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        // move all items stored in farmyard
                        Wind.updateWind();
                        farmyard.updateGame();
                        // Clear the canvas
                        gc.clearRect(0, 0, frame_width, frame_height);

                        drawShapes(gc);
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        primaryStage.show();
    }

    /**
     * Draws all game objects to a specified graphics context
     *
     * @param gc A graphics context to draw to
     */
    private void drawShapes(GraphicsContext gc) {
        FarmYard farmyard = FarmYard.getInstance();
        farmyard.drawToGraphicsContext(gc);
    }
}

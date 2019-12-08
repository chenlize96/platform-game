package model;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class HorizontalMonsterModel extends Application {
	
	public class SpriteAnimation extends Transition {

	    private final ImageView imageView;
	    private final int count;
	    private final int columns;
	    private final int offsetX;
	    private final int offsetY;
	    private final int width;
	    private final int height;
	    private int size = 25;

	    private int lastIndex;

	    public SpriteAnimation(
	            ImageView imageView, 
	            Duration duration, 
	            int count,   int columns,
	            int offsetX, int offsetY,
	            int width,   int height) {
	        this.imageView = imageView;
	        this.count     = count;
	        this.columns   = columns;
	        this.offsetX   = offsetX;
	        this.offsetY   = offsetY;
	        this.width     = width;
	        this.height    = height;
	        setCycleDuration(duration);
	        setInterpolator(Interpolator.LINEAR);
	    }

	    protected void interpolate(double k) {
	        final int index = Math.min((int) Math.floor(k * count), count - 1);
	        if (index != lastIndex) {
	            final int x = (index % columns) * width  + offsetX;
	            final int y = (index / columns) * height + offsetY;
	            imageView.setViewport(new Rectangle2D(x, y, width, height));
	            lastIndex = index;
	        }
	    }
	}

    private static final Image IMAGE = new Image("img/Run.png");

    private static final int COLUMNS  =   3;
    private static final int COUNT    =  8;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 34;
    private static final int HEIGHT   = 24;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}

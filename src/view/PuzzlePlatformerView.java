package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PuzzlePlatformerView extends Application{

	// ATTENTION: for now, we directly use number, we would change them to [public final] later 
	
	@SuppressWarnings({ "rawtypes", "unchecked" }) // do not modify this line
	@Override
	public void start(Stage stage) throws Exception {
		// make menu
		Menu menu = new Menu("File"); 
		MenuItem item1 = new MenuItem("New Game"); // it can handle levels, networking (do later)
		MenuItem item2 = new MenuItem("Save Game"); // save (do later)
		menu.getItems().add(item1);
		menu.getItems().add(item2);
		MenuBar mb = new MenuBar(); mb.setMinHeight(25); 
		mb.getMenus().add(menu); 
		// grid holds map
		GridPane grid = new GridPane();
		grid.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		// info contains hearts, time, bag (use vbox instead of BorderPane)
		VBox info = new VBox();
		info.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		info.setPrefSize(200, 600); // not sure its best size
		Label health = new Label("Health");	
		health.setFont(new Font("Arial", 30));
		Canvas canvas = new Canvas(150, 50); // label.setGraphic(new ImageView()) doesnot work, so use canvas
		// the size of canvas bases on the number of hearts (modify later)
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image heart = new Image("img/heart.png"); 
		gc.drawImage(heart, 0, 0, 50, 50); 
		gc.drawImage(heart, 50, 0, 50, 50); // easy to update hearts by covering a 50*50 grey square (do later)
		gc.drawImage(heart, 100, 0, 50, 50); 
		Label countdown = new Label("Countdown"); // (become red when less than 1 min)
		countdown.setFont(new Font("Arial", 30));
		Label timer = new Label("300 seconds"); // create timer
		timer.setFont(new Font("Arial", 24));
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), new EventHandler() {
					public int timeSeconds = 10; // make it 300 later, now for test
					@Override
					public void handle(Event event) {
						timeSeconds--;
						// update timerLabel
						timer.setText(timeSeconds + " seconds");
						if (timeSeconds <= 5) { // make it 60 later, now for test
							timer.setTextFill(Color.RED);
						}
						if (timeSeconds <= 0) {
							timeline.stop(); // also show alert, and freeze (do later)
						}
					}
				}));
		timeline.playFromStart();

		Label items = new Label("Items"); //
		items.setFont(new Font("Arial", 30));
		// there should be a gridPane holding various images which represent items 
		// Lize will do it later, since I am trying to find the best way to handle items (do later)
		info.getChildren().addAll(health, canvas, countdown, timer, items); 
		info.setAlignment(Pos.BASELINE_CENTER);
		info.setSpacing(20); // POSSIBLE optimize: add separate line for readablity (do later)
		// p holds everything
		BorderPane p = new BorderPane();
		p.setCenter(grid); p.setTop(mb); p.setRight(info);
		// there should be someway to zoom up automatically without influencing coordinates (do later or ignore)
		Scene scene = new Scene(p, 1000, 600); 
		stage.setScene(scene); stage.setTitle("Puzzle Platformer");
		stage.show();
	}
	
}

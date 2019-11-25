import javafx.application.Application;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PuzzlePlatformerView extends Application{

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
		Canvas canvas = new Canvas(200, 50); // label.setGraphic(new ImageView()) doesnot work, so use canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image heart = new Image("img/heart.png"); 
		gc.drawImage(heart, 0, 0, 50, 50);
		Label countdown = new Label("Countdown"); // (become red when less than 1 min)
		countdown.setFont(new Font("Arial", 30));
		Label items = new Label("Items"); //
		items.setFont(new Font("Arial", 30));
		
		
		
		
		
		info.getChildren().addAll(health, canvas, countdown, items); 
		info.setAlignment(Pos.BASELINE_CENTER);
		info.setSpacing(20);
		// p holds everything
		BorderPane p = new BorderPane();
		p.setCenter(grid); p.setTop(mb); p.setRight(info);
		// there should be someway to zoom up automatically without influencing coordinates (do later or ignore)
		Scene scene = new Scene(p, 1000, 600); 
		stage.setScene(scene); stage.setTitle("Puzzle Platformer");
		stage.show();
	}
	
}

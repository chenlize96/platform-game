package view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameMenuView extends Application{
	PuzzlePlatformerView puzzle_view;
	public static void main(String[] args) {
		launch(args);
	}
	private Parent createContent() {
		Pane root = new Pane();
		
		ImageView background = new ImageView(new Image("img/game_background.jpg"));
		

		background.setFitHeight(700);
		background.setFitWidth(1200);
		

		
		ImageView start = new ImageView(new Image("img/game_start.png"));
		start.setFitHeight(50);
		start.setFitWidth(300);
		start.setTranslateX(250);
		start.setTranslateY(230);
		start.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event args) {
				puzzle_view = new PuzzlePlatformerView();
				puzzle_view.initModality(Modality.APPLICATION_MODAL);
				puzzle_view.setResizable(false);
				try {
					puzzle_view.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		ImageView load = new ImageView(new Image("img/game_load.png"));
		load.setFitHeight(50);
		load.setFitWidth(300);
		load.setTranslateX(250);
		load.setTranslateY(320);
		
		ImageView help = new ImageView(new Image("img/game_help.png"));
		help.setFitHeight(50);
		help.setFitWidth(300);
		help.setTranslateX(250);
		help.setTranslateY(410);


		
		root.getChildren().addAll(background,start,load,help);
		
		return root;
	}


	@Override
	public void start(Stage stage) throws Exception {
		Parent root = createContent();
		Scene scene = new Scene(root);
		stage.setTitle("GAME MENU");
		stage.setScene(scene);
		stage.show();
		
	}

}

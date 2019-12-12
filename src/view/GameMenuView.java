package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	@SuppressWarnings("unchecked")
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
			boolean close;
			int CHAR_SIZE = 25;

			@Override
			public void handle(Event args) {
				close = false;
				puzzle_view = new PuzzlePlatformerView();
				puzzle_view.initModality(Modality.APPLICATION_MODAL);
				puzzle_view.setResizable(false);
				
				setUpLevelSelection(new Stage(),puzzle_view);
				
				if(close) {
					return;
				}
				try {
					puzzle_view.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("UNABLE TO LOAD THE GAME");
				}
				
			}
			
			/**
			 * 
			 * @param selection
			 * @param view
			 * @author Eujin Ko
			 * @author Lize
			 */
			public void setUpLevelSelection(Stage selection, PuzzlePlatformerView view) {
				Insets insets = new Insets(CHAR_SIZE+5,CHAR_SIZE+5,0,CHAR_SIZE+5);
				Label label1 = new Label("Level:");
				// create radiobuttons 
				RadioButton r1 = new RadioButton("Easy"); 
				RadioButton r2 = new RadioButton("Medium");
				RadioButton r3 = new RadioButton("Hard");
				r1.setSelected(true);
				ToggleGroup tg1 = new ToggleGroup(); 
				r1.setToggleGroup(tg1); 
				r2.setToggleGroup(tg1);
				r3.setToggleGroup(tg1);
				HBox hbox1 = new HBox(label1, r1, r2, r3);
				hbox1.setSpacing(20);
				// set buttons
				Button b1 = new Button("OK");
				b1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(r1.isSelected()) {
							view.level = view.EASY;
						}else if (r2.isSelected()) {
							view.level = view.MEDIUM;
						}else if (r3.isSelected()) {
							view.level = view.HARD;
						}
						System.out.println("Current level: "+ view.level);
						selection.close();
					}
				});
				Button b2 = new Button("Cancel");
				b2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						close = true;
						selection.close();
					}
				});
				HBox hbox2 = new HBox(b1, b2);
				hbox2.setAlignment(Pos.CENTER); hbox2.setSpacing(20);
				VBox vbox = new VBox(hbox1, hbox2);
				vbox.setSpacing(30);
				vbox.setPadding(new Insets(30));
				BorderPane p2 = new BorderPane();
				p2.setCenter(vbox);
				hbox1.setAlignment(Pos.CENTER_LEFT);
				selection.setTitle("Level Selection");
				Scene scene = new Scene(p2, 320, 120); // best size
				selection.setScene(scene);
				selection.showAndWait();
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

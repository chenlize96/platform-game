package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.MainCharacterController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CharacterMoveMessage;
import model.MainCharacterModel;
import view.TestView.MainCharacterMovement;

public class PuzzlePlatformerView extends Application implements Observer {
    
	// Initialize the window size
	final int WINDOW_WIDTH = 800;
	final int WINDOW_HEIGHT = 600;
	final int ticksPerFrame = 60;
	final int MOVE_SIZE = 10;
	
	private int[] startpoint = {20,20};
	private int[] character_size = {20,20};
	
	private int unit_size = 25; // every unit in the map is 25*25    ***ATTENTION***
	//Character
	private Circle character = new Circle(startpoint[0], startpoint[1], 10, Color.RED); //radius = 10
	private MainCharacterModel character_model;
	private MainCharacterController character_controller;
	
	private char[][] map;
	
	//Lize
	public PuzzlePlatformerView() {
		map = getMap("PublicTestCases/basic.txt");// default
		print2DArray();
	}
	
	//Lize
	public void print2DArray() { // helper function
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j <map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	//Lize
	public char[][] getMap(String fileName) {
		Scanner sc = null;
		char[][] map = new char[WINDOW_HEIGHT / unit_size][WINDOW_WIDTH / unit_size];  // it should be 32*24 for 2d-array
		try {
			sc = new Scanner(new File(fileName));
			int j = 0;
            while (sc.hasNextLine()) {
            	String[] line = sc.nextLine().split("");
            	for (int i = 0; i < line.length; i++) {
            		map[j][i] = line[i].charAt(0);
            	}
            	j++;
            }
            sc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
	}
	
	
	// ATTENTION: for now, we directly use number, we would change them to [public final] later 
	/**
	 * @author Lize
	 * @author Eujin Ko
	 */
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
		grid.setPrefSize( WINDOW_WIDTH, WINDOW_HEIGHT ); // not sure its best size
		//**********************************************
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == '*') {
					Rectangle wall = new Rectangle(25, 25); 
					wall.setFill(Color.BLACK);
					grid.add(wall, j, i);
				}else if (map[i][j] == 'S') {// start
					grid.add(character, j, i); //jump doesnt work
				}else if (map[i][j] == 'E') { //exit
					Rectangle exit = new Rectangle(25, 25); 
					exit.setFill(Color.BLUE);
					grid.add(exit, j, i);
				}
			}
			
		}
		
		
		
		
		//**********************************************
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
		Scene scene = new Scene(p, 1000, 625); 
		stage.setScene(scene); stage.setTitle("Puzzle Platformer");
		stage.show();
		
		
		
		// Need to fix the gravity and event handler but added to show the progress
		
		
		character_model = new MainCharacterModel(
				startpoint[0],startpoint[1],character_size[0],character_size[1]);
		character_model.addObserver(this);
		character_controller = new MainCharacterController(character_model);
		
		
		
		AnimationTimer at = new AnimationTimer() {
			@Override
			public void handle(long now) {
			// perform ticksPerFrame ticks
			// by default this is 1
				for (int i = 0; i < ticksPerFrame; i++) {
//					callTickController();
				}
			}
		};
		at.start();
		
		scene.setOnKeyPressed(new MainCharacterMovement());
		    
	}
	
	public void callTickController() {
		gravity();
	}
	
	public void gravity() {
		character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, 0, +MOVE_SIZE);
	}


	/**
	 * Update function 
	 * @author Eujin Ko
	 */
	@Override
	public void update(Observable o, Object arg) {
		CharacterMoveMessage msg = (CharacterMoveMessage) arg;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				characterMoveTransition(msg);
			}
			
		});
		
		
	}
	
	/**
	 * Parses CharacterMoveMessage and moves the character object from the scene
	 * TODO: Need to setup Gravity
	 * @param msg CharacterMoveMessage which contains information for the movement of character
	 * @author Eujin Ko
	 */
	public void characterMoveTransition(CharacterMoveMessage msg) {
		
		int prevX = msg.getXMoveFrom();
		int prevY = msg.getYMoveFrom();
		int curX = msg.getXMoveTo();
		int curY = msg.getYMoveTo();
		
	    Path path = new Path();
	    path.getElements().add(new MoveTo(prevX, prevY));
	    path.getElements().add(new LineTo(curX, curY));
		

	    PathTransition pathTransition = new PathTransition();
	    pathTransition.setDuration(Duration.millis(100));
	    pathTransition.setNode(character); // Circle is built above
	    pathTransition.setPath(path);
	    pathTransition.play();		
	}
	
	
	
	// Private Event Handler classes
	
	
	/**
	 * Private class that handles movement of the main character depends on KeyEvents
	 * Available Movement: UP, DOWN, RIGHT, LEFT
	 * @author Eujin Ko
	 *
	 */
	class MainCharacterMovement implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
		    
			switch(event.getCode()) {
			
			case DOWN:
				System.out.println("DOWN");
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, 0, +MOVE_SIZE);
				break;
			case UP:
				System.out.println("UP");
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, 0, -MOVE_SIZE);
				break;
			case RIGHT:
				System.out.println("RIGHT");
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, +MOVE_SIZE, 0);
				break;
			case LEFT:
				System.out.println("LEFT");
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, -MOVE_SIZE, 0);
				break;
			case SPACE:
				System.out.println("JUMP");
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, 0, -MOVE_SIZE*12);
				break;
			}

		}

	}
}

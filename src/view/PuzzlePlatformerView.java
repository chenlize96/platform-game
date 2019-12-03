package view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.ControllerCollections;
import controller.MainCharacterController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.CharacterMoveMessage;
import message.CollectionsMessage;

public class PuzzlePlatformerView extends Application implements Observer {
    
	// Initialize the window size
	final int WINDOW_WIDTH = 800;
	final int WINDOW_HEIGHT = 600;
	final int ticksPerFrame = 1;
	final int MOVE_SIZE = 5;
	
	private int[] startpoint = {0,0};
	private int[] exitpoint = {0,0};
	private int[] character_size = {10,10};
	private int[] keys = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; // there may be 5 keys in a map
	
	
	private int unit_size = 25; // every unit in the map is 25*25    ***ATTENTION***
	//Character
	private Rectangle character = new Rectangle(10, 10, Color.RED); //radius = 10
	
	private boolean UP = false;
	private boolean DOWN = false;
	private boolean RIGHT = false;
	private boolean LEFT = false;
	private boolean JUMP = false;
	
	ControllerCollections controller;
	MainCharacterController character_controller;
	GridPane grid;
	AnimationTimer animationTimer;
	
	Canvas health_box;
	
	
	
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
	
	
	// 
	/**
	 * @author Eujin Ko
	 * @author Lize 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = setupStage(stage);	//Lize's stage setup

		controller = new ControllerCollections(this,grid);
		controller.callModelAddPlayer(startpoint, character_size);
		character_controller = controller.returnMainCharacterController();
		controller.callModelAddViewModel(startpoint, exitpoint);
		
		controller.callModelAddKeys(keys); 
		
		
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
			// perform ticksPerFrame ticks
			// by default this is 1
				for (int i = 0; i < ticksPerFrame; i++) {
					tick();
				}
			}
		};
		animationTimer.start();
		
		
	    scene.setOnKeyPressed(new MovementPressed());
	    scene.setOnKeyReleased(new MovementReleased());
		
		
		// Need to fix the gravity and event handler but added to show the progress
		//TODO CHARACTER
	    

		    
	}
	
	
	/**
	 * <ATTENTION> for now, we directly use number, we would change them to [public final] later 
	 * @param stage
	 * @author Lize
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) // do not modify this line
	public Scene setupStage(Stage stage) {
		// make menu
		Menu menu = new Menu("File"); 
		MenuItem item1 = new MenuItem("New Game"); // it can handle levels, networking (do later)
		MenuItem item2 = new MenuItem("Save Game"); // save (do later)
		menu.getItems().add(item1);
		menu.getItems().add(item2);
		MenuBar mb = new MenuBar(); mb.setMinHeight(25); 
		mb.getMenus().add(menu); 
		// grid holds map
		grid = new GridPane();
		grid.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		grid.setPrefSize( WINDOW_WIDTH, WINDOW_HEIGHT ); // not sure its best size
		//**********************************************
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == '*') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image wall = new Image("img/wall.png"); 
					gc.drawImage(wall, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
				}else if (map[i][j] == 'S') {// start
					startpoint[0] = j*unit_size;
					startpoint[1] = i*unit_size;//jump doesnt work
				}else if (map[i][j] == 'E') { //exit
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image exit = new Image("img/exit.png"); 
					gc.drawImage(exit, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					exitpoint[0] = j*unit_size;
					exitpoint[1] = i*unit_size;
				}else if (map[i][j] == 'D') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image door = new Image("img/door.png"); 
					gc.drawImage(door, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
				}else if (map[i][j] == 'K') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image key = new Image("img/key.png"); 
					gc.drawImage(key, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					for (int k = 0; k < keys.length / 2; k++) {
						if (keys[k * 2] == -100) {
							keys[k * 2] = j * unit_size;
							keys[k * 2 + 1] = i * unit_size;
							break;
						}
					}
				}
			}
			
		}
		
		System.out.println(keys[0] + " "+ keys[1]+ " "+ keys[2]+"******");
		
		
		//**********************************************
		// info contains hearts, time, bag (use vbox instead of BorderPane)
		VBox info = new VBox();
		info.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		info.setPrefSize(200, 600); // not sure its best size
		Label health = new Label("Health");	
		health.setFont(new Font("Arial", 30));
		health_box = new Canvas(150, 50); // label.setGraphic(new ImageView()) doesnot work, so use canvas
//		updateHealth();
		Label countdown = new Label("Countdown"); // (become red when less than 1 min)
		countdown.setFont(new Font("Arial", 30));
		Label timer = new Label("300 seconds"); // create timer
		timer.setFont(new Font("Arial", 24));
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), new EventHandler() {
					public int timeSeconds = 300; // make it 300 later, now for test
					@Override
					public void handle(Event event) {
						timeSeconds--;
						// update timerLabel
						timer.setText(timeSeconds + " seconds");
						if (timeSeconds <= 60) { // make it 60 later, now for test
							timer.setTextFill(Color.RED);
						}
						if (timeSeconds <= 0) {
							timeline.stop(); // also show alert, and freeze (do later)
							gameOverMessage();
						}
					}
				}));
		timeline.playFromStart();

		Label items = new Label("Items"); //
		items.setFont(new Font("Arial", 30));
		// there should be a gridPane holding various images which represent items 
		
		
		
		
		
		
		
		
		// Lize will do it later, since I am trying to find the best way to handle items (do later)
		info.getChildren().addAll(health, health_box, countdown, timer, items); 
		info.setAlignment(Pos.BASELINE_CENTER);
		info.setSpacing(20); // POSSIBLE optimize: add separate line for readablity (do later)
		// p holds everything
		BorderPane p = new BorderPane();
		p.setCenter(grid); p.setTop(mb); p.setRight(info);
		// there should be someway to zoom up automatically without influencing coordinates (do later or ignore)
		
		
		Group root = new Group();
		root.getChildren().add(p);
		root.getChildren().add(character);
		Scene scene = new Scene(root, 1000, 625); 
		
		stage.setScene(scene); stage.setTitle("Puzzle Platformer");
		
		
		
		stage.show();
		
		return scene;
	}
	/**
	 * 
	 * @param health_status
	 * @author Lize
	 * @author Eujin Ko 
	 */
	private void updateHealth(int health_status) {
		// the size of canvas bases on the number of hearts (modify later)
		clearCanvas(health_box);
		GraphicsContext gc = health_box.getGraphicsContext2D();
		
		Image heart = new Image("img/heart.png"); 
		
		for(int i = 0; i< health_status; i++) {
			gc.drawImage(heart, i*50, 0, 50, 50); 
		}
//		gc.drawImage(heart, 50, 0, 50, 50); // easy to update hearts by covering a 50*50 grey square (do later)
//		gc.drawImage(heart, 100, 0, 50, 50); 
		
		if(health_status == 0) {
			gameOverMessage();
		}
		
	}
	
	/**
	 * This function clears canvas
	 * @param canvas to be cleared
	 * @author Eujin Ko 
	 */
	private void clearCanvas(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	
	/**
	 * Calls tick using ControllerCollections & set up current velocity
	 * @author Eujin Ko
	 */
	private void tick() {
		handleCharacterVelocity();
		controller.callModelTick();
			
		
	}
	/**
	 * Setup current Velocity according to the key pressed
	 * @author Eujin Ko
	 */
	private void handleCharacterVelocity() {
		int moveX=0; int moveY=0;
		if(JUMP && character_controller.returnJumpStatus() == false) {
			moveY = -MOVE_SIZE*10;
			character_controller.toggleJumpStatus();
		}
//		else if(UP) {
//			moveY = -MOVE_SIZE;
//		}
		
		
		if(RIGHT) {
			moveX = MOVE_SIZE;
		}
		if(LEFT) {
			moveX = -MOVE_SIZE;
		}
		character_controller.addVelocity(moveX, moveY);
	}


	/**
	 * Fetches the message received from observable, updates the view
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	@Override
	public void update(Observable o, Object arg) {
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		CollectionsMessage msg = (CollectionsMessage) arg;
		CharacterMoveMessage char_msg = msg.getCharacterMoveMessage();
		int health_status = msg.returnHealthStatus();
		boolean win_status = msg.returnWinStatus();
		int keyPos = msg.returnKeyStatus();
//		System.out.println(keyPos +"***************************************"); // correct
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if(char_msg != null) {
					characterMoveTransition(char_msg);
					updateHealth(health_status);
					if (keyPos != -1) {
						pickUpKey(keyPos);
					}
					stageClearedMessage(win_status);
				}
			}
			
		});
	}
	
	
	//lize (disappear on the map and show in the item bag)
	@SuppressWarnings("static-access")
	public void pickUpKey(int keyPos) {
		int gridSize = grid.getChildren().size();
		int j = keys[keyPos * 2] / unit_size;
		int i = keys[keyPos * 2 + 1] / unit_size;
		for (int k = 0; k < gridSize; k++) {
			Object temp = grid.getChildren().get(k);
			if (temp instanceof Canvas) {
				Canvas target = (Canvas) temp;
				if (grid.getColumnIndex(target) == j && grid.getRowIndex(target) == i) {
					grid.getChildren().remove(k);
					keys[keyPos * 2] = -100;
					keys[keyPos * 2 + 1] = -100;
					controller.callModelAddKeys(keys); 
					break;
				}
			}
		}
		// show in the item bag
		
	


}

	
	/**
	 * Parses CharacterMoveMessage and moves the character object from the scene
	 * TODO: Need to setup Gravity
	 * @param msg CharacterMoveMessage which contains information for the movement of character
	 * @author Eujin Ko
	 */
	public void characterMoveTransition(CharacterMoveMessage msg) {
		if(msg == null) {
			return;
		}
		
		int prevX = msg.getXMoveFrom();
		int prevY = msg.getYMoveFrom();
		int curX = msg.getXMoveTo();
		int curY = msg.getYMoveTo();
//		System.out.println(prevX+","+prevY+"->"+curX+","+curY);
		
	    Path path = new Path();
//	    path.getElements().add(new MoveTo(prevX+character_size[0]/2, prevY+unit_size));
//	    path.getElements().add(new LineTo(curX+character_size[0]/2, curY+unit_size));
	    path.getElements().add(new MoveTo(prevX+character_size[0]/2, prevY+unit_size+character_size[1]/2));
	    path.getElements().add(new LineTo(curX+character_size[0]/2, curY+unit_size+character_size[1]/2));
		

	    PathTransition pathTransition = new PathTransition();
	    pathTransition.setDuration(Duration.millis(100));
	    pathTransition.setNode(character); // Circle is built above
	    pathTransition.setPath(path);
	    pathTransition.play();		
	}
	
	/**
	 * Sends a Stage cleared message, stops the view
	 * @param win_status indicates whether player has won the stage or not
	 * @author Eujin Ko
	 */
	public void stageClearedMessage(boolean win_status) 
	{
		if(win_status == false) {
			return;
		}
        Alert alert = new Alert(AlertType.INFORMATION); 
        alert.setTitle("Message");
        alert.setHeaderText("Message");
        alert.setContentText("To the next stage!");
        animationTimer.stop();
        alert.showAndWait(); 
		
	}
	/**
	 * Sends a Game Over message, stops the view
	 * @author Eujin Ko
	 */
	public void gameOverMessage() 
	{
        Alert alert = new Alert(AlertType.INFORMATION);  
        alert.setTitle("Message");
        alert.setHeaderText("Message");
        alert.setContentText("GAME OVER");
        animationTimer.stop();
        alert.show();
	}
	
	
	// Private Event Handler classes
	
	
	/**
	 * Private class that handles movement of the main character depends on KeyEvents when key pressed
	 * Available Movement: UP, DOWN, RIGHT, LEFT, JUMP
	 * @author Eujin Ko
	 *
	 */
	class MovementPressed implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
		    
			switch(event.getCode()) {
			
			case DOWN:
				System.out.println("DOWN");
				DOWN = true;
				break;
//			case UP:
//				System.out.println("UP");
//				UP = true;
//				break;
			case RIGHT:
				System.out.println("RIGHT");
				RIGHT = true;
				break;
			case LEFT:
				System.out.println("LEFT");
				LEFT = true;
				break;
			case SPACE:
				System.out.println("JUMP");
				JUMP = true;
				break;
			default:
				break;
			}

		}
		

	}
	/**
	 * Private class that handles movement of the main character depends on KeyEvents when key released
	 * Available Movement: UP, DOWN, RIGHT, LEFT, JUMP
	 * @author Eujin Ko
	 *
	 */
	class MovementReleased implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
		    
			switch(event.getCode()) {
			
			case DOWN:
				DOWN = false;
				break;
//			case UP:
//				UP = false;
//				break;
			case RIGHT:
				RIGHT = false;
				break;
			case LEFT:
				LEFT = false;
				break;
			case SPACE:
				JUMP = false;
				break;
			default:
				break;
			}

		}
		

	}
}

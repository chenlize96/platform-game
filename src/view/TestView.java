package view;

import java.util.Observable;
import java.util.Observer;

import controller.ControllerCollections;
import controller.MainCharacterController;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.CharacterMoveMessage;
import message.CollectionsMessage;
import model.MainCharacterModel;
import view.TestView.MovementPressed;
import view.TestView.MovementReleased;

public class TestView  extends Application implements Observer  {

	
	ControllerCollections controller;
	MainCharacterController character_controller;
	
	private int[] startpoint = {20,20};
	private int[] character_size = {20,20};

	private Circle character = new Circle(startpoint[0], startpoint[1], 20, Color.RED);
	
	// Initialize the window size
	final int WINDOW_WIDTH = 600;
	final int WINDOW_HEIGHT = 300;
	final int ticksPerFrame = 1;
	final int MOVE_SIZE = 10;
	
	private boolean UP = false;
	private boolean DOWN = false;
	private boolean RIGHT = false;
	private boolean LEFT = false;
	private boolean JUMP = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(TestView.class,args);
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		this.controller = new ControllerCollections(this);
		this.controller.callModelAddPlayer(startpoint, character_size);
		this.character_controller = controller.returnMainCharacterController();
		
		setupStage(stage);
	}
	
	//PLACEHOLDER
	public void setupStage(Stage stage) {
		
		// STAGE PLACE HOLDER:REMOVE
	    Group root = new Group(character);
	    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("TestStage-makes the circle moves smoothly while detecting edge");
		stage.show();
		// STAGE PLACE HOLDER:REMOVE_END
		
		
		AnimationTimer at = new AnimationTimer() {
			@Override
			public void handle(long now) {
			// perform ticksPerFrame ticks
			// by default this is 1
				for (int i = 0; i < ticksPerFrame; i++) {
					tick();
				}
			}
		};
		at.start();


	    
	    scene.setOnKeyPressed(new MovementPressed());
	    scene.setOnKeyReleased(new MovementReleased());
		
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
		if(JUMP) {
			moveY = -MOVE_SIZE*2;
		}else if(UP) {
			moveY = -MOVE_SIZE;
		}
		
		
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
	 */
	@Override
	public void update(Observable o, Object arg) {
		CollectionsMessage msg = (CollectionsMessage) arg;
		CharacterMoveMessage char_msg = msg.getCharacterMoveMessage();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				characterMoveTransition(char_msg);
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
		if(msg == null) {
			return;
		}
		
		int prevX = msg.getXMoveFrom();
		int prevY = msg.getYMoveFrom();
		int curX = msg.getXMoveTo();
		int curY = msg.getYMoveTo();
		System.out.println(prevX+","+prevY+"->"+curX+","+curY);
		
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
			case UP:
				System.out.println("UP");
				UP = true;
				break;
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
			case UP:
				UP = false;
				break;
			case RIGHT:
				RIGHT = false;
				break;
			case LEFT:
				LEFT = false;
				break;
			case SPACE:
				JUMP = false;
				break;
			}

		}
		

	}
}

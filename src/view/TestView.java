package view;

import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import controller.MainCharacterController;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CharacterMoveMessage;
import model.MainCharacterModel;

public class TestView  extends Application implements Observer  {
	private double cordX = 100;
	private double cordY = 100;
    
	// Initialize the window size
	final int WINDOW_WIDTH = 600;
	final int WINDOW_HEIGHT = 300;
	final int ticksPerFrame = 1;
	final int MOVE_SIZE = 10;
	
	private int[] startpoint = {20,20};
	private int[] character_size = {20,20};
	
	//Character
	private Circle character = new Circle(startpoint[0], startpoint[1], 20, Color.RED);
	private MainCharacterModel character_model;
	private MainCharacterController character_controller;

	
	private boolean UP = false;
	private boolean DOWN = false;
	private boolean RIGHT = false;
	private boolean LEFT = false;
	private boolean JUMP = false;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(TestView.class,args);
	}
	/**
	 * Set up the main character(place holder as circle) and empty scene
	 * @author Eujin Ko
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		character_model = new MainCharacterModel(
				startpoint[0],startpoint[1],character_size[0],character_size[1]);
		character_model.addObserver(this);
		character_controller = new MainCharacterController(character_model);
		
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
	    
	    //How to implement tick?????/
	}
	
	public void tick() {
		int moveX=0; int moveY=0;
		if(JUMP) {
			moveY = -MOVE_SIZE*5;
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
		character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT);
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
	 * Private class that handles movement of the main character depends on KeyEvents
	 * Available Movement: UP, DOWN, RIGHT, LEFT
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

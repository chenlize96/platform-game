package view;

import java.util.Observable;
import java.util.Observer;

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
import model.CharacterMoveMessage;
import model.MainCharacterModel;

public class TestView  extends Application implements Observer  {
	private double cordX = 100;
	private double cordY = 100;
    
	// Initialize the window size
	final int WINDOW_WIDTH = 600;
	final int WINDOW_HEIGHT = 300;
	final int ticksPerFrame = 60;
	final int MOVE_SIZE = 10;
	
	private int[] startpoint = {20,20};
	private int[] character_size = {20,20};
	
	//Character
	private Circle character = new Circle(startpoint[0], startpoint[1], 20, Color.RED);
	private MainCharacterModel character_model;
	private MainCharacterController character_controller;

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


	    
	    scene.setOnKeyPressed(new MainCharacterMovement());
	    
	    //How to implement tick?????/
	}
	
	public void tick() {
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
				character_controller.moveCharacter(WINDOW_WIDTH, WINDOW_HEIGHT, 0, -MOVE_SIZE*7);
				break;
			}

		}

	}

	

}

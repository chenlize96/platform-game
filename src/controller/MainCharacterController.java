package controller;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import message.CharacterMoveMessage;
import model.MainCharacterModel;

/**
 * Controller for the MainCharacterModel
 * @author Eujin Ko
 *
 */
public class MainCharacterController {
	ControllerCollections main_controller;
	private MainCharacterModel character_model;
	GridPane stage_grid;
	int window_width = 800;
	int window_height = 600;
	int unit_size = 25;
	final int MOVE_SIZE = 5;
	
	/**
	 * Constructor for MainCharacterController, saves the model object into global variable
	 * @param model
	 * @author Eujin Ko
	 * @param stage_grid 
	 */
	public MainCharacterController(ControllerCollections main_controller, MainCharacterModel model, GridPane stage_grid) {
		this.main_controller = main_controller;
		this.character_model = model;
		this.stage_grid = stage_grid;
	}
	
	/**
	 * Calls the MainCharacterModel's function to move the position of the character
	 * @param window_width width size of the window
	 * @param window_height height size of the window
	 * @param moveX x movement of main character
	 * @param moveY y movement of main character
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage moveCharacter() {
		int moveX = 0;
		int moveY = 0;
		
		int dx = character_model.getdx();
		int dy = character_model.getdy();
//		System.out.println("velocity: "+dx+","+dy);
		if(dx!=0) {
			if(dx<0) {
				moveX = -MOVE_SIZE;
				dx += MOVE_SIZE;
			}else {
				moveX = MOVE_SIZE;
				dx -= MOVE_SIZE;
			}
		}
		
		if(dy == 0) {
			moveY = MOVE_SIZE;
		}else if(dy<0) {
			moveY = -MOVE_SIZE;
			dy += MOVE_SIZE;
		}else {
			moveY = MOVE_SIZE;
			dy += MOVE_SIZE;
		}
		
		
		character_model.setVelocity(dx,dy);
		
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		int after_x = curr_x + moveX;
		int after_y = curr_y + moveY;
		int char_width = character_model.getCharSizeWidth();
		int char_height = character_model.getCharSizeHeight();

//		System.out.println("CURR LOCATION("+curr_x+","+curr_y+")");
		
		//1. Checks Collision on Walls
		if(after_x < 0) {
			after_x = char_width/2;
//			System.out.println("1. COLLISON("+after_x+","+after_y+")");
			
		}else if(after_x +char_width > window_width) {
			after_x = window_width-char_width;
//			System.out.println("2. COLLISON("+after_x+","+after_y+")");
		}
		
		
		if(after_y < 0 + char_height) {
			after_y = char_height/2;
//			System.out.println("3. COLLISON("+after_x+","+after_y+")");
			//TODO: DEAD CONDITIONS
			
		}else if(after_y > window_height-char_height) {
			after_y= window_height-char_height/2;
			System.out.println("4. COLLISON("+after_x+","+after_y+")");
			main_controller.returnViewModelController().decreaseHealth();
			
			CharacterMoveMessage msg = character_model.returnToStart();
			return msg;
		}
		
		for(Node child:stage_grid.getChildren()) {
		double x = child.getLayoutX();
		double y = child.getLayoutY();
//		System.out.println("Child  (r,c) :"+r+","+c+"  (x,y) : "+x+"."+y);

		
		
		if(y-unit_size <= after_y && after_y <= y) {
			
			if(x <= after_x && after_x < x+unit_size) {

				if(after_y > curr_y) {
					after_y = (int)y-char_height/2;
					if(character_model.returnJumpStatus() == true) {
						character_model.toggleJump();
						
					}
				}else {
					after_y = (int) (y-unit_size);
				}

			}else  {
				if((x+x+unit_size)/2 <= after_x && after_x <= x) {

					System.out.println("Child  (x,y) : "+x+"."+y);
					after_x = (int) (x+unit_size);
				}
			}
			
		}
	}

		
		
		CharacterMoveMessage msg = character_model.moveCharacter(after_x, after_y);
		
		return msg;
		
	}
	
	
	
	public void addVelocity(int dx, int dy) {
		character_model.addVelocity(dx,dy);
	}
	
	public boolean returnJumpStatus() {
		return character_model.returnJumpStatus();
	}
	public void toggleJumpStatus() {
		character_model.toggleJump();
	}
}


package controller;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import message.CharacterMoveMessage;
import model.MainCharacterModel;
import view.PuzzlePlatformerView;

/**
 * Controller for the MainCharacterModel
 * @author Eujin Ko
 *
 */
public class MainCharacterController {
	private MainCharacterModel character_model;
	GridPane stage;
	final int MOVE_SIZE = 10;
	private int unit_size = 25;
	
	/**
	 * Constructor for MainCharacterController, saves the model object into global variable
	 * @param model
	 * @author Eujin Ko
	 * @param character 
	 * @param gridPane 
	 */
	public MainCharacterController(MainCharacterModel model, GridPane gridPane) {
		this.character_model = model;
		this.stage = gridPane;
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
		
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
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
			moveY = -MOVE_SIZE;
		}else if(dy<0) {
			moveY = -MOVE_SIZE;
			dy += MOVE_SIZE;
		}else {
			moveY = MOVE_SIZE;
			dy -= MOVE_SIZE;
		}
		
		
		character_model.setVelocity(dx,dy);
		int after_x = curr_x + moveX;
		int after_y = curr_y + (-moveY);
		int char_width = character_model.getCharSizeWidth();
		int char_height = character_model.getCharSizeHeight();



		
		for(Node child : stage.getChildren()) {
			Integer x = GridPane.getColumnIndex(child)*unit_size;
			Integer y = GridPane.getRowIndex(child)*unit_size;
			if(x!= curr_x && y!= curr_y) {
				if(child.getBoundsInParent().intersects(after_x, after_y, char_width, char_height)) {
//					System.out.println("COLLISION = Child  (x,y) : "+x+","+y);
//					System.out.println("Curr: "+curr_x+","+curr_y+" :move:"+moveX+", "+moveY+": after:"+after_x+", "+after_y);
					
					after_y = y-char_height;
					
					
					CharacterMoveMessage msg = character_model.moveCharacter(after_x, after_y);
//					System.out.println("Curr: "+curr_x+","+curr_y+" :move:"+moveX+", "+moveY+": after:"+after_x+", "+after_y);
					return msg;
				}			
			}


		}
		
		
		CharacterMoveMessage msg = character_model.moveCharacter(after_x, after_y);
		character_model.setCoordinate(after_x, after_y);
		
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


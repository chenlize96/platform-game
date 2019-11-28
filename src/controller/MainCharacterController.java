package controller;

import java.util.Arrays;

import message.CharacterMoveMessage;
import model.MainCharacterModel;

/**
 * Controller for the MainCharacterModel
 * @author Eujin Ko
 *
 */
public class MainCharacterController {
	private MainCharacterModel character_model;
	final int MOVE_SIZE = 10;
	
	/**
	 * Constructor for MainCharacterController, saves the model object into global variable
	 * @param model
	 * @author Eujin Ko
	 */
	public MainCharacterController(MainCharacterModel model) {
		this.character_model = model;
	}
	
	/**
	 * Calls the MainCharacterModel's function to move the position of the character
	 * @param window_width width size of the window
	 * @param window_height height size of the window
	 * @param moveX x movement of main character
	 * @param moveY y movement of main character
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage moveCharacter(int window_width, int window_height) {
		int moveX = 0;
		int moveY = 0;
		
		int dx = character_model.getdx();
		int dy = character_model.getdy();
		System.out.println("velocity: "+dx+","+dy);
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
		
		int curr_x = character_model.getCordX() + moveX;
		int curr_y = character_model.getCordY() + moveY;
		int char_width = character_model.getCharSizeWidth();
		int char_height = character_model.getCharSizeHeight();
		
		
		//Checks Collision
		if(curr_x < 0+char_width) {
			curr_x = char_width;
//			System.out.println("1. COLLISON("+curr_x+","+curr_y+")");
			
		}else if(curr_x > window_width-char_width) {
			curr_x = window_width-char_width;
//			System.out.println("2. COLLISON("+curr_x+","+curr_y+")");
		}
		
		
		if(curr_y < 0 + char_height) {
			curr_y = char_height;
//			System.out.println("3. COLLISON("+curr_x+","+curr_y+")");
			
		}else if(curr_y > window_height-char_height) {
			curr_y= window_height-char_height;
//			System.out.println("4. COLLISON("+curr_x+","+curr_y+")");
			character_model.toggleJump();
		}
		
		CharacterMoveMessage msg = character_model.moveCharacter(curr_x, curr_y);
		
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


package controller;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
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

		System.out.println("After: "+after_x+","+after_y);
		
		//TODO: Checks Collision
		int char_half_width = char_width/2;
		int char_half_height = char_height/2;
		
		
		for(Node child:stage.getChildren()) {
//			Integer r = GridPane.getRowIndex(child);
//			Integer c = GridPane.getColumnIndex(child);
			double x = child.getLayoutX()*unit_size;
			double y = child.getLayoutY()*unit_size;
//			System.out.println("Child  (r,c) :"+r+","+c+"  (x,y) : "+x+"."+y);
//			System.out.println("Child  (x,y) : "+x+"."+y);
			
			if(x <= after_x+unit_size && after_x+unit_size <= x+unit_size) {
				if(y <= after_y+unit_size && after_y+unit_size <= y+unit_size) {
//					System.out.println("COLLISION!");
					if(after_x <= (x+x+unit_size)/2) {
						after_x = (int) x;
					}else {
						after_x = (int) (x+unit_size);
					}
					
					if(after_y <= (y+y+unit_size)/2) {
						after_y = (int)y;
					}else {
						after_y = (int) (y+unit_size);
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


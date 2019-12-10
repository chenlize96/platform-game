package controller;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import message.CharacterMoveMessage;
import model.MainCharacterModel;
import model.MonsterModel;
import model.StaticMonsterModel;
import controller.MonsterController;
/**
 * Controller for the MainCharacterModel
 * @author Eujin Ko
 *
 */
public class MainCharacterController {
	ControllerCollections main_controller;
	MonsterController monsters;
	private MainCharacterModel character_model;
	GridPane stage_grid;
	int window_width = 800;
	int window_height = 600;
	int unit_size = 25;
	final int MOVE_SIZE = 5;
	
	/**
	 * Constructor for MainCharacterController, saves the model object into global variable
	 * @param model
	 * @param stage_grid 
	 * @author Eujin Ko
	 */
	public MainCharacterController(ControllerCollections main_controller, MainCharacterModel model, GridPane stage_grid) {
		this.main_controller = main_controller;
		this.character_model = model;
		this.stage_grid = stage_grid;
		this.monsters = new MonsterController();
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
		

		
		int handleY= handleYCoordinate(curr_x, curr_y, after_x, after_y, char_width, char_height);
		after_y = handleY;
		
		int handleX = handleXCoordinate(curr_x, curr_y, after_x, after_y, char_width, char_height);
		after_x = handleX;
		
		CharacterMoveMessage msg;
		
		//1. Checks Collision on Walls
		if(after_x < 0) {
			after_x = char_width/2;
//			System.out.println("1. COLLISON("+after_x+","+after_y+")");
			
		}else if(after_x +char_width > window_width) {
			after_x = window_width-char_width;
//			System.out.println("2. COLLISON("+after_x+","+after_y+")");
		}
		
		
		if(after_y < 0 + char_height) {
			after_y = unit_size;
//			System.out.println("3. COLLISON("+after_x+","+after_y+")");
			//TODO: DEAD CONDITIONS
			
		}else if(after_y > window_height-char_height) {
			after_y= window_height-char_height/2;
//			System.out.println("4. COLLISON("+after_x+","+after_y+")");
			main_controller.returnViewModelController().decreaseHealth();
			
			msg = character_model.returnToStart();
			return msg;
		}
		
		// Collision with the monster
		for(MonsterModel each: monsters.getMonster()) {
//			System.out.println(each.getX()+ " , " + each.getY()+"|"+after_x + " , " + after_y);
			if ((each.getX() + 30 >=  after_x && each.getX() - 15 <= after_x && each.getY()==after_y) || 
					(each.getX() == after_x && each.getY() - 25 == after_y)) {
				after_y= window_height-char_height/2;
//				System.out.println("Monster COLLISON("+after_x+","+after_y+")");
				main_controller.returnViewModelController().decreaseHealth();
				
				msg = character_model.returnToStart();
				return msg;
			}
		}
		msg = character_model.moveCharacter(after_x, after_y);
		
		return msg;
		
	}
	
	
	/**
	 * Handles X coordinate Collision
	 * @param curr_x
	 * @param curr_y
	 * @param after_x
	 * @param after_y
	 * @param char_width
	 * @param char_height
	 * @return integer, x coordinate
	 * @author Eujin Ko
	 */
	public int handleXCoordinate(int curr_x, int curr_y, int after_x, int after_y, int char_width, int char_height) {
		int x_pos = after_x;
		for(Node child:stage_grid.getChildren()) {
			double x = child.getLayoutX();
			double y = child.getLayoutY();
//			System.out.println("Child  (r,c) :"+r+","+c+"  (x,y) : "+x+"."+y);

			if((x <= after_x && after_x < x+unit_size)
					||(x < after_x+char_width && after_x+char_width < x+unit_size)) {

				if(y <= after_y && after_y <= y+unit_size) {
					if(curr_x > after_x && x <= curr_x) {
						x_pos = (int) (x+unit_size);
					}else if(curr_x <= after_x && x >= curr_x){
						x_pos = (int) (after_x-char_width/2);
					}
				}
			}
		}

		return x_pos;
	}

	/**
	 * Handles Y coordinate Collision
	 * @param curr_x
	 * @param curr_y
	 * @param after_x
	 * @param after_y
	 * @param char_width
	 * @param char_height
	 * @return integer, y coordinate
	 * @author Eujin Ko
	 */
	public int handleYCoordinate(int curr_x, int curr_y, int after_x, int after_y, int char_width, int char_height) {
		int y_pos = after_y;
		for(Node child:stage_grid.getChildren()) {
			double x = child.getLayoutX();
			double y = child.getLayoutY();
//			System.out.println("Child  (r,c) :"+r+","+c+"  (x,y) : "+x+"."+y);
			if(y-unit_size < after_y && after_y <= y) {
				
				if((x < after_x && after_x < x+unit_size)
						||(x < after_x+char_width && after_x+char_width < x+unit_size)) {
					if(after_y > curr_y) {
						y_pos = (int)y-char_height/2;
						if(character_model.returnJumpStatus() == true) {
							character_model.toggleJump();
							
						}
						return y_pos;
					}else if(after_y < curr_y) {
						y_pos = after_y;
						return y_pos;
					}
				}
	
				
			}
		}
		return y_pos;
		
	}
	
	
	/**
	 * Checks if the Character reaches the exit
	 * @param x x-cordinate
	 * @param y y-coordinate
	 * @return boolean indicates if player has won the stage or not
	 * @author Eujin Ko
	 */
	public boolean checkIfCharacterIsAtExit(int x, int y) {
		int char_width = character_model.getCharSizeWidth();
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		
		//System.out.println("WIN STATUS"+curr_x+","+curr_y+"-"+x+","+y);
		if(x<= curr_x+char_width && curr_x<=x+unit_size) {
			if(y <= curr_y && curr_y <= y+unit_size) {
				return true;
			}
		}
		return false;
	}
	
	//lize
	public int checkIfThereIsAKey(int[] keys) {
		int char_width = character_model.getCharSizeWidth();
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		for (int k = 0; k < keys.length / 2; k++) {
			if (keys[k * 2] <= curr_x+char_width && keys[k * 2] + unit_size >= curr_x) {
				if (keys[k * 2 + 1] <= curr_y && keys[k * 2 + 1] + unit_size >= curr_y) {
//					System.out.println("key STATUS: x = "+curr_x+", y = "+curr_y+
//							"key's num = " + k + "keys'position: ("+keys[k*2]+","+keys[k*2+1]+")****************************");
					return k; // which key
				}
			}
		}
//		System.out.println("key STATUS: x = "+curr_x+", y = "+curr_y);
		return -1;
	}
	
	
	//lize
	public boolean checkIfThereIsAPortal(int[] portal) {
		int char_width = character_model.getCharSizeWidth();
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		for (int k = 0; k < portal.length / 2; k++) {
			if (portal[k * 2] <= curr_x+char_width && portal[k * 2] + unit_size >= curr_x) {
				if (portal[k * 2 + 1] <= curr_y && portal[k * 2 + 1] + unit_size >= curr_y) {
//					System.out.println("key STATUS: x = "+curr_x+", y = "+curr_y+
//							"key's num = " + k + "keys'position: ("+keys[k*2]+","+keys[k*2+1]+")****************************");
					return true; // which key
				}
			}
		}
//		System.out.println("key STATUS: x = "+curr_x+", y = "+curr_y);
		return false;
	}
	
	
	//lize
	public MainCharacterModel getPlayerPosition() {
		return character_model;
	}
	
	
	/**
	 * Addes the velocity to the character model
	 * @param dx x velocity
	 * @param dy y velocity
	 * @author Eujin Ko
	 */
	public void addVelocity(int dx, int dy) {
		character_model.addVelocity(dx,dy);
	}
	
	/**
	 * Returns the jump status of character model
	 * @return boolean
	 * @author Eujin Ko
	 */
	public boolean returnJumpStatus() {
		return character_model.returnJumpStatus();
	}
	/**
	 * Toggles the jump status of character model
	 * @author Eujin Ko
	 */
	public void toggleJumpStatus() {
		character_model.toggleJump();
	}

	public void addMonster(MonsterModel MonsterModel) {
		// TODO Auto-generated method stub
		monsters.addMonster(MonsterModel);
	}
	
	public void updateMonster() {
		for (MonsterModel each: monsters.getMonster()) {
			each.moveRight();
		}
	}
}


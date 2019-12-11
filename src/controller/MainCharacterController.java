package controller;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import message.CharacterMoveMessage;
import model.MainCharacterModel;
import view.PuzzlePlatformerView;
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
	PuzzlePlatformerView view;
	int window_width = 800;
	int window_height = 600;
	int unit_size = 25;
	final int MOVE_SIZE = 5;
	
	/**
	 * Constructor for MainCharacterController, saves the model object into global variable
	 * @param model
	 * @author Eujin Ko
	 */
	public MainCharacterController(ControllerCollections main_controller, MainCharacterModel model, PuzzlePlatformerView view) {
		this.main_controller = main_controller;
		this.character_model = model;
		this.stage_grid = view.callGrid();
		this.view = view;
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

		
		int handleY= handleYCoordinate(moveX, moveY);
		after_y = curr_y + handleY;
//		after_y = 565;
		int handleX = handleXCoordinate(moveX, after_y);
		after_x = curr_x + handleX;
		
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
	 * Calculates the position of the obstacles in the stage and returns how much x coordinate to move
	 * @param moveX how much X moves according to the velocity set
	 * @param afterY how much Y moves according to the velocity set
	 * @return x_pos, indicates how many x coordinate to move
	 * @author Eujin Ko
	 */
	public int handleXCoordinate(int moveX, int afterY) {
		Node character = view.callCharacter();
		double char_x = character_model.getCordX();
		double char_y = character_model.getCordY();
		int char_w = character_model.getCharSizeWidth();
		int char_h = character_model.getCharSizeHeight();
		
		int x_pos = 0;
		for(int i = 0; i< Math.abs(moveX); i++) {
			for(Node child:stage_grid.getChildren()) {
				double x = child.getLayoutX();
				double y = child.getLayoutY();
				if(child.getBoundsInParent().intersects(char_x+x_pos,afterY,char_w,char_h)) {
//					System.out.println(i+"^ CHILD="+x+" || "+y+" = "+char_x+x_pos+" || "+char_y);
					
					if(moveX < 0) {	//LEFT
						if(char_x+x_pos == x+unit_size) {
//							System.out.println(i+"^ CHILD="+x+" || "+y+" = "+char_x+x_pos+" || "+(int)(afterY));
							return x_pos+1;
						}
//						return x_pos+1;
						
					}else {	//RIGHT
						if(char_x+x_pos+char_w == x) {
							return x_pos-1;
						}
//						return x_pos-10;
					}
				}
				

			}
			if(moveX > 0) {	//RIGHT
				x_pos += 1;
			}else {	//LEFT
				x_pos -= 1;
			}
			
//			System.out.println(x_pos);
		}
		return x_pos;
//		return x_pos;
	}

	/**
	 * Calculates the position of the obstacles in the stage and returns how much y coordinate to move
	 * @param moveX how much X moves according to the velocity set
	 * @param afterY how much Y moves according to the velocity set
	 * @return y_pos, indicates how many y coordinate to move
	 * @author Eujin Ko
	 */
	public int handleYCoordinate(int moveX, int moveY){
		Node character = view.callCharacter();
		double char_x = character_model.getCordX();
		double char_y = character_model.getCordY();
		int char_w = character_model.getCharSizeWidth();
		int char_h = character_model.getCharSizeHeight();
		
		int y_pos = 0;
		for(int i = 0; i< Math.abs(moveY); i++) {
			for(Node child:stage_grid.getChildren()) {
				double x = child.getLayoutX();
				double y = child.getLayoutY();
				if(child.getBoundsInParent().intersects(char_x,char_y+y_pos,char_w,char_h)) {
//					System.out.println(i+"^ CHILD="+child.getBoundsInParent());
					
					if(moveY < 0) {	//UP
						if((int)char_y+y_pos == (int)(y+unit_size)) {
//							System.out.println(i+"^ CHILD="+x+" || "+(y+unit_size)+" = "+char_x+" || "+(int)(char_y+y_pos));
							return y_pos+1;
						}
						return y_pos-1;
//						
					}else {	//DOWN
						if(char_y+y_pos+char_h == y) {
							character_model.toggleJump();
							return y_pos-1;
						}
						return y_pos+1;
					}
				}
				

			}
			if(moveY < 0) {	//UP
				y_pos -= 1;
			}else {	//DOWN
				y_pos += 1;
			}
			
//			System.out.println(y_pos);
		}
		return y_pos;
//		return 300;
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
		int char_height = character_model.getCharSizeHeight();
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		
		//System.out.println("WIN STATUS"+curr_x+","+curr_y+"-"+x+","+y);
		if(x<= curr_x+char_width+1 && curr_x-1<=x+unit_size) {
			if(y <= curr_y+char_height+1 && curr_y-1 <= y+unit_size) {
				return true;
			}
		}
		return false;
	}
	
	//lize
	public int checkIfThereIsAKey(int[] keys) {
		int char_width = character_model.getCharSizeWidth();
		int char_height = character_model.getCharSizeHeight();
		int curr_x = character_model.getCordX();
		int curr_y = character_model.getCordY();
		for (int k = 0; k < keys.length / 2; k++) {
//			System.out.println("key STATUS: x = "+curr_x+", y = "+curr_y+
//			"key's num = " + k + "keys'position: ("+keys[k*2]+","+keys[k*2+1]+")****************************");
			if (keys[k * 2] <= curr_x+char_width+1 && keys[k * 2] + unit_size >= curr_x-1) {
				if (keys[k * 2 + 1] <= curr_y+char_height+1 && keys[k * 2 + 1] + unit_size >= curr_y-1) {
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
			if (portal[k * 2] <= curr_x+char_width+1 && portal[k * 2] + unit_size >= curr_x-1) {
				if (portal[k * 2 + 1] <= curr_y+1 && portal[k * 2 + 1] + unit_size >= curr_y-1) {
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
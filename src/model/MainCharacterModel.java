package model;

import java.util.Observable;

/**
 * Model for the main character
 * @author Eujin Ko
 *
 */
public class MainCharacterModel extends Observable{
	boolean isJumping;
	
	//Character position
	private int cord_x;
	private int cord_y;
	
	//Chracter size
	private int size_width = 20;
	private int size_height = 20;
	
	public MainCharacterModel(int start_coordinate_x, int start_coordinate_y, 
			int character_size_width, int character_size_height) {
		
		this.cord_x = start_coordinate_x;
		this.cord_y = start_coordinate_y;
		
		this.size_width = character_size_width;
		this.size_height = character_size_height;
		
		this.isJumping = false;
	}
	
	public int getCordX() {
		return this.cord_x;
	}

	public int getCordY() {
		return this.cord_y;
	}
	
	public int getCharSizeWidth() {
		return this.size_width;
	}
	public int getCharSizeHeight() {
		return this.size_height;
	}
	
	
	public void moveCharacter(int moveX, int moveY) {
		if(cord_x != moveX || cord_y != moveY) {
			
			CharacterMoveMessage msg = new CharacterMoveMessage(cord_x,cord_y, moveX, moveY);
			
			this.cord_x = moveX;
			this.cord_y = moveY;
			
			setChanged();
			notifyObservers(msg);	
		}
	}

}

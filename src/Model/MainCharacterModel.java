package model;

import java.util.Observable;

/**
 * Main Character Model
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
	
	/**
	 * Constructor for MainCharacterModel
	 * @param start_coordinate_x x position where the character will be created
	 * @param start_coordinate_y y position where the character will be created
	 * @param character_size_width character width size
	 * @param character_size_height character height size
	 * @author Eujin Ko
	 */
	public MainCharacterModel(int start_coordinate_x, int start_coordinate_y, 
			int character_size_width, int character_size_height) {
		
		this.cord_x = start_coordinate_x;
		this.cord_y = start_coordinate_y;
		
		this.size_width = character_size_width;
		this.size_height = character_size_height;
		
		this.isJumping = false;
	}
	
	/**
	 * Toggles the jump status
	 * @author Eujin Ko
	 */
	public void toggleJump() {
		this.isJumping = !this.isJumping;
	}
	
	/**
	 * Returns the jump status, false if not jumping, true if it is jumping
	 * @return boolean
	 * @author Eujin Ko
	 */
	public boolean returnJumpStatus() {
		return this.isJumping;
	}

	/**
	 * Returns current X position of the character
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCordX() {
		return this.cord_x;
	}

	/**
	 * Returns current Y position of the character
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCordY() {
		return this.cord_y;
	}
	
	/**
	 * Returns character width size
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCharSizeWidth() {
		return this.size_width;
	}
	
	/**
	 * Returns character height size
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCharSizeHeight() {
		return this.size_height;
	}
	
	/**
	 * Moves the character into position {moveX, moveY}
	 * @param moveX new X coordinate for the character
	 * @param moveY new Y coordinate for the character
	 * @author Eujin Ko
	 */
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

package model;

// import java.util.Observable;

import message.CharacterMoveMessage;

/**
 * Main Character Model
 * 
 * @author Eujin Ko
 *
 */
public class MainCharacterModel {
	boolean isJumping;

	private int[] start_pos = { 0, 0 };

	// Character position
	private int cord_x;
	private int cord_y;

	private int dx = 0;
	private int dy = 0;

	// Chracter size
	private int size_width;
	private int size_height;

	/**
	 * Constructor for MainCharacterModel
	 * 
	 * @param start_coordinate_x    x position where the character will be created
	 * @param start_coordinate_y    y position where the character will be created
	 * @param character_size_width  character width size
	 * @param character_size_height character height size
	 * @author Eujin Ko
	 */
	public MainCharacterModel(int start_coordinate_x, int start_coordinate_y,
			int character_size_width, int character_size_height) {

		this.cord_x = start_coordinate_x;
		this.cord_y = start_coordinate_y;

		this.start_pos[0] = start_coordinate_x;
		this.start_pos[1] = start_coordinate_y;

		this.size_width = character_size_width;
		this.size_height = character_size_height;

		this.isJumping = false;
	}

	/**
	 * Toggles the jump status
	 * 
	 * @author Eujin Ko
	 */
	public void toggleJump() {
		this.isJumping = !this.isJumping;
	}

	/**
	 * Returns the jump status, false if not jumping, true if it is jumping
	 * 
	 * @return boolean
	 * @author Eujin Ko
	 */
	public boolean returnJumpStatus() {
		return this.isJumping;
	}

	/**
	 * Returns current X position of the character
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCordX() {
		return this.cord_x;
	}

	/**
	 * Returns current Y position of the character
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCordY() {
		return this.cord_y;
	}

	/**
	 * set x position of the character
	 * 
	 * @author Lize Chen
	 */
	public void setCordX(int x) {
		this.cord_x = x;
	}

	/**
	 * set y position of the character
	 * 
	 * @return int
	 * @author Lize Chen
	 */
	public void setCordY(int y) {
		this.cord_y = y;
		;
	}

	/**
	 * Returns character width size
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCharSizeWidth() {
		return this.size_width;
	}

	/**
	 * Returns character height size
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getCharSizeHeight() {
		return this.size_height;
	}

	/**
	 * Adds velocity in dx, dy direction
	 * 
	 * @param x
	 * @param y
	 * @author Eujin Ko
	 */
	public void addVelocity(int x, int y) {
		dx += x;
		dy += y;
	}

	/**
	 * Set up velocity directly
	 * 
	 * @param x
	 * @param y
	 * @author Eujin Ko
	 */
	public void setVelocity(int x, int y) {
		dx = x;
		dy = y;
	}

	/**
	 * Returns dx ( velocity in x direction )
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getdx() {
		return this.dx;
	}

	/**
	 * Returns dy ( velocity in y direction )
	 * 
	 * @return int
	 * @author Eujin Ko
	 */
	public int getdy() {
		return this.dy;
	}

	/**
	 * Sets current location
	 * 
	 * @param x
	 * @param y
	 * @author Eujin Ko
	 */
	public void setCurrentLocation(int x, int y) {
		cord_x = x;
		cord_y = y;
	}

	/**
	 * Returns the character to the start when player dies
	 * Refactors the current coordinate and returns CharacterMoveMessage
	 * 
	 * @return CharacterMoveMessage
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage returnToStart() {
		CharacterMoveMessage msg = new CharacterMoveMessage(start_pos[0], start_pos[1] - size_height, start_pos[0],
				start_pos[1]);

		this.cord_x = start_pos[0];
		this.cord_y = start_pos[1];

		return msg;
	}

	/**
	 * Moves the character into position {moveX, moveY}
	 * 
	 * @param moveX new X coordinate for the character
	 * @param moveY new Y coordinate for the character
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage moveCharacter(int moveX, int moveY) {
		if (cord_x != moveX || cord_y != moveY) {

			CharacterMoveMessage msg = new CharacterMoveMessage(cord_x, cord_y, moveX, moveY);

			this.cord_x = moveX;
			this.cord_y = moveY;

			return msg;
		}
		return null;
	}

}
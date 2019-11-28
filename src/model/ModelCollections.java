package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import controller.ControllerCollections;
import controller.MainCharacterController;
import message.CharacterMoveMessage;
import message.CollectionsMessage;

/**
 * Collections of all models
 * @author Eujin Ko
 *
 */
public class ModelCollections  extends Observable {
	MainCharacterModel player;
	ControllerCollections controllerCollections;
	
	int window_width = 600;
	int window_height = 300;
	
	/**
	 * Constructor for ModelCollections, saves controllerCollections for further use inside the class
	 * @param controllerCollections
	 * @author Eujin Ko
	 */
	public ModelCollections(ControllerCollections controllerCollections) {
		this.controllerCollections = controllerCollections;
	}
	
	
	/**
	 * performs one tick for all models inside the MOdelCollections
	 * @author Eujin Ko
	 */
	public void tick() {
		CharacterMoveMessage char_msg = movePlayer();
		moveEnemies();
		moveProjectiles();
		checkForDeath();
		checkForWin();
		
		CollectionsMessage msg = new CollectionsMessage(char_msg);
		setChanged();
		notifyObservers(msg);
		
	}

	//PLAYER METHODS - STARTS
	
	/**
	 * Adds player to the player model
	 * @param startpoint
	 * @param character_size
	 * @author Eujin Ko
	 */
	public void addPlayer(int[] startpoint, int[] character_size) {
		this.player = new MainCharacterModel(startpoint[0], startpoint[1], character_size[0], character_size[1]);
	}
	
	/**
	 * Returns the player model
	 * @return MainCharacterModel
	 * @author Eujin Ko
	 */
	public MainCharacterModel returnPlayer() {
		return this.player;
	}
	
	/**
	 * Moves the player by calling the MainCharacterController through
	 * controllerCollections
	 * @return CharacterMoveMessage
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage movePlayer() {
		CharacterMoveMessage msg = controllerCollections
				.returnMainCharacterController()
				.moveCharacter(window_width, window_height);
		
		return msg;
		
	}
	//PLAYER METHODS - ENDS
	
	
	
	
	
	//PLACE HOLDERS
	

	public void moveEnemies() {
		// TODO
	}
	
	private void checkForWin() {
		// TODO
		
	}


	private void checkForDeath() {
		// TODO
		
	}


	private void moveProjectiles() {
		// TODO
		
	}
}

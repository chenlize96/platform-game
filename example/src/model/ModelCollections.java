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
	MainViewModel view_model;
	ControllerCollections controllerCollections;
	
	int window_width = 800;
	int window_height = 600;
	
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
	 * @author Lize Chen
	 */
	public void tick() {
		CharacterMoveMessage char_msg = movePlayer();
//		moveEnemies();
//		moveProjectiles();
		
		int keyPos = checkForKey();//get key postion, if -1 then not found
		int health_status = checkForDeath();
		boolean win = checkForWin();
		CollectionsMessage msg = new CollectionsMessage(char_msg, health_status,win,keyPos);
		setChanged();
		notifyObservers(msg);
		
	}
	

	
	
	
	// <NEW> VIEW MODEL METHODS - STARTS
	/**
	 * Adds ViewModel to the ModelCollections
	 * MUST BE CALLED FOR EVERY STAGE
	 * @param start player starting coordinate
	 * @param exit exit coordinate
	 * @author Eujin Ko
	 */
	public void addViewModel(int start[], int[] exit) {
		this.view_model = new MainViewModel(start, exit);
	}
	/**
	 * Returns the ViewModel
	 * @return MainViewModel
	 * @author Eujin Ko
	 */
	public MainViewModel returnViewModel() {
		return this.view_model;
	}
	
	//VIEW MODEL METHODS - ENDS
	
	
	
	// <NEW> PLAYER METHODS - STARTS
	
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
				.moveCharacter();
		
		return msg;
		
	}
	//PLAYER METHODS - ENDS
	
	
	
	
	
	//PLACE HOLDERS
	

	public void moveEnemies() {
		// TODO
	}
	
	/**
	 * Checks if the player reached the exit
	 * @return boolean
	 * @author Eujin Ko
	 */
	private boolean checkForWin() {
		int[] exit = controllerCollections.returnViewModelController().returnExitPosition();
		boolean win = controllerCollections.returnMainCharacterController().checkIfCharacterIsAtExit(exit[0], exit[1]);
		return win;
		
	}

	//lize
	private int checkForKey() {
		int[] keys = controllerCollections.returnViewModelController().returnKeyPosition();
		int k = controllerCollections.returnMainCharacterController().checkIfThereIsAKey(keys);
		return k;
	}
	
	

	/**
	 * Checks the health status of the model
	 * @return int remaining health
	 * @author Eujin Ko
	 */
	private int checkForDeath() {
		return controllerCollections.returnViewModelController().healthStatus();
	}


	private void moveProjectiles() {
		// TODO
		
	}
}
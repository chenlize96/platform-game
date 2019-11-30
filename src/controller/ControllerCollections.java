package controller;

import java.util.Observer;

import model.ModelCollections;
import view.PuzzlePlatformerView;

/**
 * Controller for ModelCollections
 * Creates all the controller inside the class and returns the controller if needed
 * @author Eujin Ko
 *
 */
public class ControllerCollections {
	ModelCollections model;
	MainCharacterController character_controller;
	PuzzlePlatformerView view;

	/**
	 * Constructor for ControllerCollections, creates ModelCollections and adds the observer(View) passed in as a argument
	 * @param observer
	 * @author Eujin Ko
	 */
	public ControllerCollections(PuzzlePlatformerView observer) {
		model = new ModelCollections(this);
		model.addObserver(observer);
		view = observer;
	}

	/**
	 * This function calls the MainCharacterModel and adds the player
	 * <WARNING> MUST BE CALLED TO USE CHARACTER
	 * @param startpoint
	 * @param character_size
	 * @author Eujin Ko
	 */
	public void callModelAddPlayer(int[] startpoint, int[] character_size) {
		model.addPlayer(startpoint, character_size);
		character_controller = new MainCharacterController(model.returnPlayer(), view.getGrid());
	}
	/**
	 * Returns the MainCharacterController
	 * @return MainCharacterController
	 * @author Eujin Ko
	 */
	public MainCharacterController returnMainCharacterController() {
		return this.character_controller;
	}
	
	/**
	 * Calls ModelCollections to perform tick()
	 * @author Eujin Ko
	 */
	public void callModelTick() {
		model.tick();
	}
	
}

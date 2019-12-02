package controller;

import java.util.Observer;

import javafx.scene.layout.GridPane;
import model.ModelCollections;

/**
 * Controller for ModelCollections
 * Creates all the controller inside the class and returns the controller if needed
 * @author Eujin Ko
 *
 */
public class ControllerCollections {
	ModelCollections model;
	MainCharacterController character_controller;
	GridPane stage_grid;

	/**
	 * Constructor for ControllerCollections, creates ModelCollections and adds the observer(View) passed in as a argument
	 * @param observer
	 * @author Eujin Ko
	 * @param stage_grid 
	 */
	public ControllerCollections(Observer observer, GridPane stage_grid) {
		model = new ModelCollections(this);
		model.addObserver(observer);
		this.stage_grid = stage_grid;
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
		character_controller = new MainCharacterController(model.returnPlayer(), stage_grid);
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

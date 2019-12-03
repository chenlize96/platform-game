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
	MainViewModelController view_controller;
	GridPane stage_grid;

	/**
	 * Constructor for ControllerCollections, creates ModelCollections and adds the observer(View) passed in as a argument
	 * @param observer
	 * @param stage_grid 
	 * @author Eujin Ko
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
		character_controller = new MainCharacterController(this, model.returnPlayer(), stage_grid);
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
	 * Adds MainViewModel to the controller with creating controller which handles the stage
	 * @param start start coordinate of the character
	 * @param exit coordinate of the exit
	 * @author Eujin Ko
	 */
	public void callModelAddViewModel(int[] start, int[] exit) {
		model.addViewModel(start, exit);
		view_controller = new MainViewModelController(model.returnViewModel());
	}
	
	
	//lize
	public void callModelAddKeys(int[] keys) {
		model.returnViewModel().setKeyPosition(keys);
	}
	
	
	
	
	/**
	 * Returns the controller which handles the MainViewModel
	 * @return MainViewModelController
	 * @author Eujin Ko
	 */
	public MainViewModelController returnViewModelController() {
		return view_controller;
	}
	
	/**
	 * Calls ModelCollections to perform tick()
	 * @author Eujin Ko
	 */
	public void callModelTick() {
		model.tick();
	}
	
}

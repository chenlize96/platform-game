package controller;

import java.util.List;
import java.util.Observer;

import javafx.scene.layout.GridPane;
import model.HorizontalMonsterModel;
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
	MainViewModelController view_controller;
	PuzzlePlatformerView view;

	/**
	 * Constructor for ControllerCollections, creates ModelCollections and adds the observer(View) passed in as a argument
	 * @param observer
	 * @param stage_grid 
	 * @author Eujin Ko
	 */
	public ControllerCollections(Observer observer, PuzzlePlatformerView stage_view) {
		model = new ModelCollections(this);
		model.addObserver(observer);
		this.view = stage_view;
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
		character_controller = new MainCharacterController(this, model.returnPlayer(), view);
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
	public void callModelAddPortal(int[] portal) {
		model.returnViewModel().setPortalPosition(portal);
	}
	
	//lize
	public void callModelAddKeys(int[] keys) {
		model.returnViewModel().setKeyPosition(keys);
	}
	
	/**
	 * Adds list(coordinates) of moving Boxes to the mainViewModel
	 * @param movingBoxes List<int[]>, coordinate of moving boxes
	 * @author Eujin Ko
	 */
	public void callModelAddMovingBoxes(List<int[]> movingBoxes) {
		model.addMovingBoxes(movingBoxes);
	}
	
	/**
	 * Adds list(coordinates) of moving Boxes to the mainViewModel
	 * @param coordinate, int[] coordinate of moving boxe
	 * @author Eujin Ko
	 */
	public void callModelAddMovingBoxes(int[] coordinate) {
		model.addMovingBoxes(coordinate);
	}
	
	/**
	 * Return list of moving boxes
	 * @return List<int[]>
	 * @author Eujin Ko
	 */
	public List<int[]> returnMovingBoxes(){
		return model.returnMovingBoxes();
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

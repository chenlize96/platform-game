package controller;

import java.util.List;

import model.MainViewModel;
/**
 * Controller class which handles the MainViewModel
 * @author Eujin Ko
 *
 */
public class MainViewModelController {
	
	MainViewModel view_model;
	/**
	 * Constructor which initializes the controller and saves the MainViewModel to control
	 * @param view_model
	 * @author Eujin Ko
	 */
	public MainViewModelController(MainViewModel view_model) {
		this.view_model = view_model;
	}
	/**
	 * Returns the health Status
	 * @return int (0~3) health
	 * @author Eujin Ko
	 */
	public int healthStatus() {
		return view_model.returnHealthLeft();
	}
	
	
	//lize
	public void setHealthStatus(int left) {
		view_model.setHealthLeft(left);
	}
	
	
	/**
	 * Decreases the health by 1
	 * @author Eujin Ko
	 */
	public void decreaseHealth() {
		view_model.decreaseHealth();
	}
	/**
	 * return the exit position
	 * @return int[] coordinate of the exit
	 * @author Eujin Ko
	 */
	public int[] returnExitPosition() {
		return view_model.returnExitPosition();
	}
	/**
	 * Return list of moving boxes
	 * @return List<int[]>
	 * @author Eujin Ko
	 */
	public List<int[]> returnMovingBoxes(){
		return view_model.returnMovingBoxes();
	}
	
	/**
	 * 
	 * @param path 
	 * @author Eujin Ko
	 */
	public void movingBoxPopAndAddToStack(String direction, int[] coordinate) {
		view_model.movingBoxPopAndAddToStack(direction, coordinate);
	}
	/**
	 * 
	 * @return
	 * @author Eujin Ko
	 */
	public String returnMovingBoxStackDirection() {
		return view_model.returnMovingBoxStackDirection();
	}
	
	/**
	 * 
	 * @return
	 * @author Eujin Ko
	 */
	public int[] returnMovingBoxStackCoordinate() {
		return view_model.returnMovingBoxStackCoordinate();
	}
	/**
	 * 
	 * @author Eujin Ko
	 */
	public void clearMovingBoxStack() {
		view_model.clearMovingBoxStack();
	}
	//lize
		
	public int[] returnPortalPosition() {
		return view_model.returnPortalPosition();
	}
	
	//lize
	public int[] returnKeyPosition() {
		return view_model.returnKeysPosition();
	}

}
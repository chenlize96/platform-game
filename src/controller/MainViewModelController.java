package controller;

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

}

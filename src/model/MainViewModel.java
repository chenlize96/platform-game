package model;

/**
 * View Model which contains information how many health left and the coordinate of exit, starting position of character
 * @author Eujin Ko
 *
 */
public class MainViewModel {
	int[] start = {0,0};
	int[] exit = {0,0};
	int[] portal = {0,0};
	int[] keys = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; // there may be 5 keys in a map
	//int[] doors = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; 
	int health_left;
	
	/**
	 * Constructor for MainViewModel, initializes the values
	 * @param start coordinate for the starting position
	 * @param exit coordinate for the exit
	 * @author Eujin Ko
	 */
	public MainViewModel(int[] start, int[] exit) {
		this.health_left = 3;
		this.exit[0] = exit[0];
		this.exit[1] = exit[1];
		this.start[0] = start[0];
		this.start[1] = start[1];
	}
	
	
	//lize  only handle five keys but i think enough
	public void setKeyPosition(int[] keys) {
		this.keys = keys; 
	}
	
	//lize
	public void setPortalPosition(int[] portal) {
		this.portal = portal;
	}
	
	//lize 
	public int[] returnPortalPosition() {
		return portal;
	}
	
	//lize
	public int[] returnKeysPosition() {
		return this.keys;
	}
	
	
	/**
	 * Returns the health status
	 * @return int
	 * @author Eujin Ko
	 */
	public int returnHealthLeft() {
		return this.health_left;
	}
	/**
	 * Returns the coordinate of exit
	 * @return int[] coordinate of exit
	 * @author Eujin Ko
	 */
	public int[] returnExitPosition() {
		return this.exit;
	}
	
	
	/**
	 * Decreases health by 1
	 * @author Eujin Ko
	 */
	public void decreaseHealth() {
		this.health_left -= 1;
	}

}

package model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * View Model which contains information how many health left and the coordinate of exit, starting position of character
 * @author Eujin Ko
 * @author Lize Chen
 *
 */
public class MainViewModel {
	int[] start = {0,0};
	int[] exit = {0,0};
	int[] portal = {0,0};
	int[] keys = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; // there may be 5 keys in a map
	//int[] doors = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; 
	int health_left;
	List<int[]> movingBoxes;
	Map<int[],String> movingBox_stack;
	
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
		this.movingBox_stack = new HashMap<>();
	}
	
	
	/**
	 * this function is to handle up to five keys
	 * @param keys - the coordinates of keys
	 * @author Lize Chen
	 */
	public void setKeyPosition(int[] keys) {
		this.keys = keys; 
	}
	
	/**
	 * This removes the box in the coordinate
	 * @param coordinate
	 * @param direction 
	 * @author Eujin Ko
	 */
	public void movingBoxPopAndAddToStack(String direction, int[] coordinate) {
		System.out.println("BOX(remove+model)= "+movingBoxes.remove(coordinate)+" = "+direction+" = "+Arrays.toString(coordinate) );
		this.movingBox_stack.put(coordinate, direction);
		System.out.println(this.movingBox_stack.get(coordinate)+":"+Arrays.toString(coordinate));
	}
	/**
	 * Returns the Direction of moving box in the stack
	 * @return String, direction(right or left)
	 * @author Eujin Ko
	 */
	public String returnMovingBoxStackDirection() {
		for(int[] key: movingBox_stack.keySet()) {
			String direction = movingBox_stack.get(key);
			return direction;
			
		}
		return null;
	}
	
	/**
	 * Returns the coordinate of moving box in the stack
	 * @return int[], coordinate
	 * @author Eujin Ko
	 */
	public int[] returnMovingBoxStackCoordinate() {
		for(int[] key: movingBox_stack.keySet()) {
			return key;
			
		}
		return null;
	}
	/**
	 * Clears the moveBoxStack
	 * @author Eujin Ko
	 */
	public void clearMovingBoxStack() {
		this.movingBox_stack.clear();
	}

	/**
	 * Add the list of Moving boxes
	 * @param movingBoxes
	 * @author Eujin Ko
	 */
	public void addMovingBox(List<int[]> movingBoxes) {
		this.movingBoxes = movingBoxes;
		
	}
	/**
	 * Add the list of Moving boxes
	 * @param coordinate, coordinate of moving box
	 * @author Eujin Ko
	 */
	public void addMovingBox(int[] coordinate) {
		this.movingBoxes.add(coordinate);
		
	}
	
	/**
	 * Return list of moving boxes
	 * @return List<int[]>
	 * @author Eujin Ko
	 */
	public List<int[]> returnMovingBoxes(){
		return this.movingBoxes;
	}

	/**
	 * this function is to set the position of portal
	 * @param portal - the coordinates of portal
	 * @author Lize Chen
	 */
	public void setPortalPosition(int[] portal) {
		this.portal = portal;
	}
	
	/**
	 * this function is to return the position of portal
	 * @return int[]
	 * @author Lize Chen
	 */
	public int[] returnPortalPosition() {
		return portal;
	}
	
	/**
	 * this function is to return the position of key
	 * @return int[]
	 * @author Lize Chen
	 */
	public int[] returnKeysPosition() {
		return this.keys;
	}
	
	/**
	 * this function is to set the health
	 * @param left - the health left
	 * @author Lize Chen
	 */ 
	public void setHealthLeft(int left) {
		this.health_left = left;
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


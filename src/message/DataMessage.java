package message;

import java.io.Serializable;

/**
 * This function is regarded as storage for save and load
 * @author Lize Chen
 */
@SuppressWarnings("serial")
public class DataMessage implements Serializable {

	private int time;
	private int health;
	private char[][] map;
	private int key;

	/**
	 * set the time
	 * @author Lize Chen
	 * @param time - countdown
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * set the health
	 * @author Lize Chen
	 * @param health - the number of hearts
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * set the map
	 * @author Lize Chen
	 * @param map - store the map
	 */
	public void setMap(char[][] map){
		this.map = map;
	}
	
	/**
	 * set the key
	 * @author Lize Chen
	 * @param key - the number of keys
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * return the time
	 * @author Lize Chen
	 * @return int
	 */
	public int returnTime() {
		return time;
	}

	/**
	 * return the health
	 * @author Lize Chen
	 * @return int
	 */
	public int returnHealth() {
		return health;
	}

	/**
	 * return the map
	 * @author Lize Chen
	 * @return char[][]
	 */
	public char[][] returnMap(){
		return map;
	}

	/**
	 * return the key
	 * @author Lize Chen
	 * @return int
	 */
	public int returnKey() {
		return key;
	}

}

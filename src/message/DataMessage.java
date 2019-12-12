package message;

import java.io.Serializable;


public class DataMessage implements Serializable {
	
	private int time;
	private int health;
	private char[][] map;
	private int key;

	
	public void setTime(int time) {
		this.time = time;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setMap(char[][] map){
		this.map = map;
	}
	public void setKey(int key) {
		this.key = key;
	}
	
	public int returnTime() {
		return time;
	}
	
	public int returnHealth() {
		return health;
	}

	public char[][] returnMap(){
		return map;
	}
	
	public int returnKey() {
		return key;
	}

}

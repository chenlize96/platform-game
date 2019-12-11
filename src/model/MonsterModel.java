package model;
/**
 * MonsterModel
 * @author Suyang Chen
 *
 */
public class MonsterModel {
	private int x;
	private int y;
	private int size;
	private int velocity;
	
	public MonsterModel(int x, int y, int size, int velocity) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.velocity = velocity;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void moveLeft() {
		this.x -= velocity;
	}
	
	public void moveRight() {
		this.x = (this.x + velocity) % size;
	}
	
	public void moveUp() {
		this.y += velocity;
	}
	
	public void moveDown() {
		this.y -= velocity;
	}
}

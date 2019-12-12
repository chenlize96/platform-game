package model;
/**
 * MonsterModel
 * @author Suyang Chen
 *
 */
public class MonsterModel {
	protected int x;
	protected int y;
	protected int size;
	protected int velocity;
	
	public MonsterModel(int x, int y, int size, int velocity) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.velocity = velocity;
	}
	
	/**
	 * set the x position of the monster 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	public void sety(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getVelocity() {
		return this.velocity;
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
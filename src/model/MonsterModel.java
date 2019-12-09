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
	
	public MonsterModel(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		velocity = 0;
	}
	
	public boolean isCollision(int x, int y) {
		return this.x >= x - size/2 && this.x <= x + size/2
				&& this.y >= y - size/2 && this.y <= y + size/2;
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
		this.x += velocity;
	}
	
	public void moveUp() {
		this.y += velocity;
	}
	
	public void moveDown() {
		this.y -= velocity;
	}
}

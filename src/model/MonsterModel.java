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
	
	public MonsterModel(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
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
		this.x -= 1;
	}
	
	public void moveRight() {
		this.x += 1;
	}
	
	public void moveUp() {
		this.y += 1;
	}
	
	public void moveDown() {
		this.y -= 1;
	}
}

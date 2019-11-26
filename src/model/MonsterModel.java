package model;
/**
 * MonsterModel
 * @author Suyang Chen
 *
 */
public class MonsterModel {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public MonsterModel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean isCollision(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	
}

package model;

public class MainViewModel {
	int[] start = {0,0};
	int[] exit = {0,0};
	int health_left;
	
	public MainViewModel(int[] start, int[] exit) {
		this.health_left = 3;
		this.exit[0] = exit[0];
		this.exit[1] = exit[1];
		this.start[0] = start[0];
		this.start[1] = start[1];
	}
	
	public int returnHealthLeft() {
		return this.health_left;
	}
	
	public void decreaseHealth() {
		this.health_left -= 1;
	}

}

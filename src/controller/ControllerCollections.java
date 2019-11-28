package controller;

import java.util.Observer;

import model.ModelCollections;

public class ControllerCollections {
	ModelCollections model;
	MainCharacterController character_controller;

	public ControllerCollections(Observer observer) {
		model = new ModelCollections(this);
		model.addObserver(observer);
	}

	//MUST BE CALLED TO USE CHARACTER
	public void callModelAddPlayer(int[] startpoint, int[] character_size) {
		model.addPlayer(startpoint, character_size);
		character_controller = new MainCharacterController(model.returnPlayer());
	}
	public MainCharacterController returnMainCharacterController() {
		return this.character_controller;
	}
	
	
	public void callModelTick() {
		model.tick();
	}
	
}

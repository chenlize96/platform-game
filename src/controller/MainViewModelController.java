package controller;

import model.MainViewModel;

public class MainViewModelController {
	
	MainViewModel view_model;
	public MainViewModelController(MainViewModel view_model) {
		this.view_model = view_model;
	}
	public int healthStatus() {
		return view_model.returnHealthLeft();
	}
	public void decreaseHealth() {
		view_model.decreaseHealth();
	}

}

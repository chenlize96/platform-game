package controller;

import java.util.ArrayList;

import model.MonsterModel;

public class MonsterController {
	private ArrayList<MonsterModel> monsters;

	public MonsterController() {
		this.monsters = new ArrayList<MonsterModel>();
	}

	public void addMonster(MonsterModel monster) {
		monsters.add(monster);
	}

	public ArrayList<MonsterModel> getMonster() {
		return this.monsters;
	}
}
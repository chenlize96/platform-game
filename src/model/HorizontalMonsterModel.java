package model;


import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class HorizontalMonsterModel extends MonsterModel {
	private int x;
	private int y;
	private int size;
	private int velocity;
	public HorizontalMonsterModel(int i, int j, int unit_size) {
		// TODO Auto-generated constructor stub
		super(i, j, unit_size);
		velocity = 5;
	}
	
}


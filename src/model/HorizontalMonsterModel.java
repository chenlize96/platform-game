package model;

// import javafx.animation.Interpolator;
// import javafx.animation.PathTransition;
// import javafx.animation.Transition;
// import javafx.geometry.Rectangle2D;
// import javafx.scene.image.ImageView;
// import javafx.scene.shape.Line;
// import javafx.util.Duration;
// import javafx.animation.Animation;
// import javafx.application.Application;
// import javafx.geometry.Rectangle2D;
// import javafx.scene.Group;
// import javafx.scene.Scene;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.stage.Stage;
// import javafx.util.Duration;

public class HorizontalMonsterModel extends MonsterModel {
	private int count;
	private int len;

	public HorizontalMonsterModel(int i, int j, int unit_size, int velocity) {
		// Auto-generated constructor stub
		super(i, j, unit_size, velocity);
		count = i;
		len = 0;
	}

	@Override
	public void moveRight() {
		len += this.getVelocity();
		len %= this.size;
		// System.out.println(len);
		this.setX(count + len);
	}

}

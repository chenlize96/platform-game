import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestStage  extends Application {
	private double cordX = 100;
	private double cordY = 100;
	
	private int radius = 20;
	
	private Circle circle = new Circle(cordX, cordY, radius, Color.RED);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(TestStage.class,args);
	}
	/**
	 * @author Eujin Ko
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
//	    path.getElements().add(new LineTo(300, 50));
//	    path.getElements().add(new LineTo(250, 100));
	    
		int WINDOW_WIDTH = 600;
		int WINDOW_HEIGHT = 300;
	    Group root = new Group(circle);
	    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	    
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
			    Path path = new Path();
			    path.getElements().add(new MoveTo(cordX, cordY));
			    
				switch(event.getCode()) {
				case DOWN:
					System.out.println("DOWN");
					cordY += 10;
					break;
				case UP:
					System.out.println("UP");
					cordY -= 10;
					break;
				case RIGHT:
					System.out.println("RIGHT");
					cordX += 10;
					break;
				case LEFT:
					System.out.println("LEFT");
					cordX -= 10;
					break;
				
				}
				if(!checkEdge()) {
				    path.getElements().add(new LineTo(cordX, cordY));
					

				    PathTransition pathTransition = new PathTransition();
				    pathTransition.setDuration(Duration.millis(100));
				    pathTransition.setNode(circle); // Circle is built above
				    pathTransition.setPath(path);
				    pathTransition.play();					
				}

				
			}
			
			/**
			 * If object meets the edge return false
			 * @return
			 * @author Eujin Ko
			 */
			private boolean checkEdge() {
				if(cordX < 0+radius) {
					cordX = radius;
					System.out.println("COLLISON"+cordX);
					return true;
				}else if(cordX> WINDOW_WIDTH-radius) {
					cordX = WINDOW_WIDTH-radius;
					System.out.println("COLLISON"+cordX);
					return true;
				}
				if(cordY< 0 + radius) {
					cordY = radius;
					System.out.println("COLLISON"+cordY);
					return true;
				}else if(cordY> WINDOW_HEIGHT-radius) {
					cordY= WINDOW_HEIGHT-radius;
					System.out.println("COLLISON"+cordY);
					return true;
				}
				return false;
			}
	    	
	    });
	    
		stage.setScene(scene);
		stage.setTitle("TestStage-makes the circle moves smoothly while detecting edge");
		stage.show();
	}

}

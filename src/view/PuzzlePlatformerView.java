package view;

import java.io.File;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.ControllerCollections;
import controller.HorizonatalMonsterController;
import controller.MainCharacterController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.CharacterMoveMessage;
import message.CollectionsMessage;
import model.StaticMonsterModel;
import model.DenseFogModel;
import model.HorizontalMonsterModel;

public class PuzzlePlatformerView extends Application implements Observer {
    
	// Initialize the window size
	final int WINDOW_WIDTH = 800;
	final int WINDOW_HEIGHT = 600;
	final int ticksPerFrame = 1;
	final int MOVE_SIZE = 5;
	
	final int EASY = 123;
	final int MEDIUM = 124;
	final int HARD = 125;
	final int HARD_PART2 = 126;
	
	private int[] startpoint = {0,0};
	private int[] exitpoint = {0,0};
	private int[] portal = {0,0};
	private int[] character_size = {20,20};
	private int[] keys = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; // there may be 5 keys in a map
	private int[] doors = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100}; // five doors
	
	private int level = EASY; // default is easy
	private int keyNum = 0;
	private int healthLeft = -1;
	
	private int unit_size = 25; // every unit in the map is 25*25    ***ATTENTION***
	//Character
	private Rectangle character = new Rectangle(character_size[0], character_size[1], Color.RED); //radius = 10
	private Label itemKeyNum;
	private int timeSeconds = 300;
	

	
	private Rectangle wall1 = new Rectangle(20, 5, Color.PINK); //radius = 10
    private Rectangle wall2 = new Rectangle(20, 5, Color.PINK); //radius = 10
    private Rectangle wall3 = new Rectangle(5, 20, Color.PINK); //radius = 10
    private Rectangle wall4 = new Rectangle(5, 20, Color.PINK); //radius = 10
	
//	private boolean UP = false;
//	private boolean DOWN = false;

	private boolean RIGHT = false;
	private boolean LEFT = false;
	private boolean JUMP = false;
	private boolean ifPortal = false;
	private Group root;
	ControllerCollections controller;
	MainCharacterController character_controller;
	GridPane grid;
	AnimationTimer animationTimer;
	Timeline timeline;
	PuzzlePlatformerView itself = this;
	PuzzlePlatformerView view;
	
	Canvas health_box;
	
	private char[][] map;
	
	// NO CONSTRUCTOR
	
	
	//Lize
	public void readFile() {
		if (level == EASY) {
			map = getMap("PublicTestCases/basic.txt");// default
		}else if (level == MEDIUM) {
			map = getMap("PublicTestCases/medium.txt");
		}else if (level == HARD) {
			map = getMap("PublicTestCases/hard.txt");
		}else if (level == HARD_PART2) {
			map = getMap("PublicTestCases/hardPart2.txt"); 
		}
		this.view = this;
		print2DArray();
		if (root == null) {
			root = new Group();
		}else if (root.getChildren().size() > 2) {
			Iterator<?> iterator = root.getChildren().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				iterator.next();
				if (count >= 2) {
					iterator.remove();
				}
				count += 1;
			}
		}
	}

	//Lize
	public void print2DArray() { // helper function
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j <map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	//Lize
	public char[][] getMap(String fileName) {
		Scanner sc = null;
		char[][] map = new char[WINDOW_HEIGHT / unit_size][WINDOW_WIDTH / unit_size];  // it should be 32*24 for 2d-array
		try {
			sc = new Scanner(new File(fileName));
			int j = 0;
            while (sc.hasNextLine()) {
            	String[] line = sc.nextLine().split("");
            	for (int i = 0; i < line.length; i++) {
            		map[j][i] = line[i].charAt(0);
            	}
            	j++;
            }
            sc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
	}
	
	
	// 
	/**
	 * @author Eujin Ko
	 * @author Lize 
	 */
	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = setUpStage(stage);	//Lize's stage setup
		character.setOnMouseClicked(new EventHandler() {
			@Override
			public void handle(Event e) {
				Node node = (Node)e.getSource();
				System.out.println("GRID: (X,Y) = "+node.getBoundsInParent().getMaxX()+","+node.getBoundsInParent().getMaxY()+")");
				
			}
			
		});
		controller = new ControllerCollections(this, itself);
		controller.callModelAddPlayer(startpoint, character_size);
		character_controller = controller.returnMainCharacterController();
		controller.callModelAddViewModel(startpoint, exitpoint);
		controller.callModelAddKeys(keys); 
		//if (ifPortal) {
		//	controller.callModelAddPortal(portal);
		//}
		
		//addMonster();
		
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
			// perform ticksPerFrame ticks
			// by default this is 1
				for (int i = 0; i < ticksPerFrame; i++) {
					tick();
				}
			}
		};
		animationTimer.start();
		
		
	    scene.setOnKeyPressed(new MovementPressed());
	    scene.setOnKeyReleased(new MovementReleased());


	    // Need to fix the gravity and event handler but added to show the progress
	    //TODO CHARACTER

	}

	
	
	public void addMonster() {
		if (level == EASY) {
			character_controller.addMonster(new StaticMonsterModel(445, 315, unit_size, 0));
		}else if(level == MEDIUM) {
			character_controller.addMonster(new HorizontalMonsterModel(445, 315, 100, 3));
		}else {
			
		}
	}
	
	// lize
	public void setUpLevelSelection(Stage selection) {
		Label label1 = new Label("Level:");
		// create radiobuttons 
		RadioButton r1 = new RadioButton("Easy"); 
		RadioButton r2 = new RadioButton("Medium");
		RadioButton r3 = new RadioButton("Hard");
		r1.setSelected(true);
		ToggleGroup tg1 = new ToggleGroup(); 
		r1.setToggleGroup(tg1); 
		r2.setToggleGroup(tg1);
		r3.setToggleGroup(tg1);
		HBox hbox1 = new HBox(label1, r1, r2, r3);
		hbox1.setSpacing(20);
		// set buttons
		Button b1 = new Button("OK");
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(r1.isSelected()) {
					level = EASY;
				}else if (r2.isSelected()) {
					level = MEDIUM;
				}else if (r3.isSelected()) {
					level = HARD;
				}
				System.out.println(level);
				selection.close();
				readFile(); //get new map
				drawMap(); // update new map
				timeSeconds = 300; // reset countdown
				animationTimer.start();
			}
		});
		Button b2 = new Button("Cancel");
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				selection.close();
			}
		});
		HBox hbox2 = new HBox(b1, b2);
		hbox2.setAlignment(Pos.CENTER); hbox2.setSpacing(20);
		VBox vbox = new VBox(hbox1, hbox2);
		vbox.setSpacing(30);
		vbox.setPadding(new Insets(30));
		BorderPane p2 = new BorderPane();
		p2.setCenter(vbox);
		hbox1.setAlignment(Pos.CENTER_LEFT);
		selection.setTitle("Level Selection");
		Scene scene = new Scene(p2, 320, 120); // best size
		selection.setScene(scene);
	}
	
	
	private void updateMonster() {

	}

	public class SpriteAnimation extends Transition {

		private final ImageView imageView;
		private final int count;
		private final int columns;
		private final int offsetX;
		private final int offsetY;
		private final int width;
		private final int height;
		private int size = 25;

		private int lastIndex;

		public SpriteAnimation(
				ImageView imageView, 
				Duration duration, 
				int count,   int columns,
				int offsetX, int offsetY,
				int width,   int height) {
			this.imageView = imageView;
			this.count     = count;
			this.columns   = columns;
			this.offsetX   = offsetX;
			this.offsetY   = offsetY;
			this.width     = width;
			this.height    = height;
			setCycleDuration(duration);
			setInterpolator(Interpolator.LINEAR);
		}

		protected void interpolate(double k) {
			final int index = Math.min((int) Math.floor(k * count), count - 1);
			if (index != lastIndex) {
				final int x = (index % columns) * width  + offsetX;
				final int y = (index / columns) * height + offsetY;
				imageView.setViewport(new Rectangle2D(x, y, width, height));
				lastIndex = index;
				updateMonster();
			}
		}
	}

	//lize
	public void drawMap() {
		// clear all things on grid
		grid.getChildren().clear();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == '*') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image wall = new Image("img/wall.png");
					//TEST
					canvas.setOnMouseClicked(new EventHandler() {
						@Override
						public void handle(Event e) {
							Node node = (Node)e.getSource();
							System.out.println("GRID: (X,Y) = "+node.getLayoutX()+","+node.getLayoutY()+")");
							
						}
						
					});
					//TEST_END
					gc.drawImage(wall, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
				}else if (map[i][j] == 'S') {// start
					startpoint[0] = j*unit_size+1;
					startpoint[1] = i*unit_size+1;//jump doesnt work
				}else if (map[i][j] == 'E') { //exit
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image exit = new Image("img/exit.png"); 
					gc.drawImage(exit, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					exitpoint[0] = j*unit_size;
					exitpoint[1] = i*unit_size;
				}else if (map[i][j] == 'D') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image door = new Image("img/door.png"); 
					gc.drawImage(door, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					for (int k = 0; k < doors.length / 2; k++) {
						if (doors[k * 2] == -100) {
							doors[k * 2] = j * unit_size;
							doors[k * 2 + 1] = i * unit_size;
//									System.out.println(doors[0]+"-"+doors[1]);
							break;
						}
					}
				}else if (map[i][j] == 'K') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image key = new Image("img/key.png"); 
					gc.drawImage(key, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					for (int k = 0; k < keys.length / 2; k++) {
						if (keys[k * 2] == -100) {
							keys[k * 2] = j * unit_size;
							keys[k * 2 + 1] = i * unit_size;
							break;
						}
					}
				}else if (map[i][j] == 'M') {

					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image monster = new Image("img/Static.png"); 
					gc.drawImage(monster, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
				}else if (map[i][j] == 'H') {
					Image IMAGE = new Image("img/Run.png");
					final int COLUMNS  =   3;
					final int COUNT    =  8;
					final int OFFSET_X =  3;
					final int OFFSET_Y =  0;
					final int WIDTH    = unit_size;
					final int HEIGHT   = unit_size;

					ImageView imageView = new ImageView(IMAGE);
					imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

					final Animation animation = new SpriteAnimation(
							imageView,
							Duration.millis(1000),
							COUNT, COLUMNS,
							OFFSET_X, OFFSET_Y,
							WIDTH, HEIGHT

							);
					animation.setCycleCount(Animation.INDEFINITE);
					animation.play();


					Line line = new Line(500, 395, 630, 395);
					PathTransition trasition = new PathTransition();
					trasition.setNode(imageView);
					trasition.setDuration(Duration.seconds(3));
					trasition.setPath(line);
					trasition.setCycleCount(PathTransition.INDEFINITE);
					trasition.play();

					root.getChildren().add(imageView);
					//stage.setOnShowing(new Scene(root, 1000, 625)); 
					//stage.show(new Scene(new Group (root)));

					//Canvas canvas = new Canvas(103, 72); 
					//GraphicsContext gc = canvas.getGraphicsContext2D();
					//Image monster = new Image("img/Run.png"); 
					//gc.drawImage(IMAGE, 103, HEIGHT, HEIGHT, HEIGHT, HEIGHT, HEIGHT, HEIGHT, HEIGHT); 
					//grid.add(canvas, j, i);
					//grid.add(IMAGE, j, i);

				}else if (map[i][j] == 'P') {
					Canvas canvas = new Canvas(unit_size, unit_size); 
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Image door = new Image("img/portal.png"); 
					gc.drawImage(door, 0, 0, unit_size, unit_size); 
					grid.add(canvas, j, i);
					portal[0] = j*unit_size;
					portal[1] = i*unit_size;
					//controller.callModelAddPortal(portal);
					ifPortal = true;
				}
			}
		}
		
		root.getChildren().addAll(wall1,wall2,wall3,wall4);
		setUpController();	
//				System.out.println(keys[0] + " "+ keys[1]+ " "+ keys[2]+"******");
	}

	//lize
	public void setUpController() {
		controller = new ControllerCollections(this,itself);
		controller.callModelAddPlayer(startpoint, character_size);
		character_controller = controller.returnMainCharacterController();
		controller.callModelAddViewModel(startpoint, exitpoint);
		controller.callModelAddKeys(keys); 
		if (ifPortal) {
			controller.callModelAddPortal(portal);
		}
		addMonster();
	}
	
	
	/**
	 * <ATTENTION> for now, we directly use number, we would change them to [public final] later 
	 * @param stage
	 * @author Lize
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) // do not modify this line
	public Scene setUpStage(Stage stage) {
		readFile(); ////***********************************************
		// make menu
		Menu menu = new Menu("File"); 
		MenuItem item1 = new MenuItem("New Game"); // it can handle levels, networking (do later)
		//******************
		item1.setOnAction(new EventHandler<ActionEvent>() {
			//lize
			@Override
			public void handle(ActionEvent event) {
				try {
					Stage selection = new Stage();
					setUpLevelSelection(selection);		
					selection.showAndWait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//******************
		MenuItem item2 = new MenuItem("Save Game"); // save (do later)
		MenuItem item3 = new MenuItem("Online Game");
		menu.getItems().add(item1);
		menu.getItems().add(item2);
		menu.getItems().add(item3);
		MenuBar mb = new MenuBar(); mb.setMinHeight(25); 
		mb.getMenus().add(menu); 
		// grid holds map
		grid = new GridPane();
		grid.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		grid.setPrefSize( WINDOW_WIDTH, WINDOW_HEIGHT ); // not sure its best size
		// info contains hearts, time, bag (use vbox instead of BorderPane)
		VBox info = new VBox();
		info.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		info.setPrefSize(200, 600); // not sure its best size
		Label health = new Label("Health");	
		health.setFont(new Font("Arial", 30));
		health_box = new Canvas(150, 50); // label.setGraphic(new ImageView()) doesnot work, so use canvas
		//		updateHealth();
		Label countdown = new Label("Countdown"); // (become red when less than 1 min)
		countdown.setFont(new Font("Arial", 30));
		Label timer = new Label("300 seconds"); // create timer
		timer.setFont(new Font("Arial", 24));
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), new EventHandler() {
					//public int timeSeconds = 300; // make it 300 later, now for test
					
					@Override
					public void handle(Event event) {
						timeSeconds--;
						// update timerLabel
						timer.setText(timeSeconds + " seconds");
						if (timeSeconds <= 60) { // make it 60 later, now for test
							timer.setTextFill(Color.RED);
						}
						if (timeSeconds <= 0) {
							timeline.stop(); // also show alert, and freeze (do later)
							gameOverMessage();
						}
					}
				}));
		timeline.playFromStart();
		Label items = new Label("Items"); //
		items.setFont(new Font("Arial", 30));
		// there should be a gridPane holding various images which represent items 
		Label itemPress = new Label("Press"); 
		itemPress.setFont(new Font("Arial", 18));
		Label itemImage = new Label("Image");
		itemImage.setFont(new Font("Arial", 18));
		Label itemNum = new Label("Unit");
		itemNum.setFont(new Font("Arial", 18));
		HBox hbox1 = new HBox(itemPress, itemImage, itemNum);
		hbox1.setSpacing(10);
		hbox1.setAlignment(Pos.CENTER);
		Canvas canvas = new Canvas(unit_size, unit_size); 
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image key = new Image("img/key.png"); 
		gc.drawImage(key, 0, 0, unit_size, unit_size); 
		Label itemKey = new Label("K = "); 
		itemKey.setFont(new Font("Arial", 24));
		itemKeyNum = new Label(" x " + keyNum);
		itemKeyNum.setFont(new Font("Arial", 24));
		HBox hbox2 = new HBox(itemKey, canvas, itemKeyNum);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setSpacing(10);
		VBox list = new VBox();
		list.getChildren().addAll(hbox1, hbox2);
		info.getChildren().addAll(health, health_box, countdown, timer, items, list); 
		info.setAlignment(Pos.BASELINE_CENTER);
		info.setSpacing(20); 
		// p holds everything
		BorderPane p = new BorderPane();
		p.setCenter(grid); p.setTop(mb); p.setRight(info);
		// there should be someway to zoom up automatically without influencing coordinates (do later or ignore)
		root.getChildren().add(p);
		root.getChildren().add(character);
		//**********************************************
		drawMap();
		//**********************************************
		Scene scene = new Scene(root, 1000, 625); 
		stage.setScene(scene); stage.setTitle("Puzzle Platformer");
		stage.show();
		return scene;
	}
	/**
	 * 
	 * @param health_status
	 * @author Lize
	 * @author Eujin Ko 
	 */
	private void updateHealth(int health_status) {
		// the size of canvas bases on the number of hearts (modify later)
		clearCanvas(health_box);
		GraphicsContext gc = health_box.getGraphicsContext2D();

		Image heart = new Image("img/heart.png"); 

		for(int i = 0; i< health_status; i++) {
			gc.drawImage(heart, i*50, 0, 50, 50); 
		}
		if(health_status == 0) {
			gameOverMessage();
		}
	}

	/**
	 * This function clears canvas
	 * @param canvas to be cleared
	 * @author Eujin Ko 
	 */
	private void clearCanvas(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}


	/**
	 * Calls tick using ControllerCollections & set up current velocity
	 * @author Eujin Ko
	 */
	private void tick() {
		handleCharacterVelocity();
		controller.callModelTick();


	}
	/**
	 * Setup current Velocity according to the key pressed
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	private void handleCharacterVelocity() {
		int moveX=0; int moveY=0;
		if(JUMP && character_controller.returnJumpStatus() == false) {
			moveY = -MOVE_SIZE*10;
			character_controller.toggleJumpStatus();
		}
//		else if(UP) {
//			moveY = -MOVE_SIZE;
//		}else if(DOWN) {
//			moveY = MOVE_SIZE;
//		}
		
		if(RIGHT) {
			moveX = MOVE_SIZE;
		}
		if(LEFT) {
			moveX = -MOVE_SIZE;
		}
		character_controller.addVelocity(moveX, moveY);
	}

	public Node callCharacter() {
		return character;
	}
	public GridPane callGrid() {
		return grid;
	}

	/**
	 * Fetches the message received from observable, updates the view
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	@Override
	public void update(Observable o, Object arg) {

		//		try {
		//			Thread.sleep(1000);
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		CollectionsMessage msg = (CollectionsMessage) arg;
		CharacterMoveMessage char_msg = msg.getCharacterMoveMessage();
		//System.out.println(character_controller.getPlayerPosition().getCordX()+" , "+ character_controller.getPlayerPosition().getCordY());
		int health_status = msg.returnHealthStatus();
		healthLeft = health_status;
		boolean win_status = msg.returnWinStatus();
		int keyPos = msg.returnKeyStatus();
		boolean portal_status = msg.returnPortalStatus();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if(char_msg != null) {
					characterMoveTransition(char_msg);
					updateHealth(health_status);
					if (keyPos != -1) {
						pickUpKey(keyPos);
					}
					if (portal_status) {
						setUpPortal();
						ifPortal = false;
					}
					stageClearedMessage(win_status);
				}
			}

		});
	}

	//lize
	public void setUpPortal() {
		level = HARD_PART2;
		readFile(); //get new map
		drawMap(); // update new map
		System.out.println(healthLeft+"****************");
		clearCanvas(health_box);
		//GraphicsContext gc = health_box.getGraphicsContext2D();
		//Image heart = new Image("img/heart.png"); 
		//for(int i = 0; i < healthLeft; i++) {
		//	gc.drawImage(heart, i*50, 0, 50, 50); 
		//}
		
	}

	//lize (disappear on the map and show in the item bag)
	@SuppressWarnings("static-access")
	public void pickUpKey(int keyPos) {
		boolean flag = false;         
		int gridSize = grid.getChildren().size();
		int j = keys[keyPos * 2] / unit_size;
		int i = keys[keyPos * 2 + 1] / unit_size;
		for (int k = 0; k < gridSize; k++) {
			Object temp = grid.getChildren().get(k);
			if (temp instanceof Canvas) {
				Canvas target = (Canvas) temp;
				if (grid.getColumnIndex(target) == j && grid.getRowIndex(target) == i) {
					grid.getChildren().remove(k);
					keys[keyPos * 2] = -100;
					keys[keyPos * 2 + 1] = -100;
					controller.callModelAddKeys(keys); 
					flag = true;
					break;
				}
			}
		}
		// show in the item bag
		if (flag) {
			keyNum += 1;
			itemKeyNum.setText(" x " + keyNum);
		}

	}


	/**
	 * Parses CharacterMoveMessage and moves the character object from the scene
	 * TODO: Need to setup Gravity
	 * @param msg CharacterMoveMessage which contains information for the movement of character
	 * @author Eujin Ko
	 */
	public void characterMoveTransition(CharacterMoveMessage msg) {
		if(msg == null) {
			return;
		}

		int prevX = msg.getXMoveFrom();
		int prevY = msg.getYMoveFrom();
		int curX = msg.getXMoveTo();
		int curY = msg.getYMoveTo();
//		System.out.println(prevX+","+prevY+"->"+curX+","+curY);
		
	    Path path = new Path();
//	    path.getElements().add(new MoveTo(prevX+character_size[0]/2, prevY+unit_size));
//	    path.getElements().add(new LineTo(curX+character_size[0]/2, curY+unit_size));
	    path.getElements().add(new MoveTo(prevX+character_size[0]/2, prevY+unit_size+character_size[1]/2));
	    path.getElements().add(new LineTo(curX+character_size[0]/2, curY+unit_size+character_size[1]/2));


	    DenseFogModel.set(prevX, curX, prevY, curY, wall3, wall2,wall1, wall4);
	    
	    
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(100));
		pathTransition.setNode(character); // Circle is built above
		pathTransition.setPath(path);
		pathTransition.play();		
	}

	/**
	 * Sends a Stage cleared message, stops the view, stops the timer
	 * @param win_status indicates whether player has won the stage or not
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	public void stageClearedMessage(boolean win_status) 
	{
		if(win_status == false) {
			return;
		}
		RIGHT = false;
		LEFT = false;
		JUMP = false;
        Alert alert = new Alert(AlertType.INFORMATION); 
        alert.setTitle("Message");
        alert.setHeaderText("Message");
        alert.setContentText("To the next stage!");
        animationTimer.stop();
        timeline.stop();
        alert.showAndWait(); 
		
	}
	/**
	 * Sends a Game Over message, stops the view, stops the timer
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	public void gameOverMessage() 
	{
		RIGHT = false;
		LEFT = false;
		JUMP = false;
        Alert alert = new Alert(AlertType.INFORMATION);  
        alert.setTitle("Message");
        alert.setHeaderText("Message");
        alert.setContentText("GAME OVER");
        animationTimer.stop();
        timeline.stop();
        alert.show();
	}
	
	
	// Private Event Handler classes
	
	
	/**
	 * Private class that handles movement of the main character depends on KeyEvents when key pressed
	 * Available Movement: UP, DOWN, RIGHT, LEFT, JUMP
	 * @author Eujin Ko
	 *
	 */
	class MovementPressed implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
		    
			switch(event.getCode()) {
			
//			case DOWN:
//				System.out.println("DOWN");
//				DOWN = true;
//				break;
//			case UP:
//				System.out.println("UP");
//				UP = true;
//				break;
			case RIGHT:
				System.out.println("RIGHT");
				RIGHT = true;
				break;
			case LEFT:
				System.out.println("LEFT");
				LEFT = true;
				break;
			case SPACE:
				System.out.println("JUMP");
				JUMP = true;
				break;
			default:
				break;
			}

		}
		

	}
	/**
	 * Private class that handles movement of the main character depends on KeyEvents when key released
	 * Available Movement: UP, DOWN, RIGHT, LEFT, JUMP
	 * @author Eujin Ko
	 * @author Lize Chen
	 *
	 */
	class MovementReleased implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
		    
			switch(event.getCode()) {
			
//			case DOWN:
//				DOWN = false;
//				break;
//			case UP:
//				UP = false;
//				break;
			case RIGHT:
				RIGHT = false;
				break;
			case LEFT:
				LEFT = false;
				break;
			case SPACE:
				JUMP = false;
				break;
    		case K:
    			System.out.println("Pressed K!");
    			// get player's current coordinate
    			int curr_x = character_controller.getPlayerPosition().getCordX();
    			int curr_y = character_controller.getPlayerPosition().getCordY();
    			// return surrounding doors' positions
    			int[] surrounding = returnSurroundingDoors(curr_x, curr_y);
    			// if true, then disapear the door and update the label
    			if (keyNum > 0) {
    				openDoors(surrounding);
    			}
    			break;
			default:
				break;
			}

		}

		//lize
		@SuppressWarnings("static-access")
		public void openDoors(int[] surrounding) {
			boolean flag = false; // indicate if open one or more doors
			for (int g = 0; g < surrounding.length / 2; g++) {
				if (surrounding[g * 2] == -100) {
					continue;
				}
				int j = surrounding[g * 2] / unit_size;
				int i = surrounding[g * 2 + 1] / unit_size;
				// only iteration works (one key for multiple doors)
				Iterator<Node> itr = grid.getChildren().iterator();
				while (itr.hasNext()) {
					Object temp = itr.next();
					if (temp instanceof Canvas) {
						Canvas target = (Canvas) temp;
						if (grid.getColumnIndex(target) == j && grid.getRowIndex(target) == i) {
							itr.remove();
							doors[g * 2] = -100;
							doors[g * 2 + 1] = -100;
							flag = true;
						}
					}
				}
			} // one key can open all the doors surrounded, I set it for making trap
			if (flag) {
				keyNum--;
				itemKeyNum.setText(" x " + keyNum);
			}
		}

		//lize
		public int[] returnSurroundingDoors(int x, int y) {
//			System.out.println("x= "+x+" y= "+y);
			int[] temp = {-100,-100,-100,-100,-100,-100,-100,-100,-100,-100};
			for (int k = 0; k < doors.length / 2; k++) {
				if (doors[k * 2] <= x + unit_size && doors[k * 2] + unit_size * 2 >= x) {
					if (doors[k * 2 + 1] <= y + unit_size && doors[k * 2 + 1] + unit_size * 2 >= y) {
						// update to temp[]
						temp[2 * k] = doors[2 * k]; 
						temp[2 * k + 1] = doors[2 * k + 1];
					}
				}
			}
			//check
/*			for (int k = 0; k < temp.length /2;k++) {
				System.out.println(doors[2*k]+", "+doors[2*k+1]);
			}
			//check
			for (int k = 0; k < temp.length /2;k++) {
				System.out.println(temp[2*k]+", "+temp[2*k+1]);
			}*/
			
			return temp;
		}

	}
}
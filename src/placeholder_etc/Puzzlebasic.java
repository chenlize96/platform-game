package placeholder_etc;


import java.io.File;
import java.util.Scanner;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//perry
public class Puzzlebasic extends Application {
	private static final int MOVE = 0;
	// 25 x 25 pixel square
    final int SIZE = 50;
    // Points needed to draw a triangle
    final int TRIANGLE = 3;
    private int y = 1;
    private int x = 1;
    Button nextMove;
    GraphicsContext gc;
	public char[][] maze;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Process the input file and store it in a 2D array of characters
        char[][] maze = readMaze("PublicTestCases/basic.txt");
        primaryStage.setTitle("Puzzle");

        Group root = new Group();
        // Canvas to scale to the size of the input file
        Canvas canvas = new Canvas(maze[0].length * SIZE, maze.length * SIZE);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        initScreenMaze(gc, maze);
        // Text field to take in maze command
        TextField command = new TextField();

        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Input Area + nextMove Button
        Label cmdLabel = new Label("Type Command in TextField");
        HBox hb = new HBox(3);

        // Input + Text Output
        VBox vb = new VBox(2);
        
        setupNodes(hb, cmdLabel, vb, command);
        nextMove.setOnAction(new CommandHandler(command));

        p.setCenter(canvas);
        p.setBottom(vb);
        
        primaryStage.setScene(new Scene(p));
        primaryStage.show();
    }
    
    class CommandHandler implements EventHandler<ActionEvent> {
    	private TextField command;

    	CommandHandler(TextField command) {
    		this.command = command;
    	}

    	/*
    	 * Button EventHandler to take input command
    	 * when button is clicked.
    	 */
    	@Override
    	public void handle(ActionEvent event) {
    		parseLine(command.getText());

    	}

    }
    
    /*
     * Sets up the TextField, label, and button to be
     * at the bottom
     */
    private void setupNodes(HBox hb, Label cmd, VBox vb, TextField inputCmd) {

        nextMove = new Button("Simulation Step");

        hb.setSpacing(15);
        hb.getChildren().add(cmd);
        hb.getChildren().add(nextMove);

        vb.getChildren().add(hb);
        vb.getChildren().add(inputCmd);
    }


	/*
     * Iterates over the 2D array maze and draws the shape that corresponds
     * to the character.
     */
    public void initScreenMaze(GraphicsContext gc, char[][] maze) {
        // default is yellow because it is a corn maze
        gc.setFill(Color.DARKGRAY);

        // Two arrays that correspond to the points of the triangle to be drawn
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {

                // * fill in as yellow because it is a corn wall
                if (maze[i][j] == '*') {
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);

                } else if (maze[i][j] == 'S') { // Color blue for start position
                                                // in maze
                    gc.setFill(Color.BLUE);
                    double[] yPoints = new double[] {
                            (double) (i * SIZE + SIZE), (double) SIZE * i,
                            (double) (SIZE * i + SIZE) };
                    double[] xPoints = new double[] { (double) j * SIZE,
                            (double) (SIZE * j + (SIZE / 2)),
                            (double) (SIZE * j + SIZE) };
                    // Could just pass arrays straight into fillPolygon
                    gc.fillPolygon(xPoints, yPoints, TRIANGLE);
                    gc.setFill(Color.DARKGRAY); // Set color back to yellow

                } else if (maze[i][j] == 'E') { // Color square green for end
                    gc.setFill(Color.GREEN);
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
                    gc.setFill(Color.DARKGRAY); // Set color back to yellow
                }

            }
        }

    }

    /*
     * readMaze accepts a string argument that is the file name
     * Process the file and place its contents in the 2D character
     * array Maze. This 2D array is returned so that another function
     * can handle drawing on the Canvas.
     */
    public char[][] readMaze(String filename) {
        char[][] maze = null;

        try {
            Scanner in = new Scanner(new File(filename));
            int width = in.nextInt(), height = in.nextInt();
            in.nextLine();

            // Initialize array to match input file
            maze = new char[height][width];

            // Fill array with input characters
            for (int i = 0; i < height; i++) {
                int j = 0;
                String s = in.nextLine();
                for (char c : s.toCharArray()) {
                    maze[i][j++] = c;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maze;
    }
    
    public void parseLine(String line) {
        System.out.println("reading " + line); // Print line for debugging
        if (line.toLowerCase().compareTo("up") == 0 && y - MOVE >= 0
                && maze[y - MOVE][x] != '*') {
            move(y - MOVE, x);
        } 
    }
    
    /**
     * This function 'erases' the old triangle and redraws
     * it in the next move location to make it appear that
     * the triangle is navigating the maze. int newY and 
     * int newX represent the coordinates of the valid move. 
     */
    public void move(int newY, int newX) {
        gc.clearRect(x * SIZE, y * SIZE, SIZE, SIZE);
        double[] yPoints = new double[] { (double) (newY * SIZE + SIZE),
                (double) SIZE * newY, (double) (SIZE * newY + SIZE) };
        double[] xPoints = new double[] { (double) newX * SIZE,
                (double) (SIZE * newX + (SIZE / 2)),
                (double) (SIZE * newX + SIZE) };
        gc.setFill(Color.BLUE);
        gc.fillPolygon(xPoints, yPoints, TRIANGLE);
        x = newX;
        y = newY;
    }

}
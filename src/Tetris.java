
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Tetris extends Application {
	// The variables
	public static final int MOVE = 25;
	public static final int BLOCK_SIZE = 25;
	public static int XMAX = BLOCK_SIZE * 12;
	public static int YMAX = BLOCK_SIZE * 25;
	public static int[][] MESH = new int[XMAX / BLOCK_SIZE][YMAX / BLOCK_SIZE];
	private static Pane group = new Pane();
	private static Pentomino object;
	private static Scene scene = new Scene(group, XMAX + 150, YMAX);
	public static int score = 0;
	private static int top = 0;
	private static boolean game = true;
	private static Pentomino nextObj = Controller.makeRect();
	private static int linesNo = 0;

	private boolean autoplay = false;

	private static Stage stage;

	JSONHandler highScoreSaver = new JSONHandler();
	Bot bot = new Bot();
	private static int BotDecisionFinished = -1;

	private int laptime = 0;

	public static void main(String[] args) {
		launch(args);
	}

	public Tetris(){}

	@Override
    public void start(Stage primaryStage) throws Exception{
		stage = primaryStage;

	    Parent root = FXMLLoader.load(getClass().getResource("fxml\\main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tetris: Such a fun game with Pentominos");

        //Disable fullscreen button --> code taken from: https://stackoverflow.com/questions/32282646/how-to-lock-javafx-fullscreen-mode
        
        primaryStage.fullScreenProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue != null && !newValue.booleanValue())
                    primaryStage.setFullScreen(false);
            }
        });
	}

	public void startGame(){
		for (int[] a : MESH) {
			Arrays.fill(a, 0);
		}

		Line line = new Line(XMAX, 0, XMAX, YMAX);

		//Add horizontal lines to the Mesh
		for(int i = 1 ; i <= YMAX / BLOCK_SIZE; i++){
			Line Mesh_line = new Line(XMAX, i*BLOCK_SIZE, 0, i*BLOCK_SIZE);
			group.getChildren().add(Mesh_line);
		}

		//Add vertical lines to the Mesh
		for(int i = 1 ; i <= XMAX / BLOCK_SIZE; i++){
			Line Mesh_line = new Line(i*BLOCK_SIZE, YMAX, i*BLOCK_SIZE, 0);
			group.getChildren().add(Mesh_line);
		}

		Text scoretext = new Text("Score: ");
		Button BTN_autoplay = new Button("AutoPlay ON");
		BTN_autoplay.setStyle("-fx-font: 18 arial; -fx-border-radius: 5em");
		BTN_autoplay.setTranslateX(XMAX + 10);
		BTN_autoplay.setTranslateY(YMAX - 120);

		scoretext.setStyle("-fx-font: 20 arial;");
		scoretext.setY(50);
		scoretext.setX(XMAX + 5);
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arial;");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.GREEN);
		group.getChildren().addAll(scoretext, line, level, BTN_autoplay);

		Pentomino a = nextObj;
		group.getChildren().addAll(a.a, a.b, a.c, a.d, a.e);
		
		BTN_autoplay.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
				autoplay = !autoplay;
				
				if(autoplay){
					BTN_autoplay.setText("AutoPlay OFF");
				}else{
					BTN_autoplay.setText("AutoPlay ON");
				}
			}
		});
		//Make the button not interact with key arrows
		BTN_autoplay.setFocusTraversable(false);

		moveOnKeyPress(a);
		object = a;
		nextObj = Controller.makeRect();
		stage.setScene(scene);
		stage.setTitle("Tetris: But with Pentominos");
		stage.show();


		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
			long ThreadCounter = 0;

			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
								|| object.d.getY() == 0 || object.e.getY() == 0)
							top++;
						else
							top = 0;

						if (top == 2) {
							// GAME OVER

							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							group.getChildren().add(over);
							game = false;
							highScoreSaver.TryToInsertHighScore(score, laptime);
						}

						if (game) {

							if(autoplay){

								//First decision or Last decision was finished
								if(BotDecisionFinished == -1 || BotDecisionFinished == 2){
									System.out.println("test");
									bot.sendPieceCoords(object);
									String choice = bot.TakeAChoice(MESH);

									switch (choice) {
									case "left":
										Controller.MoveLeft(object);
										break;
									case "right":
										Controller.MoveRight(object);
									case "turn":
										MoveTurn(object);
									case "down":
										MoveDown(object);
									case "space":
										DropPiece(object);
									default:
										break;
								}
								}
							}

							ThreadCounter++;
							if(ThreadCounter%2 == 0) laptime++;
							MoveDown(object);
							scoretext.setText("Score: " + Integer.toString(score));
							level.setText("Lines: " + Integer.toString(linesNo));
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 500); 
	}

	public static void SetStateOfBotDecision(int state){
		BotDecisionFinished = state;
	}

	private void moveOnKeyPress(Pentomino form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(autoplay) return;

				switch (event.getCode()) {
				case RIGHT:
					Controller.MoveRight(form);
					break;
				case DOWN:
					MoveDown(form);
					score++;
					break;
				case LEFT:
					Controller.MoveLeft(form);
					break;
				case UP:
					MoveTurn(form);
					break;
				case SPACE:
					DropPiece(form);
					break;
				}
			}
		});
	}

	private boolean piecePlaced = false;

	private void DropPiece(Pentomino form){
		if(game){
			while (!piecePlaced && game) {
				MoveDown(form);
				score++;
			}

			if(! (form.a.getY() == 0 || form.b.getY() == 0 || form.c.getY() == 0 || form.d.getY() == 0 || form.e.getY() == 0))
				top = 0;
			
			piecePlaced = false;
		}
	}

	private void MoveTurn(Pentomino form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		Rectangle e = form.e;

		// System.out.println(
		// 	"A: " + a.getY() + '\n' +
		// 	"B: " + b.getY() + '\n' +
		// 	"C: " + c.getY() + '\n' +
		// 	"D: " + d.getY() + '\n' +
		// 	"E: " + e.getY() 
		// );

		switch (form.getName()) {

		case "l":
			if (f == 1 && cB(a, -2, 3) && cB(b, -1, 2) && cB(c, 0, 1) && cB(d, 1, 0) && cB(e, 0, -1)) {

				MoveLeft(form.a,2);
				MoveDown(form.a,3);
				MoveLeft(form.b);
				MoveDown(form.b,2);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveUp(form.e);

				form.changeForm();
				break;
			}

			if (f == 2 && cB(a, 1, -3) && cB(b, 1, -2) && cB(d, -1, -3) && cB(e, -1, 0)) {

				MoveUp(form.a,3);
				MoveRight(form.a);
				MoveRight(form.b);
				MoveUp(form.b,2);
				MoveLeft(form.d);
				MoveUp(form.d,3);
				MoveLeft(form.e);

				form.changeForm();
				break;
			}

			if (f == 3 && cB(a, 0, 2) && cB(c, 2, -2) && cB(d, -1, 1) && cB(e, 1, -1)) {

				MoveDown(form.a,2);
				MoveRight(form.c,2);
				MoveUp(form.c,2);
				MoveLeft(form.d);
				MoveDown(form.d);
				MoveRight(form.e);
				MoveUp(form.e);
				
				form.changeForm();
				break;
			}

			if (f == 4 && cB(a, 1, -2) && cB(c, -2, 1) && cB(d, 1, 2) && cB(e, 0, 2)) {
				
				MoveRight(form.a);
				MoveUp(form.a,2);
				MoveLeft(form.c,2);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveDown(form.d,2);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			break;
		case "s":

			if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {

				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d,2);

				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {

				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d,2);

				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {

				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d,2);

				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {

				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d,2);

				form.changeForm();
				break;
			}
			break;
		case "t":
			if (f == 1 && cB(b, 0, -1) && cB(c, 0, -1) && cB(d, -1, 0) && cB(e, -1, 0)) {

				MoveDown(form.b);
				MoveDown(form.c);
				MoveLeft(form.d);
				MoveLeft(form.e);

				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, 0) && cB(d, 1, 1) && cB(c, 0, 1)) {				
				MoveRight(form.a);
				MoveDown(form.d);
				MoveRight(form.d);
				MoveDown(form.c);

				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, 1, 0) && cB(e, 0, -1) && cB(d, 1, -1)) {
				//DebugColors(a,b,c,d,e);

				MoveRight(form.a);
				MoveUp(form.e);
				MoveUp(form.d);
				MoveRight(form.d);

				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
				
				MoveLeft(form.a,2);
				MoveUp(form.b);
				MoveUp(form.c,2);
				MoveLeft(form.d);
				MoveRight(form.e);
				MoveDown(form.e);

				form.changeForm();
				break;
			}
			break;
		case "z":
			if ( (f == 1 || f == 3) && cB(a, 2, 0) && cB(d, -1, -1) && cB(e, -2, 0)) {

				MoveRight(form.a,2);
				MoveDown(form.b);
				MoveRight(form.b);
				MoveUp(form.d);
				MoveLeft(form.d);
				MoveLeft(form.e,2);

				form.changeForm();
				break;
			}
			if ( (f == 2 || f == 4) && cB(a, -2, 0) && cB(b, -1, 1) && cB(d, 1, -1) && cB(e, 2, 0)) {

				MoveLeft(form.a,2);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveRight(form.e,2);

				form.changeForm();
				break;
			}
			break;
		case "i":
			if ( (f == 1 || f == 3) && cB(a, 2, -2) && cB(b, 1, -1) && cB(d, -1, -1) && cB(e, -2, -3)) {

				MoveUp(form.a,2);
				MoveRight(form.a,2);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveUp(form.e,3);
				MoveLeft(form.e,2);

				form.changeForm();
				break;
			}
			if ( (f == 2 || f == 4) && cB(a, -2, 2) && cB(b, -1, 1) && cB(d, 1, 1) && cB(e, 2, 3)) {

				MoveLeft(form.a,2);
				MoveDown(form.a,2);
				MoveLeft(form.b);
				MoveDown(form.b);
				MoveRight(form.d);
				MoveUp(form.d);
				MoveRight(form.e,2);
				MoveDown(form.e,3);

				form.changeForm();
				break;
			}
			break;
		case "x":
			break;
		case "v":
			if (f == 1 && cB(b, 0, 2) && cB(c, 0, 2)) {
				// DebugColors(a, b, c, d, e);

				MoveDown(form.b,2);
				MoveDown(form.c,2);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(a, 2, 0) && cB(d, 2, 0)) {
				
				MoveRight(form.a,2);
				MoveRight(form.d,2);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(b, 0, -2) && cB(e, 0, -2)) {
				
				MoveUp(form.b,2);
				MoveUp(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(a, -2, 0) && cB(c, 0, -2) && cB(d, -2, 0) && cB(e, 0, 2)) {
				
				MoveLeft(form.a,2);
				MoveUp(form.c,2);
				MoveLeft(form.d,2);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			break;

		case "u":
			if (f == 1 && cB(b, 1, 0) && cB(d, 2, -1) && cB(e, 0, -1)) {

				MoveRight(form.b);
				MoveRight(form.d,2);
				MoveUp(form.d);
				MoveUp(form.e);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(b, -1, 0) && cB(c, 0, 1) && cB(d, -1, 1)) {
				
				MoveLeft(form.b);
				MoveDown(form.c);
				MoveLeft(form.d);
				MoveDown(form.d);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(c, 2, -2) && cB(d, 1, -1) && cB(d, -1, 0)) {
				
				MoveUp(form.c,2);
				MoveRight(form.c,2);
				MoveRight(form.d);
				MoveUp(form.d);
				MoveLeft(form.e);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(c, -2, 1) && cB(d, -2, 1) && cB(e, 1, 1)) {
				
				MoveLeft(form.c,2);
				MoveDown(form.c);
				MoveLeft(form.d,2);
				MoveDown(form.d);
				MoveDown(form.e);
				MoveRight(form.e);

				form.changeForm();
				break;
			}
			break;
		case "w":
			if (f == 1 && cB(a, -1, 0) && cB(d, 0, -2) && cB(e, 1, 0)) {

				MoveLeft(form.a);
				MoveUp(form.d,2);
				MoveRight(form.e);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(c, 0, -1) && cB(d, 0, 1) && cB(e, -2, 0)) {
				
				MoveUp(form.c);
				MoveDown(form.d);
				MoveLeft(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(a, -1, 0) && cB(c, 0, 2) && cB(e, 1, 0)) {
				
				MoveLeft(form.a);
				MoveDown(form.c,2);
				MoveRight(form.e);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(a, 2, 0) && cB(c, 0, -1) && cB(d, 0, 1)) {
				
				MoveRight(form.a,2);
				MoveUp(form.c);
				MoveDown(form.d);

				form.changeForm();
				break;
			}
			break;
		case "y":
			if (f == 1 && cB(b, -1, 0) && cB(c, -1, 0) && cB(d, 1, -1) && cB(e, 2, -2)) {

				MoveLeft(form.b);
				MoveLeft(form.c);
				MoveRight(form.d);
				MoveUp(form.d);
				MoveRight(form.e,2);
				MoveUp(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(d, -1, 1) && cB(e, -2, -2)) {
				
				MoveLeft(form.d);
				MoveDown(form.d);
				MoveLeft(form.e,2);
				MoveUp(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(a, 1, 1) && cB(e, -2, 2)) {
				
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.e,2);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(a, -1, -2) && cB(b, 1, -1) && cB(c, 1, -1) && cB(d, 0, -1) && cB(e, 2, 1)) {
				
				MoveLeft(form.a);
				MoveUp(form.a,2);
				MoveRight(form.b);
				MoveUp(form.b);
				MoveRight(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveRight(form.e,2);
				MoveDown(form.e);

				form.changeForm();
				break;
			}
			break;
		case "p":
			if (f == 1 && cB(e, 2, -1)) {

				MoveRight(form.e,2);
				MoveUp(form.e);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(e, -1, -2)) {
				
				MoveLeft(form.e);
				MoveUp(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(e, -2, 1)) {
				
				MoveLeft(form.e,2);
				MoveDown(form.e);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(e, 1, 2)) {
				
				MoveRight(form.e);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			break;
		case "n":
			if (f == 1 && cB(c, -1, 0) && cB(d, -1, -2) && cB(e, -2, -3)) {

				MoveLeft(form.c);
				MoveLeft(form.d);
				MoveUp(form.d,2);
				MoveLeft(form.e,2);
				MoveUp(form.e,3);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(c, 1, 0) && cB(d, 1, 2) && cB(e, -2, 2)) {
				
				MoveRight(form.c);
				MoveRight(form.d);
				MoveDown(form.d,2);
				MoveLeft(form.e,2);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(c, -1, 0) && cB(d, -2, 1) && cB(e, 2, -1)) {
				
				MoveLeft(form.c);
				MoveLeft(form.d,2);
				MoveDown(form.d);
				MoveRight(form.e,2);
				MoveUp(form.e);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(c, 1, 0) && cB(d, 2, -1) && cB(e, 2, 2)) {
				
				MoveRight(form.c);
				MoveRight(form.d,2);
				MoveUp(form.d);
				MoveRight(form.e,2);
				MoveDown(form.e,2);

				form.changeForm();
				break;
			}
			break;
		case "f":
			if (f == 1 && cB(a, -1, 0) && cB(b, 0, 1)) {

				MoveLeft(form.a);
				MoveDown(form.b);

				form.changeForm();
				break;
			}
			if ( f == 2 && cB(a, 1, 0) && cB(e, 0, 1)) {
				
				MoveRight(form.a);
				MoveDown(form.e);

				form.changeForm();
				break;
			}
			if ( f == 3 && cB(e, 0, -1) && cB(d, 1, 0)) {
				
				MoveUp(form.e);
				MoveRight(form.d);

				form.changeForm();
				break;
			}
			if ( f == 4 && cB(b, 0, -1) && cB(d, -1, 0)) {
				
				MoveUp(form.b);
				MoveLeft(form.d);

				form.changeForm();
				break;
			}
			break;
		}
	}

	private void DebugColors(Rectangle a, Rectangle b, Rectangle c, Rectangle d, Rectangle e)
	{
		a.setFill(Color.BLACK);
		b.setFill(Color.RED);
		c.setFill(Color.GREEN);
		d.setFill(Color.PURPLE);
		e.setFill(Color.GOLD);
	}

	private void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		int full = 0;
		for (int i = 0; i < MESH[0].length; i++) {
			for (int j = 0; j < MESH.length; j++) {
				if (MESH[j][i] == 1)
					full++;
			}
			if (full == MESH.length) lines.add(i);

			full = 0;
		}

		double multiplier = 1;
		
		for(int i = 0 ; i < lines.size();i++)
			multiplier = multiplier + (multiplier*0.3);

		while (lines.size() > 0)
		{
			for (Node node : pane.getChildren()) {
				if (node instanceof Rectangle)
					rects.add(node);
			}
			score += (int) 25*multiplier;
			linesNo++;

			for (Node node : rects) {
				Rectangle a = (Rectangle) node;
				if (a.getY() == lines.get(0) * BLOCK_SIZE) {
					MESH[(int) a.getX() / BLOCK_SIZE][(int) a.getY() / BLOCK_SIZE] = 0;
					pane.getChildren().remove(node);
				} else
					newrects.add(node);
			}

			for (Node node : newrects) {
				Rectangle a = (Rectangle) node;
				if (a.getY() < lines.get(0) * BLOCK_SIZE) {
					MESH[(int) a.getX() / BLOCK_SIZE][(int) a.getY() / BLOCK_SIZE] = 0;
					a.setY(a.getY() + BLOCK_SIZE);
				}
			}
			lines.remove(0);
			rects.clear();
			newrects.clear();
			for (Node node : pane.getChildren()) {
				if (node instanceof Rectangle)
					rects.add(node);
			}
			for (Node node : rects) {
				Rectangle a = (Rectangle) node;
				try {
					MESH[(int) a.getX() / BLOCK_SIZE][(int) a.getY() / BLOCK_SIZE] = 1;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("DEBUG: Array out of Bounds " + e.getMessage());
				}
			}
			rects.clear();
		}
	}

	private void MoveDown(Rectangle rect) {
		rect.setY(rect.getY() + MOVE);
	}

	private void MoveRight(Rectangle rect) {
		rect.setX(rect.getX() + MOVE);
	}

	private void MoveLeft(Rectangle rect) {
		rect.setX(rect.getX() - MOVE);
	}

	private void MoveUp(Rectangle rect) {
		rect.setY(rect.getY() - MOVE);
	}


	private void MoveDown(Rectangle rect, int times){
		rect.setY(rect.getY() + (times * MOVE));
	}

	private void MoveRight(Rectangle rect, int times){
		rect.setX(rect.getX() + (times * MOVE));
	}

	private void MoveLeft(Rectangle rect, int times){
		rect.setX(rect.getX() - (times * MOVE));
	}

	private void MoveUp(Rectangle rect, int times){
		rect.setY(rect.getY() - (times * MOVE));
	}

	private void MoveDown(Pentomino form) {
		if (form.a.getY() == YMAX - BLOCK_SIZE || form.b.getY() == YMAX - BLOCK_SIZE || form.c.getY() == YMAX - BLOCK_SIZE
				|| form.d.getY() == YMAX - BLOCK_SIZE || form.e.getY() == YMAX - BLOCK_SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form) || moveE(form)) {
			MESH[(int) form.a.getX() / BLOCK_SIZE][(int) form.a.getY() / BLOCK_SIZE] = 1;
			MESH[(int) form.b.getX() / BLOCK_SIZE][(int) form.b.getY() / BLOCK_SIZE] = 1;
			MESH[(int) form.c.getX() / BLOCK_SIZE][(int) form.c.getY() / BLOCK_SIZE] = 1;
			MESH[(int) form.d.getX() / BLOCK_SIZE][(int) form.d.getY() / BLOCK_SIZE] = 1;
			MESH[(int) form.e.getX() / BLOCK_SIZE][(int) form.e.getY() / BLOCK_SIZE] = 1;
			RemoveRows(group);

			Pentomino a = nextObj;
			nextObj = Controller.makeRect();
			object = a;
			group.getChildren().addAll(a.a, a.b, a.c, a.d, a.e);
			moveOnKeyPress(a);
			piecePlaced = true;
		}

		if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
				&& form.d.getY() + MOVE < YMAX && form.e.getY() + MOVE < YMAX) {
			int movea = MESH[(int) form.a.getX() / BLOCK_SIZE][((int) form.a.getY() / BLOCK_SIZE) + 1];
			int moveb = MESH[(int) form.b.getX() / BLOCK_SIZE][((int) form.b.getY() / BLOCK_SIZE) + 1];
			int movec = MESH[(int) form.c.getX() / BLOCK_SIZE][((int) form.c.getY() / BLOCK_SIZE) + 1];
			int moved = MESH[(int) form.d.getX() / BLOCK_SIZE][((int) form.d.getY() / BLOCK_SIZE) + 1];
			int movee = MESH[(int) form.e.getX() / BLOCK_SIZE][((int) form.e.getY() / BLOCK_SIZE) + 1];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved && moved == movee) {
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
				form.e.setY(form.e.getY() + MOVE);
			}
		}
	}

	private boolean moveA(Pentomino form) {
		return (MESH[(int) form.a.getX() / BLOCK_SIZE][((int) form.a.getY() / BLOCK_SIZE) + 1] == 1);
	}

	private boolean moveB(Pentomino form) {
		return (MESH[(int) form.b.getX() / BLOCK_SIZE][((int) form.b.getY() / BLOCK_SIZE) + 1] == 1);
	}

	private boolean moveC(Pentomino form) {
		return (MESH[(int) form.c.getX() / BLOCK_SIZE][((int) form.c.getY() / BLOCK_SIZE) + 1] == 1);
	}

	private boolean moveD(Pentomino form) {
		return (MESH[(int) form.d.getX() / BLOCK_SIZE][((int) form.d.getY() / BLOCK_SIZE) + 1] == 1);
	}

	private boolean moveE(Pentomino form) {
		return (MESH[(int) form.e.getX() / BLOCK_SIZE][((int) form.e.getY() / BLOCK_SIZE) + 1] == 1);
	}

	private boolean cB(Rectangle rect, int x, int y) {
		boolean xb = false;
		boolean yb = false;
		if (x >= 0)
			xb = rect.getX() + x * MOVE <= XMAX - BLOCK_SIZE;
		if (x < 0)
			xb = rect.getX() + x * MOVE >= 0;
		if (y >= 0)
			yb = rect.getY() - y * MOVE > 0;
		if (y < 0)
			yb = rect.getY() + y * MOVE < YMAX && (rect.getY() + y * MOVE) > 0;

		if ( (MESH.length <= ((int) rect.getX() / BLOCK_SIZE) + x) || (MESH[0].length <= ((int) rect.getY() / BLOCK_SIZE) - y) )
			return false;

		return xb && yb && MESH[((int) rect.getX() / BLOCK_SIZE) + x][((int) rect.getY() / BLOCK_SIZE) - y] == 0;
	}

}
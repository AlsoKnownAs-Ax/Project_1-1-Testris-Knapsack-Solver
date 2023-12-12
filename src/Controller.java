
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import PentominosUtils.PentominoBuilder;
import PentominosUtils.PentominoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {
	public static final int MOVE = Tetris.MOVE;
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int XMAX = Tetris.XMAX;
	public static int YMAX = Tetris.YMAX;
	public static int[][] MESH = Tetris.MESH;

    private Stage stage;
    private Scene scene;
    private Parent root; 


    @FXML
    void StartGame(ActionEvent event) throws IOException {
        Tetris tetris = new Tetris();

        tetris.startGame();
    }

    @FXML
    void ShowHighScores(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("fxml\\HighScoreScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

       setHighScoreTexts();

    }

    private void setHighScoreTexts(){
        int[] scores = JSONHandler.GetScoresArray();
        String[] times = JSONHandler.GetTimesArray();

        for(int i = 1; i <= scores.length; i++){
            Text CurrentText = (Text) root.lookup("#top"+i+"Text");
            if(CurrentText != null) CurrentText.setText("Top "+i+": " + " Score: " + scores[i-1] + " Time: " + times[i-1]);
        }
    }

    @FXML
    void ShowTitleScreen(ActionEvent event) throws IOException {
        
        root = FXMLLoader.load(getClass().getResource("fxml\\main.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


	public static void MoveRight(Pentomino form) {
		if (form.a.getX() + MOVE <= XMAX - BLOCK_SIZE && form.b.getX() + MOVE <= XMAX - BLOCK_SIZE
				&& form.c.getX() + MOVE <= XMAX - BLOCK_SIZE && form.d.getX() + MOVE <= XMAX - BLOCK_SIZE && form.e.getX() + MOVE <= XMAX - BLOCK_SIZE) {
			int movea = MESH[((int) form.a.getX() / BLOCK_SIZE) + 1][((int) form.a.getY() / BLOCK_SIZE)];
			int moveb = MESH[((int) form.b.getX() / BLOCK_SIZE) + 1][((int) form.b.getY() / BLOCK_SIZE)];
			int movec = MESH[((int) form.c.getX() / BLOCK_SIZE) + 1][((int) form.c.getY() / BLOCK_SIZE)];
			int moved = MESH[((int) form.d.getX() / BLOCK_SIZE) + 1][((int) form.d.getY() / BLOCK_SIZE)];
			int movee = MESH[((int) form.e.getX() / BLOCK_SIZE) + 1][((int) form.e.getY() / BLOCK_SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved && moved == movee) {
				form.a.setX(form.a.getX() + MOVE);
				form.b.setX(form.b.getX() + MOVE);
				form.c.setX(form.c.getX() + MOVE);
				form.d.setX(form.d.getX() + MOVE);
				form.e.setX(form.e.getX() + MOVE);
			}
		}
	}

	public static void MoveLeft(Pentomino form) {
		if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
				&& form.d.getX() - MOVE >= 0 && form.e.getX() - MOVE >= 0) {
			int movea = MESH[((int) form.a.getX() / BLOCK_SIZE) - 1][((int) form.a.getY() / BLOCK_SIZE)];
			int moveb = MESH[((int) form.b.getX() / BLOCK_SIZE) - 1][((int) form.b.getY() / BLOCK_SIZE)];
			int movec = MESH[((int) form.c.getX() / BLOCK_SIZE) - 1][((int) form.c.getY() / BLOCK_SIZE)];
			int moved = MESH[((int) form.d.getX() / BLOCK_SIZE) - 1][((int) form.d.getY() / BLOCK_SIZE)];
			int movee = MESH[((int) form.e.getX() / BLOCK_SIZE) - 1][((int) form.e.getY() / BLOCK_SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved && movec == movee) {
				form.a.setX(form.a.getX() - MOVE);
				form.b.setX(form.b.getX() - MOVE);
				form.c.setX(form.c.getX() - MOVE);
				form.d.setX(form.d.getX() - MOVE);
				form.e.setX(form.e.getX() - MOVE);
			}
		}
	}


	/*	DEBUG COLORS: 
		* a - BLACK
		* b - RED
		* c - GREEN
		* d - PURPLE
		* e - GOLD
	*/

	public static Pentomino makeRect() {
		Random random = new Random();
		int block = random.nextInt((PentominoBuilder.GetPentominosLength())) + 1;
		String name = "";
		Rectangle a = new Rectangle(BLOCK_SIZE-1, BLOCK_SIZE-1), b = new Rectangle(BLOCK_SIZE-1, BLOCK_SIZE-1), c = new Rectangle(BLOCK_SIZE-1, BLOCK_SIZE-1),
				  d = new Rectangle(BLOCK_SIZE-1, BLOCK_SIZE-1), e = new Rectangle(BLOCK_SIZE - 1, BLOCK_SIZE - 1);

		switch (block) {
			case 1:
					/*
					* { ,b, }
					* {a,e,c}
					* { ,d, }
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					a.setY(BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2 + BLOCK_SIZE);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(BLOCK_SIZE);

					name = "x";
				break;
			case 2:
					/*
					* {a,b,c,d,e},
					*/
					// a.setX(XMAX / 2 - (2*BLOCK_SIZE) );
					// b.setX(XMAX / 2 - BLOCK_SIZE);
					// c.setX(XMAX / 2);
					// d.setX(XMAX / 2 + BLOCK_SIZE);
					// e.setX(XMAX / 2 + (2*BLOCK_SIZE));

					a.setX(XMAX / 2);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2);
					d.setX(XMAX / 2);
					e.setX(XMAX / 2);

					a.setY(BLOCK_SIZE);
					b.setY(2*BLOCK_SIZE);
					c.setY(3*BLOCK_SIZE);
					d.setY(4*BLOCK_SIZE);
					e.setY(5*BLOCK_SIZE);

					name = "i";
				break;
			case 3:
					/*
					* {a,b, }
					* { ,c, }
					* { ,d,e}
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX /2 + BLOCK_SIZE);
					e.setY(2*BLOCK_SIZE);

					name = "z";
				break;
			case 4:
			        /*
					* {a,b,c}
					* { ,d, }
					* { ,e, }
					* 
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2 + BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(2*BLOCK_SIZE);

					name = "t";
				break;
			case 5:
					/*
					* {a,b}
					* {c, }
					* {d,e}
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2 - BLOCK_SIZE);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2 - BLOCK_SIZE);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(2*BLOCK_SIZE);

					name = "u";
				break;
			case 6:
					/*
					* {a,b,c}
					* {d, , }
					* {e, , }
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2 + BLOCK_SIZE);
					d.setX(XMAX / 2 - BLOCK_SIZE);
					d.setY(BLOCK_SIZE);
					e.setX(XMAX / 2 - BLOCK_SIZE);
					e.setY(2*BLOCK_SIZE);

					name = "v";
				break;
			case 7:
					/*
					* { , ,a}
					* { ,b,c}
					* {d,e, }
					*/
					a.setX(XMAX / 2 + BLOCK_SIZE);
					b.setX(XMAX / 2);
					b.setY(BLOCK_SIZE);
					c.setX(XMAX / 2 + BLOCK_SIZE);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2 - BLOCK_SIZE);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(2*BLOCK_SIZE);

					name = "w";
				break;
			case 8:
					/*
					* {a, }
					* {b,c}
					* {d, }
					* {e, }
					*/
					a.setX(XMAX / 2);
					b.setX(XMAX / 2);
					b.setY(BLOCK_SIZE);
					c.setX(XMAX / 2 + BLOCK_SIZE);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(3*BLOCK_SIZE);

					name = "y";
				break;
			case 9:
					/*
					* {a, }
					* {b, }
					* {c, }
					* {d,e}
					*/
					a.setX(XMAX / 2);
					b.setX(XMAX / 2);
					b.setY(BLOCK_SIZE);
					c.setX(XMAX / 2);
					c.setY(2*BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(3*BLOCK_SIZE);
					e.setX(XMAX / 2 + BLOCK_SIZE);
					e.setY(3*BLOCK_SIZE);

					name = "l";
				break;
			case 10:
					/*
					* {a,b}
					* {c,d}
					* {e, }
					*/
					a.setX(XMAX / 2);
					b.setX(XMAX / 2 + BLOCK_SIZE);
					c.setX(XMAX / 2);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2 + BLOCK_SIZE);
					d.setY(BLOCK_SIZE);
					e.setX(XMAX / 2);
					e.setY(2*BLOCK_SIZE);

					name = "p";
				break;
			case 11:
					/*
					* {a,b, , }
					* { ,c,d,e}
					*/
					a.setX(XMAX / 2 - BLOCK_SIZE);
					b.setX(XMAX / 2);
					c.setX(XMAX / 2);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2 + BLOCK_SIZE);
					d.setY(BLOCK_SIZE);
					e.setX(XMAX / 2 + (2*BLOCK_SIZE));
					e.setY(BLOCK_SIZE);

					name = "n";
				break;
			case 12:
					/*
					* { ,a,b}
					* {e,c, }
					* { ,d, }
					*/
					a.setX(XMAX / 2);
					b.setX(XMAX / 2 + BLOCK_SIZE);
					c.setX(XMAX / 2);
					c.setY(BLOCK_SIZE);
					d.setX(XMAX / 2);
					d.setY(2*BLOCK_SIZE);
					e.setX(XMAX / 2 - BLOCK_SIZE);
					e.setY(BLOCK_SIZE);

					name = "f";
				break;
		}
		int pentoID = block - 1;

		return new Pentomino(a, b, c, d, e, name, pentoID);
	}
}
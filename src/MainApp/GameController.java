package MainApp;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import PentominosUtils.PentominoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameController {

    @FXML
    private Pane gamePane, nextPiecePane;

    @FXML
    private Button autoPlaybtn;
    
    public GameController(){}

    @FXML
    private Pane nextPieceContainer;

    @FXML
    private Text scoreCounter;

    private final int XMAX = 5;
    private final int YMAX = 15;
    public final int BLOCK_SIZE = 30;
    private final int MOVE = BLOCK_SIZE;

    private int[][] playGrid = new int[XMAX][YMAX];

    private int currentScore = 0;
    private int lapTime = 0;

    private boolean inGame = false;
    private boolean autoPlay = false;
    
    private int PieceX,PieceY;

    private int currentPieceID = 0;
    private int nextPieceID;

    private static Pentomino[] pentos = new Pentomino[PentominoDatabase.data.length];

    public void storePentominos(){
        int PIECE_WIDTH = BLOCK_SIZE - 1;
        int PIECE_HEIGHT = BLOCK_SIZE - 1;
        for(int i = 0 ; i < PentominoDatabase.data.length; i++){
            Rectangle a = new Rectangle( PIECE_WIDTH , PIECE_HEIGHT);
            Rectangle b = new Rectangle( PIECE_WIDTH , PIECE_HEIGHT);
            Rectangle c = new Rectangle( PIECE_WIDTH , PIECE_HEIGHT);
            Rectangle d = new Rectangle( PIECE_WIDTH , PIECE_HEIGHT);
            Rectangle e = new Rectangle (PIECE_WIDTH , PIECE_HEIGHT);


            pentos[i] = new Pentomino(a, b, c, d, e, i);
        }
    }

    @FXML
    private void SetAutoPlay(ActionEvent event) {
        autoPlay = !autoPlay;

        if(autoPlay) 
            autoPlaybtn.setText( "AutoPlay OFF" );
        else
            autoPlaybtn.setText( "AutoPlay ON" );

    }

    public void StartGame(){
        inGame = true;
        ActivateTimer();
        
        //Game Loop
        //while(inGame){

       // }
    }

    public void UpdateScore(){
        scoreCounter.setText(currentScore+"");
    }

    private Timer timer;

    private void ActivateTimer() {  

        if(timer != null && timer.isRunning()) return;  

        timer = new Timer(1000,new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (inGame == false) {
                    timer.stop();
                }else{
                   lapTime++;
                   PieceY++;
                }
            }
        });

        timer.start();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.A && isValidMove(-1, 0)) {
            System.out.println("LEFT");
            PieceX--;
            drawShape();
        } else if (code == KeyCode.D && isValidMove(1, 0)) {
            System.out.println("RIGHT");
            PieceX++;
            MoveRight(pentos[currentPieceID].a);
        } else if (code == KeyCode.S && isValidMove(0, 1)) {
             System.out.println("DOWN");
            PieceY++;
            drawShape();
        } else if (code == KeyCode.W) {
             System.out.println("UP");
            rotateShape();
            drawShape();
        }
    }

    private void MoveRight(Rectangle rect) {
		if (rect.getX() + MOVE <= XMAX - BLOCK_SIZE)
			rect.setX(rect.getX() + MOVE);
	}

    private void drawShape()
    {
        gamePane.getChildren().clear();

        gamePane.getChildren().addAll(pentos[currentPieceID].a, pentos[currentPieceID].b, pentos[currentPieceID].c, pentos[currentPieceID].d, pentos[currentPieceID].e);

    }

    private void drawBlock(int x, int y, Color color) {
        Rectangle block = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        block.setFill(color);
        gamePane.getChildren().add(block);
    }

    private void rotateShape()
    {

    }

    private boolean isValidMove(int offsetX,int offsetY)
    {

        return true;
    }

    private void chooseRandomShape(){
        currentPieceID = (int) (Math.random() * PentominoDatabase.data.length);
        PieceX = XMAX / 2;
        PieceY = 0;

        drawShape();
    }

}

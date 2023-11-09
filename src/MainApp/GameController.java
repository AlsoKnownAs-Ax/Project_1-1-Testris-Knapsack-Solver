package MainApp;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameController {
    
    public GameController(){}

    @FXML
    private GridPane PlayGrid;

    @FXML
    private Button autoPlaybtn;

    @FXML
    private Pane nextPieceContainer;

    @FXML
    private GridPane nextPieceGrid;

    @FXML
    private Text scoreCounter;

    private int currentScore = 0;
    private boolean inGame = false;
    private boolean autoPlay = false;
    private int x,y;

    @FXML
    void SetAutoPlay(ActionEvent event) {
        autoPlay = true;
    }

    public void StartGame(){
        inGame = true;
        ActivateTimer();
        
        //Game Loop
        while(inGame){

        }
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
                    y++;
                }
            }
        });

        timer.start();
    }



}

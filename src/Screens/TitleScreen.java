package Screens;

import PentominosUtils.Search;
import MainApp.TetrisGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class TitleScreen
{
    private JFrame window;
    private static JPanel TitleNamePanel,startButtonPanel;
    private JLabel GameTitle;
    private JButton startButton;
    private TitleScreenHandler tsHandler = new TitleScreenHandler();

    private Color BLACK = Color.black;
    private Color RED   = Color.red;
    private Color BLUE  = Color.blue;
    private Color WHITE = Color.white;

    private Container container = TetrisGame.container;


    public TitleScreen()
    {
        window = new JFrame();
        window.setSize(600,1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(BLACK);
        window.setLayout(null);
        container = window.getContentPane();

        TitleNamePanel = new JPanel();
        TitleNamePanel.setBounds(70,100,700,150);
    }

    private void setFonts(){
        // GameTitle.setFont(App.titleFont);
        // startButton.setFont(App.MainTextFont);
    }

    public void Run(){
        // setFonts();

        // TitleNamePanel.add(GameTitle);
        // App.container.add(TitleNamePanel);
        // slowPrint("Jerry's Adventure");

        
        // window.setVisible(true);
    }


    private static class TitleScreenHandler implements ActionListener{

        public void actionPerformed(ActionEvent e){

            //App.GameScreen();
            // Close();
            // App.GameScreen.Run();
        }
    }

    // public void run(){
    //     AnimateText();
    // }
}
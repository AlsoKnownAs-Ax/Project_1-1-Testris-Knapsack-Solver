package Screens;

import java.awt.Color;
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

    public TitleScreen()
    {

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
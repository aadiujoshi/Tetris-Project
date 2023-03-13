import java.awt.event.*;
import javax.swing.*;

public class Player implements ActionListener
{
    private int score = 0;
    private int level = 0;
    private int lines = 0;
    
    private GameUI gui;
    private Timer clock = new Timer(900, this);
    private boolean gameOver = false;
    
    public Player()
    {
        gui = new GameUI();
        
        //wait for startgamebutton to be pressed to start game clock
        while(!gui.getStartGameState())
        {
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        
        StartGame();
    }
    
    public void StartGame()
    {
        clock.start();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println(activeCount());
        if(gui.generateNextBlockState())
        {
            if(gui.checkForCompleteLines())
            {
                gui.updateCompleteLines();            
                gui.moveLinesDown();
            }
            Block b = new Block((int)(Math.random()*7));
            gui.drawBlock(b.getBlockArray());
            gui.setGenerateNextBlockState(false);
        }
        else
        {
            gui.moveDown();
        }
    }
}
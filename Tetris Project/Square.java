import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Square implements Serializable
{
    private final int SQUARE_HEIGHT = 20;
    private final int SQUARE_WIDTH = 20;
    
    private final JLabel CURRENT_SQUARE_COLOR;
    
    private int ColorID;
    private boolean activeSquare = true;
    
    private final JLabel GREEN_SQUARE = new JLabel(new ImageIcon(new File("GreenSquare.png").getAbsolutePath()));
    private final JLabel RED_SQUARE = new JLabel(new ImageIcon(new File("RedSquare.png").getAbsolutePath()));
    private final JLabel PURPLE_SQUARE = new JLabel(new ImageIcon(new File("PurpleSquare.png").getAbsolutePath()));
    private final JLabel BLUE_SQUARE = new JLabel(new ImageIcon(new File("BlueSquare.png").getAbsolutePath()));
    private final JLabel LIGHT_BLUE_SQUARE = new JLabel(new ImageIcon(new File("LightBlueSquare.png").getAbsolutePath()));
    private final JLabel YELLOW_SQUARE = new JLabel(new ImageIcon(new File("YellowSquare.png").getAbsolutePath()));
    private final JLabel ORANGE_SQUARE = new JLabel(new ImageIcon(new File("OrangeSquare.png").getAbsolutePath()));
    
    private final JLabel[] SQUARE_COLORS = {GREEN_SQUARE, RED_SQUARE, 
                                            PURPLE_SQUARE, BLUE_SQUARE, 
                                            LIGHT_BLUE_SQUARE, YELLOW_SQUARE, 
                                            ORANGE_SQUARE};
    
    public Square(int ID) //coorelates to BlockID
    {
        CURRENT_SQUARE_COLOR = SQUARE_COLORS[ID];
        ColorID = ID;
    }
    
    public JLabel getSquareAsLabel()
    {
        return CURRENT_SQUARE_COLOR;
    }
    
    public int getColorID()
    {
        return ColorID;
    }
    
    public boolean isActive()
    {
        return activeSquare;
    }
    
    public void setActive(boolean b)
    {
        activeSquare = b;
    }
}
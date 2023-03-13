import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI implements ActionListener
{
    private final int FRAME_HEIGHT = 500; 
    private final int FRAME_WIDTH = 620; 
    private JFrame frame = new JFrame("Tetris Game");
    private JPanel mainPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel blockPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel openingPanel = new JPanel();
    
    private JLabel openingTitleLabel;
    private JButton startGameButton;
    
    private boolean generateNextBlock = true;
    private boolean startGameBool = false;
    
    private Square[][] grid = new Square[20][10];
    
    private Block CurrentBlock;
    
    private Font tetrisFont;
    private Font bigTetrisFont;
    private final Color GREY = new Color(50, 50, 50);
    
    public GameUI()
    {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        
        //custom font
        try 
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("TETRIS_FONT.ttf")));
            tetrisFont = Font.createFont(Font.TRUETYPE_FONT, new File("TETRIS_FONT.ttf")).deriveFont(30f);
            bigTetrisFont = Font.createFont(Font.TRUETYPE_FONT, new File("TETRIS_FONT.ttf")).deriveFont(120f);
        }
        catch (IOException | FontFormatException e) 
        {
            //System.out.println("error");
        }
        
        //----------
        
        loadOpeningScreen();
        
        //----------
        frame.setVisible(true);
    }
    
    public void loadGameUI()
    {
        frame.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent k)
            {
            int arrowKey = k.getKeyCode();
            switch(arrowKey)
            {
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    break;
                
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    break;
                
                case KeyEvent.VK_DOWN:
                    moveDown();
                    break;
                
                case KeyEvent.VK_R:
                    break;
            }
        }
        public void keyReleased(KeyEvent k){}
        public void keyTyped(KeyEvent k){}
        });
        
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 35, 30));
        mainPanel.setBackground(new Color(50, 50, 50));
        
        blockPanel.setLayout(null);
        blockPanel.setBackground(GREY);
        blockPanel.setPreferredSize(new Dimension(200 + 6, 400 + 6));
        blockPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        
        leftPanel.setLayout(null);
        leftPanel.setBackground(GREY);
        leftPanel.setPreferredSize(new Dimension(150, 400));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        
        rightPanel.setLayout(null);
        rightPanel.setBackground(GREY);
        rightPanel.setPreferredSize(new Dimension(120, 350));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        
        mainPanel.add(leftPanel);
        mainPanel.add(blockPanel);
        mainPanel.add(rightPanel);
        
        frame.add(mainPanel);
        frame.revalidate();
    }
    
    public void loadOpeningScreen()
    {
        openingPanel.setBackground(GREY);
        openingPanel.setLayout(new BorderLayout(120, 120));
        
        openingTitleLabel = new JLabel("Tetris");
        openingTitleLabel.setFont(bigTetrisFont);
        openingTitleLabel.setForeground(Color.YELLOW);
        
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(tetrisFont);
        startGameButton.setFocusable(false);
        startGameButton.setBackground(Color.GREEN);
        startGameButton.setBorder(BorderFactory.createBevelBorder(1));
        startGameButton.addActionListener(this);
        
        openingPanel.add(openingTitleLabel, BorderLayout.NORTH);
        openingPanel.add(startGameButton, BorderLayout.CENTER);
        openingPanel.add(new Panel(), BorderLayout.WEST); //filler
        openingPanel.add(new Panel(), BorderLayout.EAST); //filler
        openingPanel.add(new Panel(), BorderLayout.SOUTH); //filler
        
        frame.add(openingPanel);
        frame.revalidate();
    }
    
    public void draw(JLabel s, int x, int y)
    {
        if(s == null)
        {
            blockPanel.remove(grid[x][y].getSquareAsLabel());
            return;
        }
        s.setBounds(x*20 + 3, y*20 + 3, 20, 20);
        blockPanel.add(s);
        blockPanel.setVisible(false);
        blockPanel.setVisible(true);
    }
    
    public void drawBlock(Square[][] bArr)
    {
        for(int r = 0; r < bArr.length; r++)
        {
            for(int c = 0; c < bArr[r].length; c++)
            {
                if(bArr[r][c] instanceof Square)
                {
                    grid[r][c] = bArr[r][c];
                    draw(bArr[r][c].getSquareAsLabel(), c, r);
                }
            }
        }
        
        blockPanel.repaint();
    }
    
    public void moveDown()
    {
        if(actionKeepsBlockIntegrity(2))
        {
            for(int r = grid.length -2; r >= 0; r--)
            {
                for(int c = 0; c < grid[r].length; c++)
                {
                    if(grid[r][c] instanceof Square && grid[r][c].isActive() == true)
                    {
                        grid[r+1][c] = grid[r][c];
                        draw(grid[r+1][c].getSquareAsLabel(), c, r+1); 
                        grid[r][c] = null;
                    }
                }
            }
        }
        
        //System.out.println("down 2");
        blockPanel.repaint();
    }
    
    public void moveLeft()
    {
        if(actionKeepsBlockIntegrity(0))
        {
            for(int r = grid.length-2; r >= 0; r--)
            {
                for(int c = 1; c < grid[r].length; c++)
                {
                    if(grid[r][c] instanceof Square && grid[r][c].isActive() == true)
                    {
                        grid[r][c-1] = grid[r][c];
                        draw(grid[r][c-1].getSquareAsLabel(), c-1, r); 
                        grid[r][c] = null;
                    }
                }
            }
        }
        
        blockPanel.repaint();
        //System.out.println("left 2");
    }
    
    public void moveRight()
    {
        if(actionKeepsBlockIntegrity(1))
        {
            for(int r = grid.length-2; r >= 0; r--)
            {
                for(int c = grid[r].length-2; c >= 0; c--)
                {
                    if(grid[r][c] instanceof Square && grid[r][c].isActive() == true)
                    {
                        grid[r][c+1] = grid[r][c];
                        draw(grid[r][c+1].getSquareAsLabel(), c+1, r); 
                        grid[r][c] = null;
                    }
                }
            }
        }
        
        blockPanel.repaint();
        //System.out.println("right 2");
    }
    
    public boolean actionKeepsBlockIntegrity(int direction)
    {
        for(int r = grid.length-1; r >= 0; r--)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                if(grid[r][c] instanceof Square) //these 2 ifs check for floating blocks
                {
                    if(grid[r][c].isActive())    //^^^
                    {
                        if(direction == 0)
                        {
                            if(c-1 == -1 || grid[r][c-1] != null 
                            && grid[r][c-1].isActive() != true)
                            {
                                return false;
                            }
                        }
                        if(direction == 1)
                        {
                            if(c+1 == grid[0].length || grid[r][c+1] != null 
                            && grid[r][c+1].isActive() != true)
                            {
                                return false;
                            }
                        }
                        if(direction == 2)
                        {
                            if(r+1 == grid.length || grid[r+1][c] != null 
                            && grid[r+1][c].isActive() != true)
                            {
                                generateNextBlock = true;
                                setSquareState(false);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public void rotateBlock()
    {
        
    }
    
    public void clearLine(int r)
    {
        for(int c = 0; c < grid[r].length; c++)
        {
            draw(null, r, c);
            grid[r][c] = null;
        }
    }
    
    public void setSquareState(boolean b)
    {
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                if(grid[r][c] instanceof Square)
                {
                    grid[r][c].setActive(b);
                }
            }
        }
    }
    
    public void moveLinesDown()
    {
        for(int r = grid.length-2; r >= 0; r--)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                int clearance = getClearance(r, c);
                
                if(grid[r][c] instanceof Square)
                {
                    int counterignore = 0;
                    
                    for(int r1 = r; getClearance(r1, c) >= clearance-1 
                    && r1+1 < grid.length; r1++)
                    {
                        grid[r1+1][c] = grid[r1][c];
                        draw(grid[r1+1][c].getSquareAsLabel(), c, r1+1);
                        grid[r1][c] = null;
                    }
                }
            }
        }
    }
    
    public int getClearance(int row, int col)
    {
        int clearance = 0;
        
        for(int r = row; r < grid.length-1; r++)
        {
            if(grid[r+1][col] == null && r+1 < grid.length)
            {
                clearance++;
            }
            else 
            {
                return clearance;
            }
        }
        
        return clearance;
    }
    
    public boolean checkForCompleteLines()
    {
        int objCount = 0;
        boolean hasCompLines = false;
        
        for(int r = grid.length-1; r >= 0; r--)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                if(grid[r][c] instanceof Square)
                {
                    objCount++;
                }
            }
            if(objCount == grid[r].length) //move eveyrhting down until bottom
            {
                hasCompLines = true;
            }
            objCount = 0;
        }
        
        return hasCompLines;
    }
    
    public void updateCompleteLines()
    {
        int objCount = 0;
        Square[][] unclearedGrid = grid;
        
        for(int r = grid.length-1; r >= 0; r--)
        {
            for(int c = 0; c < grid[0].length; c++) //fix clearance issue, make integrity validator
            {
                if(grid[r][c] instanceof Square)
                {
                    objCount++;
                }
            }
            if(objCount == grid[r].length) //move eveyrhting down until bottom
            {
                clearLine(r);
            }
            objCount = 0;
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == startGameButton)
        {
            startGameBool = true;
            frame.remove(openingPanel);
            loadGameUI();
        }
    }
    
    public boolean getStartGameState()
    {
        return startGameBool;
    }
    
    public boolean generateNextBlockState()
    {
        return generateNextBlock;
    }
    
    public void setGenerateNextBlockState(boolean b)
    {
        generateNextBlock = b;
    }
}
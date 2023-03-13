import javax.swing.*;

public class Block
{
    private final Square s = new Square(0); 
    //placeholder square which is replaced in constructor
    
    private Square[][] S_Block = {{null, s, s},{s, s, null}};
    private Square[][] Z_Block = {{s, s, null},{null, s, s}};
    private Square[][] T_Block = {{s, s, s}, {null, s, null}};
    private Square[][] J_Block = {{null, s}, {null, s}, {s, s}};
    private Square[][] I_Block = {{s}, {s}, {s}, {s}};
    private Square[][] O_Block = {{s, s}, {s, s}};
    private Square[][] L_Block = {{s, null}, {s, null}, {s, s}};
    
    private final Square[][] CURRENT_BLOCK_TYPE;
    
    private final Square[][][] ALL_BLOCKS = {S_Block, Z_Block, 
                                        T_Block, J_Block, 
                                        I_Block, O_Block, 
                                        L_Block};
                                        
    public Block(int BlockID)
    {
        CURRENT_BLOCK_TYPE = ALL_BLOCKS[BlockID];
        
        for(int r = 0; r < CURRENT_BLOCK_TYPE.length; r++)
        {
            for(int c = 0; c < CURRENT_BLOCK_TYPE[0].length; c++)
            {
                if(CURRENT_BLOCK_TYPE[r][c] instanceof Square)
                {
                    CURRENT_BLOCK_TYPE[r][c] = new Square(BlockID);
                }
            }
        }
    }
    
    public Square[][] getBlockArray()
    {
        return CURRENT_BLOCK_TYPE;
    }
    
    public Square[][] getRotatedBlock()
    {
        return CURRENT_BLOCK_TYPE;
    }
}
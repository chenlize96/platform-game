package model;

import java.io.Serializable;

public class CharacterMoveMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int x_move_from;
    private int y_move_from;
    private int x_move_to;
    private int y_move_to;
    
    public CharacterMoveMessage(int x_move_from, int y_move_from,
    		int x_move_to, int y_move_to){
    	
    	this.x_move_from = x_move_from;
    	this.y_move_from = y_move_from;
    	this.x_move_to = x_move_to;
    	this.y_move_to = y_move_to;
    }
    
    /**
     * Returns X move from
     * @return int
     * @author Eujin Ko
     */   
    public int getXMoveFrom() {
    	return this.x_move_from;
    }  
    /**
     * Returns Y move from
     * @return int
     * @author Eujin Ko
     */    
    public int getYMoveFrom() {
    	return this.y_move_from;
    }  
    /**
     * Returns X move to
     * @return int
     * @author Eujin Ko
     */
    public int getXMoveTo() {
    	return this.x_move_to;
    }
    
    /**
     * Returns Y move to
     * @return int
     * @author Eujin Ko
     */
    public int getYMoveTo() {
    	return this.y_move_to;
    }
}

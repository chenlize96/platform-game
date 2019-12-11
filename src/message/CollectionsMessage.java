package message;
public class CollectionsMessage {
	
	CharacterMoveMessage char_msg;
	int health_status;
	boolean win_status;
	int key_status;
	String box_direction;
	int[] box_coordinate;
	boolean portal_status = false;
	
//	MonsterMoveMessage monster_msg;
	
	/**
	 * Constructor which contains all the message needed to update the view
	 * @param char_msg CharacterMoveMessage which contains the movement of the character
	 * @param health_status int, remaining health
	 * @param win boolean, indicates whether player has reached the exit
	 * @param box_coordinate 
	 * @param box_direction 
	 * @author Eujin Ko
	 * @author Lize Chen
	 */
	public CollectionsMessage(CharacterMoveMessage char_msg, int health_status, boolean win,
			int key_status,boolean portal_status, String box_direction, int[] box_coordinate) {
		this.char_msg = char_msg;
		this.health_status = health_status;
		this.win_status = win;
		this.key_status = key_status;
		this.box_direction = box_direction;
		this.box_coordinate = box_coordinate;
		this.portal_status = portal_status;
	}
	/**
	 * 
	 * @return
	 * @author Eujin Ko
	 */
	public String returnBoxDirection() {
		return this.box_direction;
	}
	/**
	 * 
	 * @return
	 * @author Eujin Ko
	 */
	public int[] returnBoxCoordinate() {
		return this.box_coordinate;
	}
	
	//lize
	public int returnKeyStatus() {
		return key_status;
	}
	
	//lize
	public boolean returnPortalStatus() {
		return portal_status;
	}
	
	/**
	 * Returns the CharacterMoveMessage
	 * @return CharacterMoveMessage
	 * @author Eujin Ko
	 */
	public CharacterMoveMessage getCharacterMoveMessage() {
		return this.char_msg;
	}
	/**
	 * Returns the Health status
	 * @return int, remaining health
	 * @author Eujin Ko
	 */
	public int returnHealthStatus() {
		return this.health_status;
	}
	/**
	 * Returns the win status
	 * @return boolean, true = when wins, false = when not
	 * @author Eujin Ko
	 */
	public boolean returnWinStatus() {
		return this.win_status;
	}
}

package message;

public class CollectionsMessage {
	
	CharacterMoveMessage char_msg;
	int health_status;
	
//	MonsterMoveMessage monster_msg;
	
	
	public CollectionsMessage(CharacterMoveMessage char_msg, int health_status) {
		this.char_msg = char_msg;
		this.health_status = health_status;
		
	}
	
	public CharacterMoveMessage getCharacterMoveMessage() {
		return this.char_msg;
	}
	
	public int returnHealthStatus() {
		return this.health_status;
	}
}

package message;

public class CollectionsMessage {
	
	CharacterMoveMessage char_msg;
	
//	MonsterMoveMessage monster_msg;
	
	
	public CollectionsMessage(CharacterMoveMessage char_msg) {
		this.char_msg = char_msg;
		
	}
	
	public CharacterMoveMessage getCharacterMoveMessage() {
		return this.char_msg;
	}
}

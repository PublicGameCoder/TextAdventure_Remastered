package items;

import java.util.HashMap;

import adventure.Room;
import adventure.Game;
/**
 * @author mike de groot
 */
public class Key extends Item {
	
	private HashMap<Room,Room> doors;
	
	public Key(String name, String desc) {
		this.name = name;
		this.description = desc;
		this.type = "Key";
		
		this.doors = new HashMap<Room,Room>();
		
	}

	/**
	 * @return the canOpenDoor
	 */
	public HashMap<Room,Room> getDoors() {
		return this.doors;
	}

	/**
	 * @param canOpenDoor the canOpenDoor to add
	 */
	public void setDoor(Room from, Room to) {
		this.doors.put(from, to);
		to.addKey(this);
	}
	
	public void use(Room currentRoom) {
		
	}
	
	@Override
	public boolean use() {
		Room currentRoom = Game.game.getPlayer().getCurrentRoom();
		if (this.doors.containsKey(currentRoom)) {
			if (this.doors.get(currentRoom).isLocked()) {
				if (this.doors.get(currentRoom).tryKey(this)) {
					Room targetRoom = this.doors.get(currentRoom);
					targetRoom.setLocked(false);
					return true;
				}else {
					System.out.println("This key can't be used on this door!");
				}
			}else {
				System.out.println("That door is already unlocked!");
			}
		}
		return false;
	}
}

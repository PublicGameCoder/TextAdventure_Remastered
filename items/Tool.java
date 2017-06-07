package items;

import java.util.HashMap;

import adventure.Game;
import adventure.Room;

/**
 * @author mike de groot
 */
public class Tool extends Item {

	private boolean canScrew = false;
	private boolean canBreakOpen = false;
	private HashMap<Room,Room> doors;

	public Tool(String name, String desc) {
		this.name = name;
		this.description = desc;
		this.type = "Tool";
		this.doors = new HashMap<Room,Room>();
	}
	/**
	 * @return the canScrew
	 */
	public boolean canScrew() {
		return canScrew;
	}

	/**
	 * @param canScrew the canScrew to set
	 */
	public void canScrew(boolean canScrew) {
		this.canScrew = canScrew;
	}

	/**
	 * @return the canBreakOpen
	 */
	public boolean canBreakOpen() {
		return canBreakOpen;
	}

	/**
	 * @param canBreakOpen the canBreakOpen to set
	 */
	public void canBreakOpen(boolean canBreakOpen) {
		this.canBreakOpen = canBreakOpen;
	}
	
	@Override
	public boolean use() {
		Room currentRoom = Game.game.getPlayer().getCurrentRoom();
		if (this.doors.containsKey(currentRoom)) {
			currentRoom.setLocked(false);
			
			this.doors.get(currentRoom).setLocked(false);
			this.doors.remove(currentRoom, this.doors.get(currentRoom));
			this.doors.remove(this.doors.get(currentRoom), currentRoom);
			return true;
		}
		System.out.println();
		System.out.println("You can't use that here");
		return false;
	}
	public void canBreakDoor(Room room1, Room room2) {
		this.doors.put(room1, room2);
		this.doors.put(room2, room1);
	}
}

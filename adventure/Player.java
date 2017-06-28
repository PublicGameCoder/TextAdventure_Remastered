package adventure;

import java.util.ListIterator;
import java.util.Random;

import items.Inventory;
import items.Item;

/**
 * @author mike de groot
 */
public class Player {

	private Room currentRoom;
	private float health = 100.0f;
	private float strength = 20.0f;
	private boolean isPoisened = false;
	private boolean enteredRoomBasementBevore = false;
	private Inventory inventory;
	
	public Player() {
		inventory = new Inventory();
	}

	/**
	 * @return the currentRoom
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	/**
	 * @param room the currentRoom to set
	 */
	public void goToRoom(Room room) {
		
		if (room.isLocked() || (this.currentRoom != null && this.currentRoom.isLocked()) ) {
			System.out.println("the door to "+room.getName()+" is locked!");
			System.out.println("You might need to find a way to get trough");
			return;
		}
		
		if (this.currentRoom != null && this.currentRoom.getName().equalsIgnoreCase("toiletroom") && room.getName().equalsIgnoreCase("basement") && !enteredRoomBasementBevore) {
			room.setLocked(true);
			enteredRoomBasementBevore = true;
		}
		
		this.currentRoom = room;
		
		if (this.isPoisened) {
			damage(2.5f);
		}else if (room.isToxic() && this.generateChance() <= 70) {
			this.isPoisened(true);
		}
		
		System.out.println();
        System.out.println(this.currentRoom.getLongDescription());
	}

	private int generateChance() {
		Random random = new Random();
		return random.nextInt(100);
	}

	/**
	 * @return the health
	 */
	public boolean isAlive() {
		if (this.health <= 0.0f) {
			return false;// Lager of gelijk aan 0
		}else {
			return true;// Hoger dan 0
		}
	}

	/**
	 * Heals the player by a specified amount
	 * @param amount to heal
	 */
	public void heal(float amount) {
		this.health += amount;
		
		if (this.health > 100.0f) {
			this.health = 100.0f;
		}
		System.out.println();
		System.out.println("Player healed by: "+ amount);
		System.out.println("Player's current health: "+this.health);
	}
	
	/**
	 * Damaged the player by a specified amount
	 * @param amount to damage
	 */
	public void damage(float amount) {
		this.health -= amount;
		
		if (this.health < 0.0f) {
			this.health = 0.0f;
		}
		
		System.out.println();
		System.out.println("Player damaged by: "+ amount);
		System.out.println("Player's current health: "+ this.health);
	}
	
	public void isPoisened(boolean state) {
		this.isPoisened = state;
		
		String str = "Player ";
		if (state) {
			str += "got poisened";
		} else {
			str += "isn't poisened anymore";
		}
		System.out.println();
		System.out.println(str);
	}
	
	public boolean isPoisened() {
		return this.isPoisened;
	}
	
	public void pickup(String name) {
		if (this.currentRoom != null && this.currentRoom.getItems().isEmpty()) {
			System.out.println();
			System.out.println("There are no items in this room");
			return;
		}else {
			for (ListIterator<Item> iterator = this.currentRoom.getItems().listIterator(); iterator.hasNext();) {
				Item item = iterator.next();
	    		if (name.equalsIgnoreCase(item.getName())) {
	    			float weigthHolding = this.inventory.getCurrentWeigth();
	    			if ((weigthHolding + item.getWeigth()) > strength) {
	    				System.out.println();
	    				System.out.println(item.getName() +" is too heavy to carry with your other items!");
	    				return;
	    			}else {
	    				this.inventory.addItem(item);
		    			System.out.println();
		    			System.out.println(item.getName() +" has been added to your inventory!");
		    			iterator.remove();
	    			}
	    		}
	    	}
		}
    }
	
	public void drop(String name) {
		for (ListIterator<Item> iterator = this.inventory.getListIterator(); iterator.hasNext();) {
			Item item = iterator.next();
    		if (name.equalsIgnoreCase(item.getName())) {
    			this.currentRoom.addItem(item);
    			System.out.println();
    			System.out.println(item.getName() +" has been dropped from your inventory!");
    			iterator.remove();
    			
    		}
    	}
	}
	
	public void use(String name) {
		for (ListIterator<Item> iterator = this.inventory.getListIterator(); iterator.hasNext();) {
			Item item = iterator.next();
    		if (name.equalsIgnoreCase(item.getName())) {
				if (item.use()){
					if (item.isOneTimeUse()){
						iterator.remove();
					}
					System.out.println();
					System.out.println(item.getName() +" is used!");
				}
    		}
    	}
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public boolean hasItem(String name) {
		if (this.inventory.containsItemName(name)) {
			return true;
		}else {
			System.out.println();
	    	System.out.println("Theres no item called "+name+" found in your inventory");
			return false;
		}
	}

	public void getItemInfo(String name) {
		for (ListIterator<Item> iterator = this.inventory.getListIterator(); iterator.hasNext();) {
			Item item = iterator.next();
    		if (name.equalsIgnoreCase(item.getName())) {
    			System.out.println();
    			System.out.println(item.getName() +" is a "+ item.getType() + " and is "+ item.isReuseable());
    			System.out.println(item.getName() + item.getDescription());
    		}
    	}
	}

	public float getHealth() {
		return this.health;
	}
	
	public float getStrength() {
		return this.strength;
	}
}

package adventure;

import java.util.Set;

import items.Item;
import items.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Room {
	private String name;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private List<Item> items;
    private boolean isToxicBool = false;
    private boolean locked = false;
    private List<Key> keys;
    private Type type;
    
    public enum Type {
    	Inside,
    	Outside;
    }

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court
     * yard".
     */
    public Room(String name,String description, Type type)
    {
    	this.name = name;
        this.description = description;
        this.type = type;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        keys = new ArrayList<Key>();
    }
    
    public Type getType() {
    	return this.type;
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    
    public void addItem(Item item) {
    	items.add(item);
    }
    
    public boolean isToxic() {
    	return this.isToxicBool;
    }
    
    public void isToxic(boolean state) {
    	this.isToxicBool = state;
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return description + ".\n" + getExitString() + "\n"+ getItemsString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }
    
    private String getItemsString()
    {
        String returnString = "Items in room: ";
        if (items.size() <= 0) {
        	returnString += "None";
        }else{
	        for (Item item : items) {
	            returnString += "\n -"+ item.getName();
	        }
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction)
    {
        return (Room)exits.get(direction);
    }
    
    public void setLocked(boolean state) {
    	this.locked = state;
    }
    
    public boolean isLocked() {
    	return this.locked;
    }

	public boolean isItemInRoom(String secondWord) {
		for (Item item : items) {
    		if (secondWord.equalsIgnoreCase(item.getName())) {
    			return true;
    		}
    	}
    	System.out.println("Theres no item called "+secondWord+" found around here");
		return false;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void addKey(Key key) {
		this.keys.add(key);
	}

	public boolean tryKey(Key key) {
		if (this.keys.contains(key)) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return this.name;
	}
}

package items;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Inventory {
	
	private List<Item> inventory;

	public Inventory() {
		this.inventory = new ArrayList<Item>();
	}

	/**
	 * @return the inventory
	 */
	public List<Item> getInventory() {
		return this.inventory;
	}

	public float getCurrentWeigth() {
		float weigthHolding = 0.0f;
		for (ListIterator<Item> iterator = this.inventory.listIterator(); iterator.hasNext();) {
			Item item = iterator.next();
			weigthHolding += item.getWeigth();
		}
		return weigthHolding;
	}

	public void addItem(Item item) {
		this.inventory.add(item);
	}

	public boolean isEmpty() {
		if (this.inventory.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}

	public void printItemString() {
		String str = "Inventory items:";
		for (Item item : this.inventory) {
			str += "\n -" + item.getName();
		}
		System.out.println(str);
	}

	public ListIterator<Item> getListIterator() {
		return this.inventory.listIterator();
	}

	public boolean containsItemName(String name) {
		for (ListIterator<Item> iterator = this.inventory.listIterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if (item.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void take(Item item) {
		for (ListIterator<Item> iterator = this.inventory.listIterator(); iterator.hasNext();) {
			Item inventoryItem = iterator.next();
    		if (inventoryItem.equals(item)) {
    			iterator.remove();
    			System.out.println();
    			System.out.println(item.getName() +" has been removed from your inventory!");
    		}
    	}
	}
}

package items;

import java.util.Random;

import adventure.Game;
import adventure.Player;

public class Medicine extends Item {

	private float healAmount = 0.0f;
	private boolean antidote = false;
	private boolean deadly = false;
	
	public Medicine(String name, String desc) {
		this.name = name;
		this.description = desc;
		this.type = "Medicine";
		
		Random rm = new Random();
		this.healAmount = rm.nextInt(100)-50;
	}
	
	public boolean isAntidote() {
		return this.antidote;
	}
	
	public void isAntidote(boolean state) {
		this.antidote = state;
	}
	
	public boolean isDeadly() {
		// TODO Auto-generated method stub
		return this.deadly;
	}
	
	public void isDeadly(boolean state) {
		// TODO Auto-generated method stub
		this.deadly = state;
	}
	
	public float getHealAmount() {
		return this.healAmount;
	}

	@Override
	public boolean use() {
		Player player = Game.game.getPlayer();
		
		System.out.println();
		if (isAntidote()) {
			if (getHealAmount() >= 0.0f) {
				player.heal(getHealAmount());
			}else if (getHealAmount() < 0.0f) {
				player.damage(getHealAmount());
			}
			if (player.isPoisened()) {
				player.isPoisened(false);
			}
		}else if (isDeadly()) {
			float health = player.getHealth();
			player.damage(health);
		}else {
			System.out.println("The "+this.name+" did nothing");
		}
		return true;
	}

}

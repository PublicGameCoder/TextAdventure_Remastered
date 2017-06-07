package items;

/**
 * @author mike de groot
 */
public abstract class Item {

	protected String name;
	protected String type;
	protected String description;
	protected float weight = 1;
	protected boolean oneTimeUse = false;

	public String getName() {
		return name;
	}

	public void setWeight(float value) {
		this.weight = value;
	}
	public float getWeigth() {
		// TODO Auto-generated method stub
		return this.weight;
	}
	
	public String getDescription() {
		return this.description;
	}

	public String getType() {
		return this.type;
	}
	
	public boolean isOneTimeUse() {
		return this.oneTimeUse;
	}
	
	public void isOneTimeUse(boolean state) {
		this.oneTimeUse = state;
	}
	
	public String isReuseable() {
		String result = "reüsable";
		if (oneTimeUse) {
			result = "not " + result;
		}
		return result;
	}
	
	public abstract boolean use();
}

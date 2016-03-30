package model;

public abstract class Effekt extends Gameobject
{
	private int value = 0;
	private boolean spezialwaffe = false;
	
	public boolean isSpezialwaffe() {
		return spezialwaffe;
	}

	public void setSpezialwaffe(boolean spezialwaffe) {
		this.spezialwaffe = spezialwaffe;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Effekt(String bezeichner, int value, boolean spezialwaffe) {
		super(bezeichner);
		this.value = value;
		this.spezialwaffe = spezialwaffe;
	}



	
	
	


}
	
	
	



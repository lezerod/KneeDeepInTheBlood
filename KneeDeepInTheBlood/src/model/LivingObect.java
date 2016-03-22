package model;

public abstract class LivingObect extends MovableObject
{
	private Waffe waffe = new Waffe(0, null, null);

	public Waffe getWaffe() {
		return waffe;
	}

	public void setWaffe(Waffe waffe) {
		this.waffe = waffe;
	}
	
	private LivingObect(int id, String bezeichner, Position positon, int winkel,
			int speed, int health) {
		super(id, bezeichner, positon, winkel, speed, health);
		// TODO Auto-generated constructor stub
	}

	public LivingObect(int id, String bezeichner, Position positon, int winkel,
			int speed, int health, Waffe waffe) {
		super(id, bezeichner, positon, winkel, speed, health);
		this.waffe = waffe;
	}	
}

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

	public LivingObect(int id, String bezeichner, Position positon, int width,
			int height, int winkel, int speed, int health, Waffe waffe) {
		super(id, bezeichner, positon, width, height, winkel, speed, health);
		this.waffe = waffe;
	}
}

package model;

public abstract class LivingObject extends MovableObject
{


	public LivingObject(String bezeichner, Position positon, int width,
			int height, int winkel, int speed, int health, Waffe waffe,
			boolean immun) {
		super(bezeichner, positon, width, height, winkel, speed, health);
		this.waffe = waffe;
		this.immun = immun;
	}

	private Waffe waffe = new Waffe(0, null, null);
	private boolean immun = false;

	public Waffe getWaffe() {
		return waffe;
	}

	public void setWaffe(Waffe waffe) {
		this.waffe = waffe;
	}

	public boolean isImmun() {
		return immun;
	}

	public void setImmun(boolean immun) {
		this.immun = immun;
	}


	}

	
	
	


	
	




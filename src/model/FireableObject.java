package model;

public class FireableObject extends MoveableObject {

	private int lastShot;

	public int getLastShot() {
		return lastShot;
	}

	public void setLastShot(int lastShot) {
		this.lastShot = lastShot;
	}
	
	public void erhöheLastShot(){
		lastShot++;
	}
	
}

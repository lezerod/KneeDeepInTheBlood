package model;

public class FireableObject extends MoveableObject {

	private int lastShot;

	public int getLastShot() {
		return lastShot;
	}

	public void setLastShot(int lastShot) {
		this.lastShot = lastShot;
	}
	
	/**
	 * erh�ht die variable, die die Anzahl der Loops seit dem letzten
	 * Schuss speichert um 1.
	 */
	public void erh�heLastShot(){
		lastShot++;
	}
	
}

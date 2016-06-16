package model;

import java.io.Serializable;

/**
 * Diese Klasse beschreibt Objekte, die Projektile schiessen k�nnen
 *
 * @author Julien
 *
 */
public class FireableObject extends MoveableObject implements Serializable {

	private int lastShot;

	public int getLastShot() {
		return lastShot;
	}

	public void setLastShot(int lastShot) {
		this.lastShot = lastShot;
	}

	/**
	 * erh�ht die variable, die die Anzahl der Loops seit dem letzten Schuss
	 * speichert um 1.
	 */
	public void erhoeheLastShot() {
		lastShot++;
	}

}

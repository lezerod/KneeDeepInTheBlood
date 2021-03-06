package model;

import java.io.Serializable;

/**
 *
 * @author Julien, Till, Marco
 *
 */
public class HeldenFahrzeug extends FireableObject implements Serializable {

	private boolean hasSpezialWeapon;

	public HeldenFahrzeug(boolean hasSpezialWeapon) {
		this.hasSpezialWeapon = hasSpezialWeapon;
	}

	public boolean isHasSpezialWeapon() {
		return hasSpezialWeapon;
	}

	public void setHasSpezialWeapon(boolean hasSpezialWeapon) {
		this.hasSpezialWeapon = hasSpezialWeapon;
	}

}

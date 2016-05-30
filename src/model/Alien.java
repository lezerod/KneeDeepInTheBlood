package model;

/**
 * @author Julien, Marco, Till
 *
 */

public class Alien extends FireableObject {

	private boolean isImmun;

	public Alien(boolean isImmun) {
		this.isImmun = isImmun;
	}

	public boolean isImmun() {
		return isImmun;
	}

	public void setIsImmun(boolean isImmun) {
		this.isImmun = isImmun;
	}

}

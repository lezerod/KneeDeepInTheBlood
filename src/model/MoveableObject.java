package model;
/**
 * Diese Klasse beschreibt alle Objekte auf dem Spielfeld, die sich mit der Zeit bewegen.
 * 
 * @author Julien, Till, Marco
 *
 */
public  class MoveableObject extends GameObject {
	private float richtung = 0;
	private int speed = 0;

	
	public float getWinkel() {
		return richtung;
	}
	public void setWinkel(float winkel) {
		this.richtung = winkel;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}

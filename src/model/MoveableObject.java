package model;

import java.io.Serializable;

/**
 * Diese Klasse beschreibt alle Objekte auf dem Spielfeld, die sich mit der Zeit
 * bewegen.
 *
 * @author Julien, Till, Marco
 *
 */
public class MoveableObject extends GameObject implements Serializable {
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

	/**
	 * aendert die Position des Moveable Objects abhaengig von seinem Zustand
	 *
	 * @param rueckwarts
	 *            true, wenn es eine Bewegung nach hinten ist
	 */
	public void move(boolean rueckwaerts) {
		this.setX((float) (getNewPosX(this.getWinkel(), this.getSpeed(), this.getX(), this.getY(), rueckwaerts)));
		this.setY((float) (getNewPosY(this.getWinkel(), this.getSpeed(), this.getX(), this.getY(), rueckwaerts)));
	}

	/**
	 * Helper-Methode um die neue X-Position zu ermitteln
	 *
	 * @param winkel
	 *            aktueller Winkel
	 * @param speed
	 *            aktuelle Geschwindigkeit
	 * @param x
	 *            aktuelle X-Position
	 * @param y
	 *            aktuelle Y-Position
	 * @param back
	 *            true, wenn er rueckwaerts faehrt
	 * @return die neue X-Position
	 */
	private static double getNewPosX(float winkel, int speed, double x, double y, boolean back) {
		double deltaX;
		deltaX = Math.sin(winkel * (Math.PI / 180)) * speed;
		if (back)
			return (x - deltaX);
		else
			return (x + deltaX);
	}

	/**
	 * Helper-Methode um die neue Y-Position zu ermitteln
	 *
	 * @param winkel
	 *            aktueller Winkel
	 * @param speed
	 *            aktuelle Geschwindigkeit
	 * @param x
	 *            aktuelle X-Position
	 * @param y
	 *            aktuelle Y-Position
	 * @param back
	 *            true, wenn er rueckwaerts faehrt
	 * @return die neue Y-Position
	 */
	private static double getNewPosY(float winkel, int speed, double x, double y, boolean back) {
		double deltaY;
		deltaY = Math.cos(winkel * (Math.PI / 180)) * speed;
		if (back)
			return (y + deltaY);
		else
			return (y - deltaY);
	}

}

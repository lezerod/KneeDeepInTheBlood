package model;

/**
 * Diese Klasse bildet die Basis für alle Objekte, die sich auf dem Spielfeld
 * befinden.
 * 
 * @author Julien, Marco, Till
 *
 */
public class GameObject {

	private float x = 0;
	private float y = 0;

	private int width = 0;
	private int height = 0;

	public GameObject() {

	}

	/**
	 * Diese Funktion dient zum setzen der Position auf dem Spielfeld
	 * 
	 * @param x
	 *            X-Koordinate
	 * @param y
	 *            Y-Koordinate
	 * @param width
	 *            Breite
	 * @param height
	 *            Höhe
	 */
	public void setPosition(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}

package model;

/**
 * Diese Klasse erbt von {@link SpielfeldObject}.
 * 
 * @author til
 *
 */
public class GameObject {
	private float x = 0;
	private float y = 0;
	private int width = 0;
	private int height = 0;
	
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

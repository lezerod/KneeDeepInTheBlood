package model;

public class Position 
{
	private int x = 0;
	private int y = 0;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}


package model;

public abstract class MovableObject extends SpielfeldObject
{
	private int winkel = 0;
	private int speed = 0;
	private int health = 0;
	
	public void move(){}
	
	int getWinkel() {
		return winkel;
	}

	public void setWinkel(int winkel) {
		this.winkel = winkel;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public MovableObject(int id, String bezeichner, Position positon,
			int width, int height, int winkel, int speed, int health) {
		super(id, bezeichner, positon, width, height);
		this.winkel = winkel;
		this.speed = speed;
		this.health = health;
	}
}

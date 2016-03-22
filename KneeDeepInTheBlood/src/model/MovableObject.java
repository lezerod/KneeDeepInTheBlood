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

	private MovableObject(int id, String bezeichner, Position positon) {
		super(id, bezeichner, positon);
		// TODO Auto-generated constructor stub
	}

	public MovableObject(int id, String bezeichner, Position positon,
			int winkel, int speed, int health) {
		super(id, bezeichner, positon);
		this.winkel = winkel;
		this.speed = speed;
		this.health = health;
	}
}

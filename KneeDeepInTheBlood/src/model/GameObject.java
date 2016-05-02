package model;
/**
 * Diese Klasse erbt von {@link SpielfeldObject}.
 * @author til
 *
 */
public abstract class GameObject 
{

	private int winkel = 0;
	private int speed = 0;
	private int health = 0;
	private Position positon = new Position(0, 0);
	private int width = 0;
	private int height = 0;
	private boolean setting;
	
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


}

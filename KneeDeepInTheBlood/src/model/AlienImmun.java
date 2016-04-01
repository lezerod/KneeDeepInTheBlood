package model;

public class AlienImmun extends Alien 
{

	public AlienImmun() {
		super("AlienImmun", new Position(0, 0), 9, 9, 0, 15, 20, new Waffe(1, "SmackerBalls'", new Effekt("Damage", 5, false) {
		}), true);
		// TODO Auto-generated constructor stub
	}

}

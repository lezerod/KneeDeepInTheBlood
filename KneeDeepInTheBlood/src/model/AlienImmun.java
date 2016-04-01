package model;

public class AlienImmun extends Alien 
{

	public AlienImmun(Position position) {
		super("AlienImmun", position, 9, 9, 0, 15, 20, new Waffe(1, "SmackerBalls'", new Effekt("Damage", 5, false) {
		}), true);
		
		// TODO Auto-generated constructor stub
	}

}

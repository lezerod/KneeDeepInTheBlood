package model;

public class AlienNichtImmun extends Alien
{

	public AlienNichtImmun(Position position) {
		super("StandartAlien", position, 3, 3, 0, 15, 300, new Waffe(1, "SmackerBalls'", new Effekt("Damage", 5, false) {
		}), false);;
		// TODO Auto-generated constructor stub
	}

}

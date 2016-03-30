package model;

public class AlienNichtImmun extends Alien
{

	public AlienNichtImmun() {
		super("StandartAlien", new Position(0, 0), 7, 6, 0, 15, 300, new Waffe(1, "SmackerBalls'", new Effekt("Damage", 5, false) {
		}), false);;
		// TODO Auto-generated constructor stub
	}

}

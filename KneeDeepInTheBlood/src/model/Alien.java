package model;

public abstract class Alien extends LivingObject
{

	public Alien(String bezeichner, Position positon, int width, int height,
			int winkel, int speed, int health, Waffe waffe, boolean immun) {
		super(bezeichner, positon, width, height, winkel, speed, health, waffe, immun);
		// TODO Auto-generated constructor stub
	}

	
	


	
	
	/*public Alien() {
		super(1, "StandartAlien", new Position(0, 0), 7, 6, 0, 15, 20, new Waffe(1, "Alien Blaster", new Effekt(1, "Damage", 5) {
		}));
		// TODO Auto-generated constructor stub
	}*/

	
}

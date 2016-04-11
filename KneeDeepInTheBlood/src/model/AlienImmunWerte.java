package model;

public interface AlienImmunWerte 
{
	String bezeichner = "AlienImmun";
	Position position = new Position(0, 0);
	int width = 5;
	int height = 5;
	int winkel = 0;
	int speed = 0;
	int health = 100;
	Waffe waffe = new Waffe("AlienBlaster", new Effekt("Damage", 5){});
	boolean immun = true;
}

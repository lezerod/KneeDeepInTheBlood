package model;
/**
 * Dies ist eine Abstrakte Klasse, welche von {@link LivingObject} erbt.
 * @author til
 *
 */
public abstract class Alien extends LivingObject
{

	public Alien(String bezeichner, Position positon, int width, int height,
			int winkel, int speed, int health, Waffe waffe, boolean immun) {
		super(bezeichner, positon, width, height, winkel, speed, health, waffe, immun);
		// TODO Auto-generated constructor stub
	}
}

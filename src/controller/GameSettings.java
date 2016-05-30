package controller;

/**
 * In diesem Interface stehen alle Konstanten des Spiels
 * 
 * @author Julien, Marco, Till
 *
 */
public interface GameSettings {

	public static final int BREITE = 1000;
	public static final int HÖHE = 500;
	public static final int RANDGRÖSSE = 50;

	public static final int THREADTICKTIME = 10;

	public static final int EASYLIFETIME = 1;
	public static final int MIDDLELIFETIME = 2;
	public static final int HARDLIFETIME = 3;

	public static final int HELDENBREITE = 25;
	public static final int HELDENHÖHE = 50;
	public static final int HELDENFAHRZEUGSPEED = 2;
	public static final int HELDENWINKELCHANGESPEED = 3;
	public static final int HELDENFEUERRATE = 100;
	public static final int HELDENSTARTLEBEN = 7;

	public static final int ALIENBREITE = 30;
	public static final int ALIENHÖHE = 30;
	public static final double ALIENCHANGETURNINGCHANCE = 0.01;
	public static final int ALIENSPEED = 1;
	public static final int ALIENFEUERRATE = 100;
	public static final int ALIENMAXANZAHL = 10;
	public static final int ALIENRESPAWNRATE = 400;

	public static final int PROJEKTILBREITE = 10;
	public static final int PROJEKTILHÖHE = 10;
	public static final int PROJEKTILENEMYSPEED = 2;
	public static final int PROJEKTILFRIENDLYSPEED = 5;

	public static final String IMGBACKGROUNDPFAD = "images/background.png";
	public static final String IMGTITLEPFAD = "images/title.png";
	public static final String IMGALIENPFAD = "images/alien.png";
	public static final String IMGHELDENFAHRZEUGPFAD = "images/heldenFahrzeug.png";
	public static final String IMGPROJEKTILPFAD = "images/projEnemy.png";
	public static final String IMGPROJEKTILFRIENDLYPFAD = "images/projFriendly.png";

}

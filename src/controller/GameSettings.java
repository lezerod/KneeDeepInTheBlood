package controller;

/**
 * In diesem Interface stehen alle Konstanten des Spiels
 * @author Julien
 *
 */
public interface GameSettings {

	public static final int BREITE = 1000;
	public static final int HÖHE = 500;
	public static final int RANDGRÖSSE = 50;
	
	public static final int THREADTICKTIME = 10;
	
	public static final int HELDENBREITE = 25;
	public static final int HELDENHÖHE = 50;
	public static final int HELDENFAHRZEUGSPEED = 2;
	public static final int HELDENWINKELCHANGESPEED = 3;
	public static final int HELDENFEUERRATE = 100;
	public static final int HELDENSTARTLEBEN = 3;
	
	public static final int ALIENBREITE = 20;
	public static final int ALIENHÖHE = 20;
	public static final double ALIENCHANGETURNINGCHANCE = 0.05;
	public static final int ALIENSPEED = 2;
	public static final int ALIENFEUERRATE = 100;
	public static final int ALIENMAXANZAHL = 10;
	public static final int ALIENRESPAWNRATE = 400;
	
	public static final int PROJEKTILBREITE = 10;
	public static final int PROJEKTILHÖHE = 10;
	public static final int PROJEKTILSPEED = 2;
	
	public static final String IMGBACKGROUNDPFAD = "images/bg.png";
	public static final String IMGALIENPFAD = "images/alien_new.png";
	public static final String IMGHELDENFAHRZEUGPFAD = "images/julien_panzer.png";
	public static final String IMGPROJEKTILPFAD = "images/proj.png";
	public static final String IMGPROJEKTILFRIENDLYPFAD = "images/projFriendly.png";
	
}

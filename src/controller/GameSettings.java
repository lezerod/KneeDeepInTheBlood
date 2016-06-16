package controller;

/**
 * In diesem Interface stehen alle Konstanten des Spiels
 *
 * @author Julien, Marco, Till
 *
 */
public interface GameSettings {

	public static final int BREITE = 1000;
	public static final int HOEHE = 500;
	public static final int RANDGROESSE = 50;

	public static final int THREADTICKTIME = 10;

	public static final int EASYLIFETIME = 1;
	public static final int MIDDLELIFETIME = 2;
	public static final int HARDLIFETIME = 3;

	public static final int HELDENBREITE = 30;
	public static final int HELDENHOEHE = 60;
	public static final int HELDENFAHRZEUGSPEED = 2;
	public static final int HELDENWINKELCHANGESPEED = 3;
	public static final int HELDENFEUERRATE = 100;
	public static final int HELDENSTARTLEBEN = 7;
	public static final int HELDENSPEZIALWAFFECOOLDWON = 1000;
	public static final int HELDENSPEZIALWAFFEDAUER = 5000;

	public static final int IWILLDESTROYYOUTANKBREITE = 25;
	public static final int IWILLDESTROYYOUTANKHOEHE = 50;
	public static final int IWILLDESTROYYOUTANKSPEED = 2;
	public static final int IWILLDESTROYYOUTANKFEUERRATE = 100;

	public static final int ALIENBREITE = 30;
	public static final int ALIENHOEHE = 30;
	public static final double ALIENCHANGETURNINGCHANCE = 0.01;
	public static final int ALIENSPEED = 1;
	public static final int ALIENFEUERRATE = 100;
	public static final int ALIENMAXANZAHL = 10;
	public static final int ALIENRESPAWNRATE = 400;
	public static final double ALIENIMMUNCHANCE = 0.1;

	public static final int PROJEKTILBREITE = 10;
	public static final int PROJEKTILHOEHE = 10;
	public static final int PROJEKTILENEMYSPEED = 2;
	public static final int PROJEKTILFRIENDLYSPEED = 5;

	public static final String IMGBACKGROUNDPFAD = "resources/background.png";
	public static final String IMGTITLEPFAD = "resources/title.png";
	public static final String IMGALIENPFAD = "resources/alien.png";
	public static final String IMGHELDENFAHRZEUGPFAD = "resources/heldenFahrzeug.png";
	public static final String IMGHELDENFAHRZEUGBFGPFAD = "resources/heldenFahrzeugBFG.png";
	public static final String IMGPROJEKTILPFAD = "resources/projEnemy.png";
	public static final String IMGPROJEKTILFRIENDLYPFAD = "resources/projFriendly.png";
	public static final String IMGTANKPFAD = "resources/julien_tank.png";
	public static final String IMGIWILLDESTORYYOUTANKPFAD = "resources/iwill.png";
	public static final String IMGPROJEKTILECLIENT = "resources/projClient.png";
	public static final String IMGITEMBFG = "resources/bfg.png";
	public static final String IMGALIENIMMUN = "resources/alienimmun.png";

	public static final String SFXSHOTFIRED = "resources/button-50.wav";
	public static final String SFXALIENHIT = "resources/Explosion.wav";
	public static final String SFXHELDENHIT = "resources/Explosion.wav";
	public static final String SFXWIN = "resources/button-26.wav";
	public static final String SFXLOOSE = "resources/button-26.wav";
	public static final String MUSICBACKGROUND = "resources/Atdoomsgate.wav";

}

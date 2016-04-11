package model;
/**
 * In diesem Interface werden die Werte von dem {@link HeldenFahrzeug} festgelegt.
 * @author til
 *
 */
public interface HeldenFahrzeugWerte 
{
	final static String bezeichner = "HeldenFahrZeug";
	final static Position positionbeispawn = new Position(0, 0);
	final static int widthbeispawn = 5;
	final static int heightbeispawn = 5;
	final static int winkelbeispawn = 0;
	final static int speedbeispawn = 0;
	final static int healthbeispawn = 100;
	Waffe waffe = new Waffe("AlienBlaster", new Effekt("Damage", 5){});
	boolean immun = false;
}

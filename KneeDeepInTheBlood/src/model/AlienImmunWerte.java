package model;
/**
 * In diesem Interface werden die Werte fuer das {@link AlienImmun} festgelegt.
 * @author til
 *
 */
public interface AlienImmunWerte 
{
	final static String bezeichner = "AlienImmun";
	final static Position positionbeispawn = new Position(0, 0);
	final static int width = 5;
	final static int height = 5;
	final static int winkelbeispawn = 0;
	final static int speedbeispawn = 0;
	final static int healthbeispawn = 100;
	final static Waffe waffe = new Waffe("AlienBlaster", new Effekt("Damage", 5){});
	final static boolean immun = true;
}

package control;
import model.*;
/**
 * Diese Klasse enth�t Methoden die w�hrend des Spielablaufes abgerufen werden.
 * @author til
 */

public class GameHelper 
{
	/**
	 * Diese Methode ist ein einfacher Test um zwei Livingobject gegeneinander K�mpfen zu lassen und zu pr�fen pb eine Speziawaffe n�tig ist.
	 * @param angreifer
	 * @param verteidiger
	 */
	public static void fight(LivingObject angreifer, LivingObject verteidiger )
	{
		if(verteidiger.isImmun() && !angreifer.getWaffe().isSpezialwaffe())
		{
			System.out.println("Gegener ist immun!");
		}
		else
		{
			verteidiger.setHealth(verteidiger.getHealth() - angreifer.getWaffe().getEffekt().getValue());
				
		}
}
	/**
	 * Diese Methode �berprp�ft ob zwei Spielfeldobject sich �berlappen und gibt einen R�ckgabewert true zur�ck wenn dies der Fall ist.
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean overlap(SpielfeldObject s1, SpielfeldObject s2)
	{
		if(s1.getPositon().getX() < s2.getPositon().getX() + s2.getWidth() &&
				s1.getPositon().getX() + s1.getWidth() > s2.getPositon().getX() &&
				s1.getPositon().getY() < s2.getPositon().getY() + s2.getHeight() &&
				s1.getHeight() + s1.getPositon().getY() > s2.getPositon().getY())
		{
			System.out.println("Overlap detected!");
			return true;
		}
		System.out.println("All okay!");
		return false;
	}
	
}

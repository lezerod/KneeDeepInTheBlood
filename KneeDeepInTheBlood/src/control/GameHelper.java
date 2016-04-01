package control;
import model.*;


public class GameHelper 
{
	public static void fight(LivingObject angreifer, LivingObject verteidiger )
	{
		if(verteidiger.isImmun() && !angreifer.getWaffe().getEffekt().isSpezialwaffe())
		{
			System.out.println("Gegener ist immun!");
		}
		else
		{
			verteidiger.setHealth(verteidiger.getHealth() - angreifer.getWaffe().getEffekt().getValue());
				
		}
}
	
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

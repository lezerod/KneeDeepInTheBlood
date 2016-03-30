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
	}

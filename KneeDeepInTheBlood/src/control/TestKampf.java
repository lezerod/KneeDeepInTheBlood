package control;
import model.*;
import control.GameHelper;

public class TestKampf 
{
	public static void main(String[] args)
	{
		HeldenFahrzeug h1 = new HeldenFahrzeug(new Position(4, 4));
		AlienNichtImmun a1 = new AlienNichtImmun(new Position(3, 1));
		AlienImmun a2 = new AlienImmun(new Position(0, 0));
		
	    while(a1.getHealth() != 0)
	    {
		GameHelper.fight(h1, a1);
		GameHelper.fight(a1, h1);
		System.out.println("Held: " + h1.getHealth() + " HP");
		System.out.println("Enemy: " + a1.getHealth() + " HP");
	    }
	    GameHelper.fight(h1, a2);
	    GameHelper.overlap(h1, a1);
		

	}
	
	

}

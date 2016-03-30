package control;
import model.*;
import control.GameHelper;

public class TestKampf 
{
	public static void main(String[] args)
	{
		HeldenFahrzeug h1 = new HeldenFahrzeug();
		AlienNichtImmun a1 = new AlienNichtImmun();
		AlienImmun a2 = new AlienImmun();
		
	    while(a1.getHealth() != 0)
	    {
		GameHelper.fight(h1, a1);
		GameHelper.fight(a1, h1);
		System.out.println("Held: " + h1.getHealth() + " HP");
		System.out.println("Enemy: " + a1.getHealth() + " HP");
	    }
	    GameHelper.fight(h1, a2);
		

	}
	
	

}

package control;

import java.util.ArrayList;
import model.AlienNichtImmun;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.Position;
import model.SpielfeldObject;
/**
 * Innerhalb diese Test wird zunächst eine {@link GameWorld} erstellt. Auf dieser wird nun ein {@link AlienNichtImmun} und ein {@link HeldenFahrzeug} platziert.
 * Die Position wird per print Methode ausgegeben die sich in {@link Position} befindet.
 * Daraufhin kämpfen die beiden Objekte gegeneinander.
 * @author til
 *
 */
public class Test 
{
	static GameWorld world = new GameWorld();
	
	public static void main(String[] args)
	{
		AlienNichtImmun a1 = new AlienNichtImmun();
		HeldenFahrzeug h1 = new HeldenFahrzeug();
		new WorldGenerator().positionRandomizer(a1, world);
		new WorldGenerator().positionRandomizer(h1, world);
		h1.getPositon().print();
		a1.getPositon().print();
	
    while(a1.getHealth() != 0)
    {
	GameHelper.fight(h1, a1);
	GameHelper.fight(a1, h1);
	System.out.println("Held: " + h1.getHealth() + " HP");
	System.out.println("Enemy: " + a1.getHealth() + " HP");
    }
    GameHelper.fight(h1, a1);	
}

}

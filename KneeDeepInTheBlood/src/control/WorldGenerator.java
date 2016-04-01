package control;
import java.util.ArrayList;
import java.util.Random;

import model.*;

public class WorldGenerator 
{
	GameWorld world = new GameWorld();

	
	public void positionRandomizer(SpielfeldObject s1)
	{
		Random rand = new Random();
		s1.setPositon(new Position(rand.nextInt(((world.getWolrd_X()))) - s1.getWidth(),rand.nextInt(world.getWorld_Y() - s1.getHeight())));
		ArrayList<SpielfeldObject> tmp = world.getObjects();
		world.setObjects(tmp);
		System.out.println("VORHER Anzahl Objekte von "+ s1 +": "+ tmp.size());
		for(SpielfeldObject s2 : tmp)
		{
			if(GameHelper.overlap(s1, s2))
			{
				positionRandomizer(s1);
			}
		}
		tmp.add(s1);
		world.setObjects(tmp);
		System.out.println("NACHHER Anzahl Objekte von "+ s1 +": "+ tmp.size());
		
		
	}
	
	public static void main(String[] args)
	{
		GameWorld world = new GameWorld();
		AlienNichtImmun a1 = new AlienNichtImmun(new Position(0, 0));
		HeldenFahrzeug h1 = new HeldenFahrzeug(new Position(0, 0));
		new WorldGenerator().positionRandomizer(a1);
		new WorldGenerator().positionRandomizer(h1);
		ArrayList<SpielfeldObject> tmp = world.getObjects();
		h1.getPositon().print();
		a1.getPositon().print();
		for(SpielfeldObject s2 : tmp)
		{
			System.out.println(s2.getPositon().getX());
		}
	}
	
}

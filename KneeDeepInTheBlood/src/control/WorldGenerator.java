package control;
import java.util.ArrayList;
import java.util.Random;

import model.*;

public class WorldGenerator 
{
	GameWorld world = new GameWorld();
	AlienNichtImmun a1 = new AlienNichtImmun(new Position(0, 0));
	HeldenFahrzeug h1 = new HeldenFahrzeug(new Position(0, 0));
	
	public void positionRandomizer(SpielfeldObject s1)
	{
		Random rand = new Random();
		s1.setPositon(new Position(rand.nextInt(((world.getWolrd_X()))) - s1.getWidth(),rand.nextInt(world.getWorld_Y() - s1.getHeight())));
		ArrayList<SpielfeldObject> tmp = world.getObjects();
		for(SpielfeldObject s2 : tmp)
		{
			if(GameHelper.overlap(s1, s2))
			{
				positionRandomizer(s1);
			}
		}
		tmp.add(s1);
		world.setObjects(tmp);
		
		
	}
	
	public static void main(String[] args)
	{
		
	}
	
}

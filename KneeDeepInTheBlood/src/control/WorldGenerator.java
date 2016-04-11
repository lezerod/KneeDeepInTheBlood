package control;
import java.util.ArrayList;
import java.util.Random;

import model.*;

public class WorldGenerator 
{
	public void positionRandomizer(SpielfeldObject s1, GameWorld world)
	{
		Random rand = new Random();
		s1.setPositon(new Position(rand.nextInt(((world.getWolrd_X()))) - s1.getWidth(),rand.nextInt(world.getWorld_Y() - s1.getHeight())));
		ArrayList<SpielfeldObject> tmp = world.getObjects();
		world.setObjects(tmp);
		for(SpielfeldObject s2 : tmp)
		{
			if(GameHelper.overlap(s1, s2))
			{
				positionRandomizer(s1, world);
			}
		}
		tmp.add(s1);
		world.setObjects(tmp);
		
		
	}
	
		}

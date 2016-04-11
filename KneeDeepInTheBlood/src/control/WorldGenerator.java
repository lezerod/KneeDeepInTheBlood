package control;
import java.util.ArrayList;
import java.util.Random;

import model.*;
/**
 * Innerhalb dieser Klasse wird das Spielfeld generiert und die Objekte erhalten einen zuf�lligen Standort auf der Karte.
 * @author til
 *
 */
public class WorldGenerator 
{
	/**
	 * Die Methode generiert zun�chst zuf�llig f�r ein {@link SpielfeldObject} die Positionskoordinaten. Danach wird globale ArrayListe in eine tempor�re 
	 * ArrayListe reingeladen. Diese wird in einer Schleife durchlaufen und kontrolliert ob sich das neue Objekt mit einem aus der ArrayListe �berlappt.
	 * Dies wird mit der Methode overlap gepr�ft.Ist dies der Fall wird die Methode positionRandomizer nochmal aufgerufen. Wenn die Schleife ohne Fehler durchl�uft 
	 * wird das Objekt der tempor�ren ArrayListe hinzugef�gt und diese wird die neue globale ArrayListe. 
	 * @param s1
	 * @param world
	 */
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

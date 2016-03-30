package TillsTry;
/*
 * nicht relevant
 */
import java.util.ArrayList;
import java.util.Random;

public class gameWorld 
{
	public static final int WORLD_X = 640;
	public static final int WORLD_Y = 480;
	
	public static final int MAX_OBJECT_WIDTH = 160;
	public static final int MIN_OBJECT_WIDTH = 80;
	
	public ArrayList<GameObject>objects;
	
	private float spawnTime;
	private float spawnPause;
	private Random positionRandomizer;
	
	public int lifes;
	public int score;
	
	public gameWorld() 
	{
		objects = new ArrayList<GameObject>();
		spawnTime = 2;
		spawnPause = 1.0f;
		positionRandomizer = new Random();
		lifes = 3;
		score = 0;
	}
	
	public void update(float delta)
	{
		spawnTime += delta;
		
		if(spawnTime >= spawnPause)
		{
			spawnObject();
		}
		
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tmp = objects.get(i);
			
			tmp.update(delta);
			
			if(tmp.isRemovable)
			{
				if (!tmp.isClicked)
					lifes--;
				else
					score++;
				
				objects.remove(i);
			}
		}
		
		private void spawnObject()
		{
			int width = positionRandomizer.nextInt((MAX_OBJECT_WIDTH - MIN_OBJECT_WIDTH) + 1) + MIN_OBJECT_WIDTH;
			int height = positionRandomizer.nextInt((MAX_OBJECT_WIDTH - MIN_OBJECT_WIDTH + 1) + MIN_OBJECT_WIDTH);
			
			int x = positionRandomizer.nextInt(WORLD_X - width);
			int y = positionRandomizer.nextInt(WORLD_Y - height);
			
			GameObject objTmp = new GameObject(x, y, width, height);
			
			for(GameObject obj : objects)
			{
				if(objTmp.overlaps(obj)
						{
							spawnObject();
							return;
						}
			}
		}
	}
	
		
	
	
}

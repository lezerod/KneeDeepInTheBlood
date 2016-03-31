package model;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld 
{
	public final int Wolrd_X = 640;
	public final int World_Y = 480;
	
	public ArrayList<Gameobject>objects;
	
	private int spawnTime = 0;
	private int spawnPause = 0;
	private Random positionRandomizer;
	
	public int lifes = 0;
	public int score = 0;
	
	public GameWorld()
	{
		objects = new ArrayList<Gameobject>();
		spawnTime = 2;
		spawnPause = 1;
		positionRandomizer = new Random();
		lifes = 3;
		score = 0;
	}
	
}

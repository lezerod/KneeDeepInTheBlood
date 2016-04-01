package model;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld 
{
	private final int Wolrd_X;
	private final int World_Y;
	
	private ArrayList<SpielfeldObject>objects;
	
	private final int spawnTime;
	private final int spawnPause;
	
	private int lifes = 0;
	private int score = 0;
	
	
	
	public ArrayList<SpielfeldObject> getObjects() {
		return objects;
	}



	public void setObjects(ArrayList<SpielfeldObject> objects) {
		this.objects = objects;
	}



	public int getLifes() {
		return lifes;
	}



	public void setLifes(int lifes) {
		this.lifes = lifes;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public int getWolrd_X() {
		return Wolrd_X;
	}



	public int getWorld_Y() {
		return World_Y;
	}



	public int getSpawnTime() {
		return spawnTime;
	}



	public int getSpawnPause() {
		return spawnPause;
	}

	
	

	public GameWorld()
	{
		Wolrd_X = 640;
		World_Y = 480;
		objects = new ArrayList<SpielfeldObject>();
		spawnTime = 2;
		spawnPause = 1;
		lifes = 3;
		score = 0;
	}
	
}

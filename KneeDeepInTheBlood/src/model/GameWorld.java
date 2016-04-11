package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
/**
 * Mit dieser Klasse lässt sich das Grundgerüst der Gameworld erzeugen. Sie besitzt die Integer World_X und Y um die Größe des Spielfeldes einzustellen.
 * Außerdem besitzt einen integer namens life um die Leben des Spielers festzustellen und den Integer score um die Punkte zu zählebn die der Spieler sammelt 
 * während des Spielens. In der ArrayListe {@link Objects} werden die erstellten Objekte gespeichert die auf dem Spielfeld liegen. Im Konstruktor werden
 * den Variablen Werte zugewiesen.
 * @author til
 *
 */
public class GameWorld 
{
	private final int Wolrd_X;
	private final int World_Y;
	
	private ArrayList<SpielfeldObject>objects;
	
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
	public GameWorld()
	{
		Wolrd_X = 640;
		World_Y = 480;
		objects = new ArrayList<SpielfeldObject>();
		lifes = 3;
		score = 0;
	}
	
}

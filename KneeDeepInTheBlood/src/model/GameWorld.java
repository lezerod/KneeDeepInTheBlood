package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Mit dieser Klasse laesst sich das Grundgeruest der Gameworld erzeugen. Sie
 * besitzt die Integer World_X und Y um die Groeße des Spielfeldes einzustellen.
 * Außerdem besitzt einen integer namens life um die Leben des Spielers
 * festzustellen und den Integer score um die Punkte zu zaehlebn die der Spieler
 * sammelt waehrend des Spielens. In der ArrayListe {@link Objects} werden die
 * erstellten Objekte gespeichert die auf dem Spielfeld liegen. Im Konstruktor
 * werden den Variablen Werte zugewiesen.
 * 
 * @author til
 *
 */
public class GameWorld {
	private final int Wolrd_X;
	private final int World_Y;
	private MoveabelObject heldenfahrzeug;
	private ArrayList<GameObject> objects;

	private int score = 0;

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
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

	public GameWorld() {
		Wolrd_X = 640;
		World_Y = 480;
		objects = new ArrayList<GameObject>();
		score = 0;
	}

}

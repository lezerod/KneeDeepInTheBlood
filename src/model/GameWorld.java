package model;

import java.util.ArrayList;

/**
 * In dieser Klasse befinden sich alle Objekte, die auf dem Spielfeld dargestellt werden
 *
 * @author Julien, Till, Marco
 *
 */
public class GameWorld {

	private final int width;
	private final int height;

	private HeldenFahrzeug heldenfahrzeug;
	private ArrayList<GameObject> items;
	private ArrayList<Alien> aliens;
	private ArrayList<MoveableObject> projektile;
	private ArrayList<MoveableObject> projektileFriendly;

	private int score = 0;
	private int leben = 3;
	
	
	public GameWorld(int breite, int höhe) {
		heldenfahrzeug = new HeldenFahrzeug(false);
		items = new ArrayList<GameObject>();
		aliens = new ArrayList<Alien>();
		projektile = new ArrayList<MoveableObject>();
		projektileFriendly = new ArrayList<MoveableObject>();
		width = breite;
		height = höhe;
	}
	
	public ArrayList<MoveableObject> getProjektileFriendly() {
		return projektileFriendly;
	}

	public void setProjektileFriendly(ArrayList<MoveableObject> projektileFriendly) {
		this.projektileFriendly = projektileFriendly;
	}

	public HeldenFahrzeug getHeldenfahrzeug() {
		return heldenfahrzeug;
	}

	public void setHeldenfahrzeug(HeldenFahrzeug heldenfahrzeug) {
		this.heldenfahrzeug = heldenfahrzeug;
	}

	public ArrayList<GameObject> getItems() {
		return items;
	}

	public void setItems(ArrayList<GameObject> items) {
		this.items = items;
	}

	public ArrayList<Alien> getAliens() {
		return aliens;
	}

	public void setAliens(ArrayList<Alien> aliens) {
		this.aliens = aliens;
	}

	public ArrayList<MoveableObject> getProjektile() {
		return projektile;
	}

	public void setProjektile(ArrayList<MoveableObject> projektile) {
		this.projektile = projektile;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

//	public void spawnNew(){
//		if(aliens.)
//	}

}

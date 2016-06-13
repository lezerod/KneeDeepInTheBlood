package model;

import java.io.Serializable;
import java.util.ArrayList;

import controller.GameSettings;

/**
 * In dieser Klasse befinden sich alle Objekte, die auf dem Spielfeld
 * dargestellt werden
 *
 * @author Julien, Till, Marco
 *
 */
public class GameWorld implements Serializable{

	private final int width;
	private final int height;

	private int threadTicks;

	private HeldenFahrzeug heldenfahrzeug;
	private IwillDestroyYouTank iwillDestroyYouTank;
	private ArrayList<GameObject> items;
	private ArrayList<Alien> aliens;
	private ArrayList<MoveableObject> projektile;
	private ArrayList<MoveableObject> projektileFriendly;
	private ArrayList<MoveableObject> projektileClient;

	private int leben;
	private int aliensSlain;

	private int minutesToWin;

	private int ticksSinceLastSpwan;

	public GameWorld(int breite, int höhe) {
		heldenfahrzeug = new HeldenFahrzeug(false);
		iwillDestroyYouTank = new IwillDestroyYouTank(false);
		items = new ArrayList<GameObject>();
		aliens = new ArrayList<Alien>();
		projektile = new ArrayList<MoveableObject>();
		projektileFriendly = new ArrayList<MoveableObject>();
		projektileClient = new ArrayList<MoveableObject>();
		width = breite;
		height = höhe;
		leben = GameSettings.HELDENSTARTLEBEN;
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getThreadTicks() {
		return threadTicks;
	}

	public void setThreadTicks(int threadTicks) {
		this.threadTicks = threadTicks;
	}

	public int getLeben() {
		return leben;
	}

	public void setLeben(int leben) {
		this.leben = leben;
	}

	public int getAliensSlain() {
		return aliensSlain;
	}

	public void setAliensSlain(int aliensSlain) {
		this.aliensSlain = aliensSlain;
	}

	public int getMinutesToWin() {
		return minutesToWin;
	}

	public void setMinutesToWin(int minutesToWin) {
		this.minutesToWin = minutesToWin;
	}

	public int getTicksSinceLastSpwan() {
		return ticksSinceLastSpwan;
	}

	public void setTicksSinceLastSpwan(int ticksSinceLastSpwan) {
		this.ticksSinceLastSpwan = ticksSinceLastSpwan;
	}

	public IwillDestroyYouTank getIwillDestroyYouTank() {
		return iwillDestroyYouTank;
	}

	public void setIwillDestroyYouTank(IwillDestroyYouTank iwillDestroyYouTank) {
		this.iwillDestroyYouTank = iwillDestroyYouTank;
	}

	public ArrayList<MoveableObject> getProjektileClient() {
		return projektileClient;
	}

	public void setProjektileClient(ArrayList<MoveableObject> projektileClient) {
		this.projektileClient = projektileClient;
	}



}

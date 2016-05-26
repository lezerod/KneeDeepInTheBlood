package controller;

import controller.GameSettings;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import model.Alien;
import model.GameObject;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.MoveableObject;
import view.EventList;
import view.MainWindow;
import javafx.scene.input.KeyEvent;

/**
 * Diese Klasse stellt den Hauptthread des Spiels
 * dar. Er updated die ganze Zeit die GameWorld 
 * und ruft dann die update Methode in der View auf.
 * @author Julien
 *
 */
public class GameUpdateThread extends Thread implements EventList {

	private MainWindow view;
	private GameWorld gameWorld;

	//diese booleans geben den Status der Tasten an
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean topPressed = false;
	private boolean bottomPressed = false;
	private boolean spacePressed = false;
	
	/**
	 * Konstruktor
	 * @param gameWorld die Spielwelt
	 * @param view die View
	 */
	public GameUpdateThread(GameWorld gameWorld, MainWindow view) {
		this.gameWorld = gameWorld;
		this.view = view;
	}

	/**
	 * Run Methode des Threads
	 */
	@Override
	public void run() {

		createStartModel();
		
		view.updateView(gameWorld);
		
		//EventsListener eintragen
		view.registerEventListener(this);
		
		
		//Dauerschleife des Spiels
		while (true) {
		
		try {
			Thread.sleep(GameSettings.THREADTICKTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		updateModel();
		
		view.updateView(gameWorld);
		}
	}
	
	/**
	 * erzeugt den Startzustand des Models
	 */
	private void createStartModel() {

		// Create some aliens:
		Alien alien;
		for (int i = 0; i < 10; i++) {
			alien = new Alien(false);
			float x = (float) Math.random() * (GameSettings.BREITE - GameSettings.ALIENBREITE);
			float y = (float) Math.random() * (GameSettings.H�HE - GameSettings.ALIENH�HE);
			alien.setPosition(x, y, GameSettings.ALIENBREITE, GameSettings.ALIENH�HE);
			alien.setSpeed(GameSettings.ALIENSPEED);
			gameWorld.getAliens().add(alien);
		}

		// Create the 'HeldenFahrzeug':
		HeldenFahrzeug heldenFahrzeug = new HeldenFahrzeug(false);
		// genau in der Mitte:
		heldenFahrzeug.setPosition((GameSettings.BREITE - (GameSettings.HELDENBREITE / 2)) / 2,
				(GameSettings.H�HE - (GameSettings.HELDENH�HE / 2)) / 2, GameSettings.HELDENBREITE,
				GameSettings.HELDENH�HE);
		heldenFahrzeug.setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
		gameWorld.setHeldenfahrzeug(heldenFahrzeug);

	}
	
	/**
	 * updated das gesamte Model
	 */
	private void updateModel() {
		updateAliens();
		updateProjektile();
		updateHeldenFahrzeug();
	}
	
	private void updateAliens() {
		for (int i = 0;i<gameWorld.getAliens().size();i++) {
			Alien aktAlien = gameWorld.getAliens().get(i);
			
			if (Math.random() <= GameSettings.ALIENCHANGETURNINGCHANCE) {
				aktAlien.setWinkel((float)(360*Math.random()));
			}
		
			aktAlien.move(false);
			
			aktAlien.erh�heLastShot();
			
			if (aktAlien.getLastShot()>=GameSettings.ALIENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(aktAlien.getX(), aktAlien.getY(), GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILH�HE);
				movObj.setSpeed(GameSettings.PROJEKTILSPEED);
				movObj.setWinkel((float)(360*Math.random()));
				gameWorld.getProjektile().add(movObj);
				aktAlien.setLastShot(0);
			}
			
			//pr�fen, dass die Aliens nicht ausserhalb des Fensters sind:
			korrigierePosition(aktAlien, false);
		}
	}
	
	/**
	 * updated die Projektile
	 */
	private void updateProjektile() {
		updateEnemyProjektile();
		updateFriendlyProjektile();
	}
	
	/**
	 * updated die gegnerischen Projektile
	 */
	private void updateEnemyProjektile() {
		//update projektile
				for (int i = 0;i<gameWorld.getProjektile().size();i++) {
					MoveableObject aktProjektil = gameWorld.getProjektile().get(i);
				
					aktProjektil.move(false);
					
					// pr�fen, dass die Projektile nicht ausserhalb des Fensters sind:
					 korrigierePosition(aktProjektil, true);
					 
				}
	}
	
	/**
	 * updated die freundlichen Projektile
	 */
	private void updateFriendlyProjektile() {
		for (int i = 0;i<gameWorld.getProjektileFriendly().size();i++) {
			MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
		
			aktProjektil.move(false);
			
			//pr�fen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);
			
		}
	}
	
	
	/**
	 * Update des Heldenfahrzeugs
	 */
	private void updateHeldenFahrzeug() {
		if (topPressed) {
			gameWorld.getHeldenfahrzeug().move(false);
		}
		if (bottomPressed) {
			gameWorld.getHeldenfahrzeug().move(true);
		}
		if (leftPressed) gameWorld.getHeldenfahrzeug().setWinkel(gameWorld.getHeldenfahrzeug().getWinkel()-GameSettings.HELDENWINKELCHANGESPEED);
		if (rightPressed) gameWorld.getHeldenfahrzeug().setWinkel(gameWorld.getHeldenfahrzeug().getWinkel()+GameSettings.HELDENWINKELCHANGESPEED);
		
		gameWorld.getHeldenfahrzeug().erh�heLastShot();
		
		if (spacePressed && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE)  {
			MoveableObject movObj = new MoveableObject();
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2), gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2), GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILH�HE);
			movObj.setSpeed(GameSettings.PROJEKTILSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
			}
		
		//pr�fen, dass das Heldenfahrezeug nicht ausserhalb des Fensters ist und ggf. position korrigieren:
		korrigierePosition(gameWorld.getHeldenfahrzeug(), false);
		
	}

	/**
	 * KeyDown-Event Behandlung
	 */
	@Override
	public void raiseKeyDownEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)bottomPressed=true;
		if (e.getCode() == KeyCode.UP)topPressed=true;
		if (e.getCode() == KeyCode.LEFT)leftPressed=true;
		if (e.getCode() == KeyCode.RIGHT)rightPressed=true;
		if (e.getCode() == KeyCode.SPACE)spacePressed=true;
	}
	
	/**
	 * Keyup-Event Behandlung
	 */
	@Override
	public void raiseKeyUpEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)bottomPressed=false;
		if (e.getCode() == KeyCode.UP)topPressed=false;
		if (e.getCode() == KeyCode.LEFT)leftPressed=false;
		if (e.getCode() == KeyCode.RIGHT)rightPressed=false;
		if (e.getCode() == KeyCode.SPACE)spacePressed=false;
		
	}
	
	/**
	 * Methode pr�ft, ob das Spielfeldobject ausserhalb des Spielfeldes
	 * ist, und setzt das Object dann ggf. wieder ins Spielfeld.
	 * @param gameObject Das Spielfeldobjekt
	 * @param deleteObj true, wenn es gel�scht werden soll, false wenn es
	 * zur�ck ins Spielfeld gesetzt werden soll
	 */
	private  void korrigierePosition(GameObject gameObject, boolean deleteObj) {
		boolean ausserhalb = false;
		if (gameObject.getY() < 0) {
			if (!deleteObj) gameObject.setY(0);
			else ausserhalb = true;
		}
		if (gameObject.getY() > (GameSettings.H�HE-gameObject.getHeight())) {
			if (!deleteObj) gameObject.setY(GameSettings.H�HE-gameObject.getHeight());
			else ausserhalb = true;
		}
		if (gameObject.getX() < 0) {
			if (!deleteObj) gameObject.setX(0);
			else ausserhalb = true;
		}
		if (gameObject.getX() > (GameSettings.BREITE-gameObject.getWidth())) {
			if (!deleteObj) gameObject.setX(GameSettings.BREITE-gameObject.getWidth());
			else ausserhalb = true;
		}
		if (ausserhalb) {
			gameWorld.getProjektile().remove(gameObject);
			gameWorld.getProjektileFriendly().remove(gameObject);
		}
	}
	
	/**
	 * Funktion pr�ft, ob zwei Spielfeldobjekte kollidieren
	 * @param gameObjectA Das erste Spielfeldobjekt
	 * @param gameObjectB Das zweite Spielfeldobject
	 * @return true, wenn sie kollidieren
	 */
	private static boolean checkForCollision(GameObject gameObjectA, GameObject gameObjectB) {
		return false;
		
	}

}

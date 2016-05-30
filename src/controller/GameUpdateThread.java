package controller;

import controller.GameSettings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
 * Diese Klasse stellt den Hauptthread des Spiels dar. Er updated die ganze Zeit
 * die GameWorld und ruft dann die update Methode in der View auf.
 * 
 * @author Planung Struktur/Grundkonzept/Spielkonzept: Julien, Till, Marco;
 *         Umsetzung: Julien
 *
 */
public class GameUpdateThread extends Thread implements EventList {

	private MainWindow view;
	private GameWorld gameWorld;

	private boolean gameIsRunning = false;

	// diese booleans geben den Status der Tasten an
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean topPressed = false;
	private boolean bottomPressed = false;
	private boolean spacePressed = false;

	/**
	 * Konstruktor
	 * 
	 * @param gameWorld
	 *            die Spielwelt
	 * @param view
	 *            die View
	 */
	public GameUpdateThread(GameWorld gameWorld, MainWindow view) {
		this.gameWorld = gameWorld;
		this.view = view;
	}

	/**
	 * MainMethode des Threads
	 */
	@Override
	public void run() {

		init();

		// Dauerschleife des Spiels
		while (true) {

			try {
				Thread.sleep(GameSettings.THREADTICKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (gameIsRunning) {

				updateModel();

				view.updateView(gameWorld);

			}
		}

	}

	/**
	 * registriert diese Instanz als Listener
	 */
	private void init() {
		// EventsListener eintragen
		view.registerEventListener(this);
	}

	/**
	 * diese Methode startet ein neues Spiel
	 * 
	 * @param schwierigkeitsgrad
	 *            Easy, Middle oder hard
	 */
	private void startNewGame(int schwierigkeitsgrad) {
		topPressed = false;
		bottomPressed = false;
		leftPressed = false;
		rightPressed = false;
		spacePressed = false;
		if (schwierigkeitsgrad == 0)
			gameWorld.setMinutesToWin(GameSettings.EASYLIFETIME);
		else if (schwierigkeitsgrad == 1)
			gameWorld.setMinutesToWin(GameSettings.MIDDLELIFETIME);
		else if (schwierigkeitsgrad == 2)
			gameWorld.setMinutesToWin(GameSettings.HARDLIFETIME);

		createStartModel();
		gameIsRunning = true;
		view.switchScene(view.getSceneGame());
	}

	/**
	 * erzeugt den Startzustand des Models
	 */
	private void createStartModel() {

		gameWorld.setLeben(GameSettings.HELDENSTARTLEBEN);
		gameWorld.setAliensSlain(0);
		gameWorld.setThreadTicks(0);

		gameWorld.getAliens().clear();
		gameWorld.getProjektile().clear();
		gameWorld.getProjektileFriendly().clear();
		gameWorld.setHeldenfahrzeug(null);

		// Create some aliens:
		Alien alien;
		for (int i = 0; i < GameSettings.ALIENMAXANZAHL; i++) {
			alien = new Alien(false);
			float x = (float) Math.random() * (GameSettings.BREITE - GameSettings.ALIENBREITE);
			float y = (float) Math.random() * (GameSettings.HÖHE - GameSettings.ALIENHÖHE);
			alien.setPosition(x, y, GameSettings.ALIENBREITE, GameSettings.ALIENHÖHE);
			alien.setSpeed(GameSettings.ALIENSPEED);
			gameWorld.getAliens().add(alien);
		}

		// Create the 'HeldenFahrzeug':
		HeldenFahrzeug heldenFahrzeug = new HeldenFahrzeug(false);
		// genau in der Mitte:
		heldenFahrzeug.setPosition((GameSettings.BREITE - (GameSettings.HELDENBREITE / 2)) / 2,
				(GameSettings.HÖHE - (GameSettings.HELDENHÖHE / 2)) / 2, GameSettings.HELDENBREITE,
				GameSettings.HELDENHÖHE);
		heldenFahrzeug.setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
		gameWorld.setHeldenfahrzeug(heldenFahrzeug);

	}

	/**
	 * updated das gesamte Model
	 */
	private void updateModel() {
		// prüfen, ob die zeit um ist.
		if (gameWorld.getThreadTicks() > (gameWorld.getMinutesToWin() * 60 * 1000 / GameSettings.THREADTICKTIME)) {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
					alert.setTitle("Spiel zu Ende");
					alert.setHeaderText("Herzlichen Glückwunsch! Sie haben das Spiel gewonnen.");
					alert.setContentText("Möchten sie nocheinmal spielen?");
					gameIsRunning = false;
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						view.switchScene(view.getSceneNewGame());
					} else if (alert.getResult() == ButtonType.NO) {
						view.switchScene(view.getSceneMainMenu());
					}

				};
			});

			gameIsRunning = false;
			return;
		}
		gameWorld.setThreadTicks(gameWorld.getThreadTicks() + 1);
		updateAliens();
		updateProjektile();
		updateHeldenFahrzeug();

		checkCollisions();

		checkForNewSpawn();
		gameWorld.setTicksSinceLastSpwan(gameWorld.getTicksSinceLastSpwan() + 1);
	}

	/**
	 * diese Methode updated die Aliens auf dem Spielfeld
	 */
	private void updateAliens() {
		for (int i = 0; i < gameWorld.getAliens().size(); i++) {
			Alien aktAlien = gameWorld.getAliens().get(i);

			if (Math.random() <= GameSettings.ALIENCHANGETURNINGCHANCE) {
				aktAlien.setWinkel((float) (360 * Math.random()));
			}

			aktAlien.move(false);

			aktAlien.erhöheLastShot();

			if (aktAlien.getLastShot() >= GameSettings.ALIENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(aktAlien.getX(), aktAlien.getY(), GameSettings.PROJEKTILBREITE,
						GameSettings.PROJEKTILHÖHE);
				movObj.setSpeed(GameSettings.PROJEKTILENEMYSPEED);
				movObj.setWinkel((float) (360 * Math.random()));
				gameWorld.getProjektile().add(movObj);
				aktAlien.setLastShot(0);
			}

			// prüfen, dass die Aliens nicht ausserhalb des Fensters sind:
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
		// update projektile
		for (int i = 0; i < gameWorld.getProjektile().size(); i++) {
			MoveableObject aktProjektil = gameWorld.getProjektile().get(i);

			aktProjektil.move(false);

			// prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);

		}
	}

	/**
	 * updated die freundlichen Projektile
	 */
	private void updateFriendlyProjektile() {
		for (int i = 0; i < gameWorld.getProjektileFriendly().size(); i++) {
			MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);

			aktProjektil.move(false);

			// prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
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
		if (leftPressed)
			gameWorld.getHeldenfahrzeug()
					.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel() - GameSettings.HELDENWINKELCHANGESPEED);
		if (rightPressed)
			gameWorld.getHeldenfahrzeug()
					.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel() + GameSettings.HELDENWINKELCHANGESPEED);

		gameWorld.getHeldenfahrzeug().erhöheLastShot();

		if (spacePressed && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE) {
			MoveableObject movObj = new MoveableObject();
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
					gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
					GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
			movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
		}

		// prüfen, dass das Heldenfahrezeug nicht ausserhalb des Fensters ist
		// und ggf. position korrigieren:
		korrigierePosition(gameWorld.getHeldenfahrzeug(), false);

	}

	/**
	 * diese Methode überprüft alle möglichen Kollisionen und behandelt diese
	 * ggf.
	 */
	private void checkCollisions() {
		for (int i = 0; i < gameWorld.getProjektile().size(); i++) {
			if (checkForCollision(gameWorld.getProjektile().get(i), gameWorld.getHeldenfahrzeug())) {
				gameWorld.setLeben(gameWorld.getLeben() - 1);
				gameWorld.getProjektile().remove(i);
				i--;
				if (gameWorld.getLeben() <= 0) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
							alert.setTitle("Spiel zu Ende");
							alert.setHeaderText("Sie wurden leider zu oft getroffen und haben verloren.");
							alert.setContentText("Möchten sie nocheinmal spielen?");
							gameIsRunning = false;
							alert.showAndWait();
							if (alert.getResult() == ButtonType.YES) {
								view.switchScene(view.getSceneNewGame());
							} else if (alert.getResult() == ButtonType.NO) {
								view.switchScene(view.getSceneMainMenu());
							}

						};
					});

					return;
				}
				break;
			}
		}

		for (int i = 0; i < gameWorld.getProjektileFriendly().size(); i++) {
			for (int j = 0; j < gameWorld.getAliens().size(); j++) {

				try { // TODO SCHLEIFENPROBLEM MUSS NOCH BEHOBEN WERDEN
						// !!!!!!!!!!!!!!!!!!!! ! Nur Übergangslösung !
					MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
					Alien aktAlien = gameWorld.getAliens().get(j);
					if (checkForCollision(aktProjektil, aktAlien)) {

						gameWorld.getProjektileFriendly().remove(i);
						gameWorld.getAliens().remove(j);
						gameWorld.setAliensSlain(gameWorld.getAliensSlain() + 1);
						i--;
						j--;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

	}

	/**
	 * diese Methode überprüft, ob ein neues Alien spawnen soll und kann
	 */
	private void checkForNewSpawn() {
		if ((gameWorld.getTicksSinceLastSpwan() > GameSettings.ALIENRESPAWNRATE)
				&& gameWorld.getAliens().size() < GameSettings.ALIENMAXANZAHL) {
			Alien newAlien = new Alien(false);
			newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
					(float) (Math.random() * GameSettings.HÖHE), GameSettings.ALIENBREITE, GameSettings.ALIENHÖHE);
			newAlien.setSpeed(GameSettings.ALIENSPEED);
			gameWorld.getAliens().add(newAlien);
			gameWorld.setTicksSinceLastSpwan(0);
		}
	}

	/**
	 * KeyDown-Event Behandlung
	 */
	@Override
	public void raiseKeyDownEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)
			bottomPressed = true;
		if (e.getCode() == KeyCode.UP)
			topPressed = true;
		if (e.getCode() == KeyCode.LEFT)
			leftPressed = true;
		if (e.getCode() == KeyCode.RIGHT)
			rightPressed = true;
		if (e.getCode() == KeyCode.SPACE)
			spacePressed = true;
		if (e.getCode() == KeyCode.ESCAPE) {
			gameIsRunning = false;
			Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Spiel beenden");
			alert.setHeaderText("Möchten sie das Spiel wirklich beenden?");
			alert.setContentText("Der Spielstand geht verloren.");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				view.switchScene(view.getSceneMainMenu());
			} else if (alert.getResult() == ButtonType.NO) {
				gameIsRunning = true;
			}

		}
	}

	/**
	 * Keyup-Event Behandlung
	 */
	@Override
	public void raiseKeyUpEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)
			bottomPressed = false;
		if (e.getCode() == KeyCode.UP)
			topPressed = false;
		if (e.getCode() == KeyCode.LEFT)
			leftPressed = false;
		if (e.getCode() == KeyCode.RIGHT)
			rightPressed = false;
		if (e.getCode() == KeyCode.SPACE)
			spacePressed = false;

	}

	/**
	 * Methode prüft, ob das Spielfeldobject ausserhalb des Spielfeldes ist, und
	 * setzt das Object dann ggf. wieder ins Spielfeld.
	 * 
	 * @param gameObject
	 *            Das Spielfeldobjekt
	 * @param deleteObj
	 *            true, wenn es gelöscht werden soll, false wenn es zurück ins
	 *            Spielfeld gesetzt werden soll
	 */
	private void korrigierePosition(GameObject gameObject, boolean deleteObj) {
		boolean ausserhalb = false;
		if (gameObject.getY() < 0) {
			if (!deleteObj)
				gameObject.setY(0);
			else
				ausserhalb = true;
		}
		if (gameObject.getY() > (GameSettings.HÖHE - gameObject.getHeight())) {
			if (!deleteObj)
				gameObject.setY(GameSettings.HÖHE - gameObject.getHeight());
			else
				ausserhalb = true;
		}
		if (gameObject.getX() < 0) {
			if (!deleteObj)
				gameObject.setX(0);
			else
				ausserhalb = true;
		}
		if (gameObject.getX() > (GameSettings.BREITE - gameObject.getWidth())) {
			if (!deleteObj)
				gameObject.setX(GameSettings.BREITE - gameObject.getWidth());
			else
				ausserhalb = true;
		}
		if (ausserhalb) {
			gameWorld.getProjektile().remove(gameObject);
			gameWorld.getProjektileFriendly().remove(gameObject);
		}
	}

	/**
	 * Funktion prüft, ob zwei Spielfeldobjekte kollidieren
	 * 
	 * @param gameObjectA
	 *            Das erste Spielfeldobjekt
	 * @param gameObjectB
	 *            Das zweite Spielfeldobject
	 * @return true, wenn sie kollidieren
	 */
	private boolean checkForCollision(GameObject gameObjectA, GameObject gameObjectB) {
		float[] position1 = { 0, 0 };
		position1[0] = gameObjectA.getX();
		position1[1] = gameObjectA.getY();
		float[] position2 = { 0, 0 };
		position2[0] = gameObjectB.getX();
		position2[1] = gameObjectB.getY();
		float height1 = gameObjectA.getHeight();
		float height2 = gameObjectB.getHeight();
		float width1 = gameObjectA.getWidth();
		float width2 = gameObjectB.getWidth();
		// Formel für die Kollisionsberechnung
		return position1[0] < position2[0] + width2 && position2[0] < position1[0] + width1
				&& position1[1] < position2[1] + height2 && position2[1] < position1[1] + height1;
	}

	@Override
	public void raiseMenuClick(int menuIndex) {
		if (menuIndex == 0) {
			view.switchScene(view.getSceneNewGame());
		} else if (menuIndex == 2) {
			view.switchScene(view.getSceneSettings());
		} else if (menuIndex == 3) {
			System.exit(0);
		}
	}

	@Override
	public void raiseNewGameClick(int menuIndex) {
		if (menuIndex < 3) {
			startNewGame(menuIndex);
		} else if (menuIndex == 3) {
			view.switchScene(view.getSceneMainMenu());
		}
	}

	@Override
	public void raiseSettingsClick(int menuIndex) {

	}

	@Override
	public void raiseConnectClick(int menuIndex) {

	}

}

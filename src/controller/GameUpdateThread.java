package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
import model.IwillDestroyYouTank;
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
	private boolean enableSounds = true;
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
		view.playMusic(GameSettings.MUSICBACKGROUND);
	}

	/**
	 * MainMethode des Threads
	 */
	@Override
	public void run() {

		init();
		// Starten des Netzwerkes
		try {
			new ServerThreadTCPControl(gameWorld).start();
			new ServerThreadTCPWorld(gameWorld).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
				if (gameWorld.getIwillDestroyYouTank().isConnected()) {
					view.activateIwillDestroyYouTank(gameWorld.getIwillDestroyYouTank());
				}

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
		synchronized (gameWorld) {

<<<<<<< HEAD
			gameWorld.setLeben(GameSettings.HELDENSTARTLEBEN);
			gameWorld.setAliensSlain(0);
			gameWorld.setThreadTicks(0);

			gameWorld.getAliens().clear();
			gameWorld.getImmunAliens().clear();
			gameWorld.getProjektile().clear();
			gameWorld.getProjektileFriendly().clear();
			gameWorld.setHeldenfahrzeug(null);

			// Create some aliens:
			Alien alien;
			for (int i = 0; i < GameSettings.ALIENMAXANZAHL; i++) {
				alien = new Alien(false);
				float x = (float) Math.random() * (GameSettings.BREITE - GameSettings.ALIENBREITE);
				float y = (float) Math.random() * (GameSettings.HOEHE - GameSettings.ALIENHOEHE);
				alien.setPosition(x, y, GameSettings.ALIENBREITE, GameSettings.ALIENHOEHE);
				alien.setSpeed(GameSettings.ALIENSPEED);
				gameWorld.getAliens().add(alien);
			}
=======
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

		// Create the 'IwillDestroyYouTaon'
		IwillDestroyYouTank iwillDestroyYouTank = new IwillDestroyYouTank(false);
		iwillDestroyYouTank.setPosition((GameSettings.BREITE - (GameSettings.HELDENBREITE / 2)) / 2,
				(GameSettings.HÖHE - (GameSettings.HELDENHÖHE / 2)) / 2, GameSettings.HELDENBREITE,
				GameSettings.HELDENHÖHE);
		iwillDestroyYouTank.setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
		gameWorld.setIwillDestroyYouTank(iwillDestroyYouTank);
>>>>>>> origin/master

			// Create the 'HeldenFahrzeug':
			HeldenFahrzeug heldenFahrzeug = new HeldenFahrzeug(false);
			// genau in der Mitte:
			heldenFahrzeug.setPosition((GameSettings.BREITE - (GameSettings.HELDENBREITE / 2)) / 2,
					(GameSettings.HOEHE - (GameSettings.HELDENHOEHE / 2)) / 2, GameSettings.HELDENBREITE,
					GameSettings.HELDENHOEHE);
			heldenFahrzeug.setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
			gameWorld.setHeldenfahrzeug(heldenFahrzeug);

			// Create the 'IwillDestroyYouTaon'
			IwillDestroyYouTank iwillDestroyYouTank = new IwillDestroyYouTank(false);
			iwillDestroyYouTank.setPosition((GameSettings.BREITE - (GameSettings.HELDENBREITE / 2)) / 2,
					(GameSettings.HOEHE - (GameSettings.HELDENHOEHE / 2)) / 2, GameSettings.HELDENBREITE,
					GameSettings.HELDENHOEHE);
			iwillDestroyYouTank.setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
			gameWorld.setIwillDestroyYouTank(iwillDestroyYouTank);

		}
	}

	/**
	 * updated das gesamte Model
	 */
	private void updateModel() {
		synchronized (gameWorld) {

			// pruefen, ob die zeit um ist.
			if (gameWorld.getThreadTicks() > (gameWorld.getMinutesToWin() * 60 * 1000 / GameSettings.THREADTICKTIME)) {

<<<<<<< HEAD
				Platform.runLater(new Runnable() {
=======
		// prüfen, ob die zeit um ist.
		if (gameWorld.getThreadTicks() > (gameWorld.getMinutesToWin() * 60 * 1000 / GameSettings.THREADTICKTIME)) {

			Platform.runLater(new Runnable() {
>>>>>>> origin/master

					@Override
					public void run() {

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master

					};
				});

				gameIsRunning = false;
				return;
			}
			gameWorld.setThreadTicks(gameWorld.getThreadTicks() + 1);
			updateAliens(gameWorld.getAliens());
			updateAliens(gameWorld.getImmunAliens());
			updateProjektile();
			updateHeldenFahrzeug();
			updateItems();
			korrigierePosition(gameWorld.getIwillDestroyYouTank(), false);
			checkCollisions();

			checkForNewSpawn();
			gameWorld.setTicksSinceLastSpwan(gameWorld.getTicksSinceLastSpwan() + 1);
		}
	}

	/**
	 * diese Methode updated die Aliens auf dem Spielfeld
	 */
	private void updateAliens(ArrayList<Alien> aliens) {
		synchronized (gameWorld) {

			for (int i = 0; i < aliens.size(); i++) {
				Alien aktAlien = aliens.get(i);

				if (Math.random() <= GameSettings.ALIENCHANGETURNINGCHANCE) {
					aktAlien.setWinkel((float) (360 * Math.random()));
				}

				aktAlien.move(false);

				aktAlien.erhoeheLastShot();

<<<<<<< HEAD
				if (aktAlien.getLastShot() >= GameSettings.ALIENFEUERRATE) {
					MoveableObject movObj = new MoveableObject();
					movObj.setPosition(aktAlien.getX(), aktAlien.getY(), GameSettings.PROJEKTILBREITE,
							GameSettings.PROJEKTILHOEHE);
					movObj.setSpeed(GameSettings.PROJEKTILENEMYSPEED);
					movObj.setWinkel((float) (360 * Math.random()));
					gameWorld.getProjektile().add(movObj);
					aktAlien.setLastShot(0);
				}

				// pruefen, dass die Aliens nicht ausserhalb des Fensters sind:
				korrigierePosition(aktAlien, false);
			}
=======
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
>>>>>>> origin/master
		}
	}

	/**
	 * updated die Projektile
	 */
	private void updateProjektile() {
		updateEnemyProjektile();
		updateFriendlyProjektile();
		updateiWillProjektile();
	}

	/**
	 * updated die gegnerischen Projektile
	 */
	private void updateEnemyProjektile() {
		synchronized (gameWorld) {

			// update projektile
			for (int i = 0; i < gameWorld.getProjektile().size(); i++) {
				MoveableObject aktProjektil = gameWorld.getProjektile().get(i);

				aktProjektil.move(false);

<<<<<<< HEAD
				// pruefen, dass die Projektile nicht ausserhalb des Fensters
				// sind:
				korrigierePosition(aktProjektil, true);
=======
			// prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);
>>>>>>> origin/master

			}
		}
	}

	/**
	 * updated die freundlichen Projektile
	 */
	private void updateFriendlyProjektile() {
		synchronized (gameWorld) {

			for (int i = 0; i < gameWorld.getProjektileFriendly().size(); i++) {
				MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);

				aktProjektil.move(false);

<<<<<<< HEAD
				// pruefen, dass die Projektile nicht ausserhalb des Fensters
				// sind:
				korrigierePosition(aktProjektil, true);
=======
			// prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);
>>>>>>> origin/master

			}
		}
<<<<<<< HEAD
	}

	/**
	 * updated die Projektile des IwillDestoryYouTank
	 */
	private void updateiWillProjektile() {
=======
	/**
	 * updated die Projektile des Clients
	 */
	private void updateiWillProjektile(){
>>>>>>> origin/master
		synchronized (gameWorld) {
			for (int i = 0; i < gameWorld.getProjektileClient().size(); i++) {
				MoveableObject aktProjektil = gameWorld.getProjektileClient().get(i);

				aktProjektil.move(false);

<<<<<<< HEAD
				// pruefen, dass die Projektile nicht ausserhalb des Fensters
				// sind:
=======
				// prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
>>>>>>> origin/master
				korrigierePosition(aktProjektil, true);

			}
		}
	}

	/**
	 * Update des Heldenfahrzeugs
	 */
	private void updateHeldenFahrzeug() {
		synchronized (gameWorld) {

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

<<<<<<< HEAD
			gameWorld.getHeldenfahrzeug().erhoeheLastShot();

			if (spacePressed && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE) {
				if (enableSounds) {
					view.playSound(GameSettings.SFXSHOTFIRED);
				}
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(
						gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
						gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
						GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHOEHE);
				movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
				movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
				gameWorld.getProjektileFriendly().add(movObj);
				gameWorld.getHeldenfahrzeug().setLastShot(0);
			}
		}

		// pruefen, dass das Heldenfahrezeug nicht ausserhalb des Fensters ist
=======
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
			if (enableSounds) {
				view.playSound(GameSettings.SFXSHOTFIRED);
			}
			MoveableObject movObj = new MoveableObject();
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
					gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
					GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
			movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
		}}

		// prüfen, dass das Heldenfahrezeug nicht ausserhalb des Fensters ist
>>>>>>> origin/master
		// und ggf. position korrigieren:
		korrigierePosition(gameWorld.getHeldenfahrzeug(), false);

	}
<<<<<<< HEAD

	/**
	 * spawnt m�gliche Items
	 */
	private void updateItems() {
		if (gameWorld.getItems().size() < 1) {
			Random rand = new Random();
			int randint = rand.nextInt(10000);
			if (randint <= 50) {
				int x = rand.nextInt(GameSettings.BREITE);
				int y = rand.nextInt(GameSettings.HOEHE);
				GameObject bfg = new GameObject();
				bfg.setPosition(x, y, 20, 20);
				gameWorld.getItems().add(bfg);
			}
		}
	}

	/**
	 * diese Methode ueberprueft alle moeglichen Kollisionen und behandelt diese
=======
	/**
	 * updated die Items auf der GameWorld.
	 */
	private void updateItems(){
		if(gameWorld.getItems().size() < 1){
		Random rand = new Random();
		int randint = rand.nextInt(10000);
		if(randint <= 50){
			int x = rand.nextInt(GameSettings.BREITE);
			int y = rand.nextInt(GameSettings.HÖHE);
			GameObject bfg = new GameObject();
			bfg.setPosition(x, y, 20, 20);
			gameWorld.getItems().add(bfg);
		}}
	}

	/**
	 * diese Methode überprüft alle möglichen Kollisionen und behandelt diese
>>>>>>> origin/master
	 * ggf.
	 */
	private void checkCollisions() {
		synchronized (gameWorld) {

			for (int i = 0; i < gameWorld.getProjektile().size(); i++) {
				if (checkForCollision(gameWorld.getProjektile().get(i), gameWorld.getHeldenfahrzeug())) {
					if (enableSounds) {
						view.playSound(GameSettings.SFXHELDENHIT);
					}
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

<<<<<<< HEAD
					try {
						MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
						Alien aktAlien = gameWorld.getAliens().get(j);
						if (checkForCollision(aktProjektil, aktAlien)) {
							if (enableSounds) {
								view.playSound(GameSettings.SFXALIENHIT);
=======
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
>>>>>>> origin/master
							}
							gameWorld.getProjektileFriendly().remove(i);
							gameWorld.getAliens().remove(j);
							gameWorld.setAliensSlain(gameWorld.getAliensSlain() + 1);
							i--;
							j--;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				for (int j = 0; j < gameWorld.getImmunAliens().size(); j++) {

					try {
						MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
						Alien aktAlien = gameWorld.getImmunAliens().get(j);
						if (checkForCollision(aktProjektil, aktAlien)
								&& gameWorld.getHeldenfahrzeug().isHasSpezialWeapon()) {
							if (enableSounds) {
								view.playSound(GameSettings.SFXALIENHIT);
							}
							gameWorld.getProjektileFriendly().remove(i);
							gameWorld.getImmunAliens().remove(j);
							gameWorld.setAliensSlain(gameWorld.getAliensSlain() + 1);
							i--;
							j--;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			for (int i = 0; i < gameWorld.getProjektileClient().size(); i++) {
				if (checkForCollision(gameWorld.getProjektileClient().get(i), gameWorld.getHeldenfahrzeug())) {
					gameWorld.getHeldenfahrzeug().setSpeed(1);
					gameWorld.getProjektileClient().remove(i);

					new Thread() {
						public void run() {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							gameWorld.getHeldenfahrzeug().setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
						}
					}.start();

				}
				for (int j = 0; j < gameWorld.getAliens().size(); j++) {
					try {
						MoveableObject aktProjektil = gameWorld.getProjektileClient().get(i);
						Alien aktAlien = gameWorld.getAliens().get(j);
						if (checkForCollision(aktProjektil, aktAlien)) {
							if (enableSounds) {
								view.playSound(GameSettings.SFXALIENHIT);
							}
							gameWorld.getProjektileClient().remove(i);
							gameWorld.getAliens().remove(j);
							i--;
							j--;

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
			if (gameWorld.getItems().size() != 0) {
				for (int j = 0; j < gameWorld.getItems().size(); j++) {
					if (checkForCollision(gameWorld.getHeldenfahrzeug(), gameWorld.getItems().get(j))) {
						gameWorld.getHeldenfahrzeug().setHasSpezialWeapon(true);
						gameWorld.getItems().remove(j);
						System.out.println("BFG!");

						new Thread() {
							public void run() {
								try {
									Thread.sleep(GameSettings.HELDENSPEZIALWAFFEDAUER);
								} catch (Exception e) {
									e.printStackTrace();
								}
								gameWorld.getHeldenfahrzeug().setHasSpezialWeapon(false);
							}
						}.start();
					}
				}
			}
		}
	}

	/**
<<<<<<< HEAD
	 * diese Methode ueberprueft, ob ein neues Alien spawnen soll und kann
=======
	 * diese Methode überprüft, ob ein neues Alien spawnen soll und kann
>>>>>>> origin/master
	 */
	private void checkForNewSpawn() {
		synchronized (gameWorld) {

			if ((gameWorld.getTicksSinceLastSpwan() > GameSettings.ALIENRESPAWNRATE)
					&& gameWorld.getAliens().size() < GameSettings.ALIENMAXANZAHL) {
				if (Math.random() <= GameSettings.ALIENIMMUNCHANCE) {
					Alien newAlien = new Alien(true);
					newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
							(float) (Math.random() * GameSettings.HOEHE), GameSettings.ALIENBREITE,
							GameSettings.ALIENHOEHE);
					newAlien.setSpeed(GameSettings.ALIENSPEED);
					gameWorld.getImmunAliens().add(newAlien);
					gameWorld.setTicksSinceLastSpwan(0);
				} else {
					Alien newAlien = new Alien(false);
					newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
							(float) (Math.random() * GameSettings.HOEHE), GameSettings.ALIENBREITE,
							GameSettings.ALIENHOEHE);
					newAlien.setSpeed(GameSettings.ALIENSPEED);
					gameWorld.getAliens().add(newAlien);
					gameWorld.setTicksSinceLastSpwan(0);
				}

<<<<<<< HEAD
			}
=======
		if ((gameWorld.getTicksSinceLastSpwan() > GameSettings.ALIENRESPAWNRATE)
				&& gameWorld.getAliens().size() < GameSettings.ALIENMAXANZAHL) {
			Alien newAlien = new Alien(false);
			newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
					(float) (Math.random() * GameSettings.HÖHE), GameSettings.ALIENBREITE, GameSettings.ALIENHÖHE);
			newAlien.setSpeed(GameSettings.ALIENSPEED);
			gameWorld.getAliens().add(newAlien);
			gameWorld.setTicksSinceLastSpwan(0);
>>>>>>> origin/master
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
<<<<<<< HEAD
	 * Methode prueft, ob das Spielfeldobject ausserhalb des Spielfeldes ist,
	 * und setzt das Object dann ggf. wieder ins Spielfeld.
=======
	 * Methode prüft, ob das Spielfeldobject ausserhalb des Spielfeldes ist, und
	 * setzt das Object dann ggf. wieder ins Spielfeld.
>>>>>>> origin/master
	 *
	 * @param gameObject
	 *            Das Spielfeldobjekt
	 * @param deleteObj
<<<<<<< HEAD
	 *            true, wenn es geloescht werden soll, false wenn es zurueck ins
=======
	 *            true, wenn es gelöscht werden soll, false wenn es zurück ins
>>>>>>> origin/master
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
<<<<<<< HEAD
		if (gameObject.getY() > (GameSettings.HOEHE - gameObject.getHeight())) {
			if (!deleteObj)
				gameObject.setY(GameSettings.HOEHE - gameObject.getHeight());
=======
		if (gameObject.getY() > (GameSettings.HÖHE - gameObject.getHeight())) {
			if (!deleteObj)
				gameObject.setY(GameSettings.HÖHE - gameObject.getHeight());
>>>>>>> origin/master
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
			gameWorld.getProjektileClient().remove(gameObject);
		}
	}

	/**
<<<<<<< HEAD
	 * Funktion prueft, ob zwei Spielfeldobjekte kollidieren
=======
	 * Funktion prüft, ob zwei Spielfeldobjekte kollidieren
>>>>>>> origin/master
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
<<<<<<< HEAD
		// Formel fuer die Kollisionsberechnung
=======
		// Formel für die Kollisionsberechnung
>>>>>>> origin/master
		return position1[0] < position2[0] + width2 && position2[0] < position1[0] + width1
				&& position1[1] < position2[1] + height2 && position2[1] < position1[1] + height1;
	}

	@Override
	public void raiseMenuClick(int menuIndex) {
		if (menuIndex == 0) {
			view.switchScene(view.getSceneNewGame());
		} else if (menuIndex == 1) {
			view.switchScene(view.getSceneConnect());
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
	public void raiseSettingsClick(String key, int value) {
		if (key.equals("sound")) {
			if (value == 0) {
				enableSounds = false;
			} else if (value == 1) {
				enableSounds = true;
			}
		} else if (key.equals("music")) {
			if (value == 0) {
				view.stopMusic();
			} else if (value == 1) {
				view.playMusic(GameSettings.MUSICBACKGROUND);
			}
		}
	}

	@Override
	public void raiseConnectClick(boolean connect, String ip) {
		if (!connect) {
			view.switchScene(view.getSceneMainMenu());
		} else {
			view.startClient(ip, this.view);
			view.switchScene(view.getSceneGame());
			view.activateIwillDestroyYouTank(gameWorld.getIwillDestroyYouTank());
		}
	}

}

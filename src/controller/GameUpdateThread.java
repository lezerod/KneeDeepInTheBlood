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
	private ServerThreadTCPControl stc;
	private ServerThreadTCPWorld stw;

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
//		try {
//			stc = new ServerThreadTCPControl(gameWorld);
//			stw = new ServerThreadTCPWorld(gameWorld);
//			stc.start();
//			stw.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	/**
	 * MainMethode des Threads
	 */
	@Override
	public void run() {

		init();
		try {
			new ServerThreadTCPControl(gameWorld).start();
			new ServerThreadTCPWorld(gameWorld).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
				if(gameWorld.getIwillDestroyYouTank().isConnected()){
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


	}}

	/**
	 * updated das gesamte Model
	 */
	private void updateModel() {
		synchronized (gameWorld) {


		// pruefen, ob die zeit um ist.
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
//						try {
//							new ServerThreadTCPWorld(gameWorld).stop();
//							new ServerThreadTCPControl(gameWorld).stop();

//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}

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
	}}

	/**
	 * diese Methode updated die Aliens auf dem Spielfeld
	 */
	private void updateAliens(ArrayList<Alien>aliens) {
		synchronized (gameWorld) {


		for (int i = 0; i < aliens.size(); i++) {
			Alien aktAlien = aliens.get(i);

			if (Math.random() <= GameSettings.ALIENCHANGETURNINGCHANCE) {
				aktAlien.setWinkel((float) (360 * Math.random()));
			}

			aktAlien.move(false);

			aktAlien.erhoeheLastShot();

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
	}}

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

			// pruefen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);

		}
	}}

	/**
	 * updated die freundlichen Projektile
	 */
	private void updateFriendlyProjektile() {
		synchronized (gameWorld) {


		for (int i = 0; i < gameWorld.getProjektileFriendly().size(); i++) {
			MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);

			aktProjektil.move(false);

			// pruefen, dass die Projektile nicht ausserhalb des Fensters sind:
			korrigierePosition(aktProjektil, true);

		}}
		}
	private void updateiWillProjektile(){
		synchronized (gameWorld) {
			for (int i = 0; i < gameWorld.getProjektileClient().size(); i++) {
				MoveableObject aktProjektil = gameWorld.getProjektileClient().get(i);

				aktProjektil.move(false);

				// pruefen, dass die Projektile nicht ausserhalb des Fensters sind:
				korrigierePosition(aktProjektil, true);

		}}
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

		gameWorld.getHeldenfahrzeug().erhoeheLastShot();

		if (spacePressed && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE) {
			if (enableSounds) {
				view.playSound(GameSettings.SFXSHOTFIRED);
			}
			MoveableObject movObj = new MoveableObject();
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
					gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
					GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHOEHE);
			movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
		}}

		// pruefen, dass das Heldenfahrezeug nicht ausserhalb des Fensters ist
		// und ggf. position korrigieren:
		korrigierePosition(gameWorld.getHeldenfahrzeug(), false);

	}

	private void updateItems(){
		if(gameWorld.getItems().size() < 1){
		Random rand = new Random();
		int randint = rand.nextInt(10000);
		if(randint <= 50){
			int x = rand.nextInt(GameSettings.BREITE);
			int y = rand.nextInt(GameSettings.HOEHE);
			GameObject bfg = new GameObject();
			bfg.setPosition(x, y, 20, 20);
			gameWorld.getItems().add(bfg);
		}}
	}

	/**
	 * diese Methode ueberprueft alle moeglichen Kollisionen und behandelt diese
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
								try {
									new ServerThreadTCPWorld(gameWorld).stop();
									new ServerThreadTCPControl(gameWorld).stop();

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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

				try {
					MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
					Alien aktAlien = gameWorld.getAliens().get(j);
					if (checkForCollision(aktProjektil, aktAlien)) {
						if (enableSounds) {
							view.playSound(GameSettings.SFXALIENHIT);
						}
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

			for (int j = 0; j < gameWorld.getImmunAliens().size(); j++) {

				try {
					MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
					Alien aktAlien = gameWorld.getImmunAliens().get(j);
					if (checkForCollision(aktProjektil, aktAlien) && gameWorld.getHeldenfahrzeug().isHasSpezialWeapon()) {
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
					// TODO: handle exception
				}

			}
		}
		for (int i = 0; i < gameWorld.getProjektileClient().size(); i++){
			if(checkForCollision(gameWorld.getProjektileClient().get(i), gameWorld.getHeldenfahrzeug())){
				gameWorld.getHeldenfahrzeug().setSpeed(1);
				gameWorld.getProjektileClient().remove(i);

				new Thread(){
					public void run(){
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gameWorld.getHeldenfahrzeug().setSpeed(GameSettings.HELDENFAHRZEUGSPEED);
					}
				}.start();

			}
			for(int j = 0; j < gameWorld.getAliens().size(); j++){
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

			}}
					catch(Exception e){
						e.printStackTrace();
					}

		}

	}
		if(gameWorld.getItems().size() != 0){
		for(int j = 0; j < gameWorld.getItems().size(); j++){
			if(checkForCollision(gameWorld.getHeldenfahrzeug(),gameWorld.getItems().get(j))){
				gameWorld.getHeldenfahrzeug().setHasSpezialWeapon(true);
				gameWorld.getItems().remove(j);
				System.out.println("BFG!");

				new Thread(){
					public void run(){
						try{
							Thread.sleep(10000);
						}
						catch (Exception e){
							e.printStackTrace();
						}
						gameWorld.getHeldenfahrzeug().setHasSpezialWeapon(false);
					}
				}.start();
			}
		}}
}}

	/**
	 * diese Methode ueberprueft, ob ein neues Alien spawnen soll und kann
	 */
	private void checkForNewSpawn() {
		synchronized (gameWorld) {


		if ((gameWorld.getTicksSinceLastSpwan() > GameSettings.ALIENRESPAWNRATE)
				&& gameWorld.getAliens().size() < GameSettings.ALIENMAXANZAHL) {
			if(Math.random() <= GameSettings.ALIENIMMUNCHANCE) {
				Alien newAlien = new Alien(true);
				newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
						(float) (Math.random() * GameSettings.HOEHE), GameSettings.ALIENBREITE, GameSettings.ALIENHOEHE);
				newAlien.setSpeed(GameSettings.ALIENSPEED);
				gameWorld.getImmunAliens().add(newAlien);
				gameWorld.setTicksSinceLastSpwan(0);
			} else {
				Alien newAlien = new Alien(false);
				newAlien.setPosition((float) (Math.random() * GameSettings.BREITE),
						(float) (Math.random() * GameSettings.HOEHE), GameSettings.ALIENBREITE, GameSettings.ALIENHOEHE);
				newAlien.setSpeed(GameSettings.ALIENSPEED);
				gameWorld.getAliens().add(newAlien);
				gameWorld.setTicksSinceLastSpwan(0);
			}

		}
	}}

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
			alert.setHeaderText("M�chten sie das Spiel wirklich beenden?");
			alert.setContentText("Der Spielstand geht verloren.");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				try {
					new ServerThreadTCPWorld(gameWorld).stop();
					new ServerThreadTCPControl(gameWorld).stop();

				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
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
	 * Methode prueft, ob das Spielfeldobject ausserhalb des Spielfeldes ist, und
	 * setzt das Object dann ggf. wieder ins Spielfeld.
	 *
	 * @param gameObject
	 *            Das Spielfeldobjekt
	 * @param deleteObj
	 *            true, wenn es geloescht werden soll, false wenn es zurueck ins
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
		if (gameObject.getY() > (GameSettings.HOEHE - gameObject.getHeight())) {
			if (!deleteObj)
				gameObject.setY(GameSettings.HOEHE - gameObject.getHeight());
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
	 * Funktion prueft, ob zwei Spielfeldobjekte kollidieren
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
		// Formel fuer die Kollisionsberechnung
		return position1[0] < position2[0] + width2 && position2[0] < position1[0] + width1
				&& position1[1] < position2[1] + height2 && position2[1] < position1[1] + height1;
	}

	@Override
	public void raiseMenuClick(int menuIndex) {
		if (menuIndex == 0) {
			view.switchScene(view.getSceneNewGame());
		} else if (menuIndex==1) {
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
		}
		else{
			view.startClient(ip, this.view);
			view.switchScene(view.getSceneGame());
			view.activateIwillDestroyYouTank(gameWorld.getIwillDestroyYouTank());
		}
	}

}

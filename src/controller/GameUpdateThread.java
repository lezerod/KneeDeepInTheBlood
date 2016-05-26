package controller;



import controller.GameSettings;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import model.Alien;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.MoveableObject;
import view.EventList;
import view.MainWindow;
import javafx.scene.input.KeyEvent;

public class GameUpdateThread extends Thread implements EventList {

	private MainWindow view;
	private GameWorld gameWorld;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean topPressed = false;
	private boolean bottomPressed = false;
	private boolean spacePressed = false;
	
	public GameUpdateThread(GameWorld gameWorld, MainWindow view) {
		this.gameWorld = gameWorld;
		this.view = view;
	}

	@Override
	public void run() {

		createTestModel();
		
		view.updateView(gameWorld);
		
		view.registerEventListener(this);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		while (true) {
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		updateModel();
		
		view.updateView(gameWorld);
		}
	}
	
	private void updateModel() {
		
		//update Aliens:
		for (int i = 0;i<gameWorld.getAliens().size();i++) {
			Alien aktAlien = gameWorld.getAliens().get(i);
			
			if (Math.random() <= GameSettings.ALIENCHANGETURNINGCHANCE) {
				aktAlien.setWinkel((float)(360*Math.random()));
			}
		
			aktAlien.setX((float)(getNewPosX(aktAlien.getWinkel(), aktAlien.getSpeed(), aktAlien.getX(), aktAlien.getY(),false)));
			aktAlien.setY((float)(getNewPosY(aktAlien.getWinkel(), aktAlien.getSpeed(), aktAlien.getX(), aktAlien.getY(),false)));
			aktAlien.setLastShot(aktAlien.getLastShot()+1);
			if (aktAlien.getLastShot()>=GameSettings.ALIENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(aktAlien.getX(), aktAlien.getY(), GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
				movObj.setSpeed(GameSettings.PROJEKTILSPEED);
				movObj.setWinkel((float)(360*Math.random()));
				gameWorld.getProjektile().add(movObj);
				aktAlien.setLastShot(0);
			}
			
			//prüfen, dass die Aliens nicht ausserhalb des Fensters sind:
			if (aktAlien.getY() < 0) {
				aktAlien.setY(0);
			}
			if (aktAlien.getY() > (GameSettings.HÖHE-aktAlien.getHeight())) {
				aktAlien.setY(GameSettings.HÖHE-aktAlien.getHeight());
			}
			if (aktAlien.getX() < 0) {
				aktAlien.setX(0);
			}
			if (aktAlien.getX() > (GameSettings.BREITE-aktAlien.getWidth())) {
				aktAlien.setX(GameSettings.BREITE-aktAlien.getWidth());
			}
		}
		
		
		//update projektile
		for (int i = 0;i<gameWorld.getProjektile().size();i++) {
			MoveableObject aktProjektil = gameWorld.getProjektile().get(i);
		
			aktProjektil.setX((float)(getNewPosX(aktProjektil.getWinkel(), aktProjektil.getSpeed(), aktProjektil.getX(), aktProjektil.getY(),false)));
			aktProjektil.setY((float)(getNewPosY(aktProjektil.getWinkel(), aktProjektil.getSpeed(), aktProjektil.getX(), aktProjektil.getY(),false)));
			
			//prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
			if (aktProjektil.getY() < 0) {
				gameWorld.getProjektile().remove(i);
				//i--;
			}
			if (aktProjektil.getY() > (GameSettings.HÖHE-aktProjektil.getHeight())) {
				gameWorld.getProjektile().remove(i);
				//i--;
			}
			if (aktProjektil.getX() < 0) {
				gameWorld.getProjektile().remove(i);
				//i--;
			}
			if (aktProjektil.getX() > (GameSettings.BREITE-aktProjektil.getWidth())) {
				gameWorld.getProjektile().remove(i);
				//i--;
			}
		}
		//jetzt noch die vom heldenFahrzeug
		for (int i = 0;i<gameWorld.getProjektileFriendly().size();i++) {
			MoveableObject aktProjektil = gameWorld.getProjektileFriendly().get(i);
		
			aktProjektil.setX((float)(getNewPosX(aktProjektil.getWinkel(), aktProjektil.getSpeed(), aktProjektil.getX(), aktProjektil.getY(),false)));
			aktProjektil.setY((float)(getNewPosY(aktProjektil.getWinkel(), aktProjektil.getSpeed(), aktProjektil.getX(), aktProjektil.getY(),false)));
			
			//prüfen, dass die Projektile nicht ausserhalb des Fensters sind:
			if (aktProjektil.getY() < 0) {
				gameWorld.getProjektileFriendly().remove(i);
				i--;
			}
			if (aktProjektil.getY() > (GameSettings.HÖHE-aktProjektil.getHeight())) {
				gameWorld.getProjektileFriendly().remove(i);
				i--;
			}
			if (aktProjektil.getX() < 0) {
				gameWorld.getProjektileFriendly().remove(i);
				i--;
			}
			if (aktProjektil.getX() > (GameSettings.BREITE-aktProjektil.getWidth())) {
				gameWorld.getProjektileFriendly().remove(i);
				i--;
			}
		}
		
		
		//update heldenFahrzeug
		if (topPressed) {
			gameWorld.getHeldenfahrzeug().setX((float)(getNewPosX(gameWorld.getHeldenfahrzeug().getWinkel(), gameWorld.getHeldenfahrzeug().getSpeed(), gameWorld.getHeldenfahrzeug().getX(), gameWorld.getHeldenfahrzeug().getY(),false)));
			gameWorld.getHeldenfahrzeug().setY((float)(getNewPosY(gameWorld.getHeldenfahrzeug().getWinkel(), gameWorld.getHeldenfahrzeug().getSpeed(), gameWorld.getHeldenfahrzeug().getX(), gameWorld.getHeldenfahrzeug().getY(),false)));
		}
		if (bottomPressed) {
			gameWorld.getHeldenfahrzeug().setX((float)(getNewPosX(gameWorld.getHeldenfahrzeug().getWinkel(), gameWorld.getHeldenfahrzeug().getSpeed(), gameWorld.getHeldenfahrzeug().getX(), gameWorld.getHeldenfahrzeug().getY(),true)));
			gameWorld.getHeldenfahrzeug().setY((float)(getNewPosY(gameWorld.getHeldenfahrzeug().getWinkel(), gameWorld.getHeldenfahrzeug().getSpeed(), gameWorld.getHeldenfahrzeug().getX(), gameWorld.getHeldenfahrzeug().getY(), true)));
		}
		if (leftPressed) gameWorld.getHeldenfahrzeug().setWinkel(gameWorld.getHeldenfahrzeug().getWinkel()-GameSettings.WINKELCHANGESPEED);
		if (rightPressed) gameWorld.getHeldenfahrzeug().setWinkel(gameWorld.getHeldenfahrzeug().getWinkel()+GameSettings.WINKELCHANGESPEED);
		gameWorld.getHeldenfahrzeug().setLastShot(gameWorld.getHeldenfahrzeug().getLastShot()+1);
		if (spacePressed && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE)  {
			MoveableObject movObj = new MoveableObject();
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2), gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2), GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
			movObj.setSpeed(GameSettings.PROJEKTILSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
			}
		
		//prüfen, dass die Aliens nicht ausserhalb des Fensters sind:
		if (gameWorld.getHeldenfahrzeug().getY() < 0) {
			gameWorld.getHeldenfahrzeug().setY(0);
		}
		if (gameWorld.getHeldenfahrzeug().getY() > (GameSettings.HÖHE-gameWorld.getHeldenfahrzeug().getHeight())) {
			gameWorld.getHeldenfahrzeug().setY(GameSettings.HÖHE-gameWorld.getHeldenfahrzeug().getHeight());
		}
		if (gameWorld.getHeldenfahrzeug().getX() < 0) {
			gameWorld.getHeldenfahrzeug().setX(0);
		}
		if (gameWorld.getHeldenfahrzeug().getX() > (GameSettings.BREITE-gameWorld.getHeldenfahrzeug().getWidth())) {
			gameWorld.getHeldenfahrzeug().setX(GameSettings.BREITE-gameWorld.getHeldenfahrzeug().getWidth());
		}
		
		
	}
	
	private void createTestModel() {

		// Create some aliens:
		Alien alien;
		for (int i = 0; i < 10; i++) {
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

	@Override
	public void raiseKeyDownEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)bottomPressed=true;
		if (e.getCode() == KeyCode.UP)topPressed=true;
		if (e.getCode() == KeyCode.LEFT)leftPressed=true;
		if (e.getCode() == KeyCode.RIGHT)rightPressed=true;
		if (e.getCode() == KeyCode.SPACE)spacePressed=true;
	}
	
	@Override
	public void raiseKeyUpEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.DOWN)bottomPressed=false;
		if (e.getCode() == KeyCode.UP)topPressed=false;
		if (e.getCode() == KeyCode.LEFT)leftPressed=false;
		if (e.getCode() == KeyCode.RIGHT)rightPressed=false;
		if (e.getCode() == KeyCode.SPACE)spacePressed=false;
		
	}
	
	private static double getNewPosX(float winkel, int speed, double x, double y, boolean back) {
				double deltaX;
				deltaX = Math.sin(winkel * (Math.PI/180)) * speed;
				if (back)return (x - deltaX);
				else return (x + deltaX);
		 	}
	
	private static double getNewPosY(float winkel, int speed, double x, double y, boolean back) {
		double deltaY;
		deltaY = Math.cos(winkel * (Math.PI/180)) * speed;
		if (back)return (y + deltaY);
		else return (y - deltaY);
 	}
	

}

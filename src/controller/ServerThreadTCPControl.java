package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.InputStream;

import javafx.scene.input.KeyCode;
import model.GameWorld;
import model.MoveableObject;

/**
 * Diese Klasse empfängt die Tastendrücke von dem Client und wertet diese Weiter
 * aus.
 *
 * @author til
 *
 */
public class ServerThreadTCPControl extends Thread {
	private ServerSocket serverSocket;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;
	private GameWorld gameWorld = null;

	public ServerThreadTCPControl(GameWorld gameworld) throws IOException {
		serverSocket = new ServerSocket(8888);
		this.gameWorld = gameworld;
	}

	public void run() {
		while (true) {
			try {
				System.out.println(up);
				Socket clientSocket = serverSocket.accept();
				/**
				 * Es wird ein InputstreamReader gestartet um das Gesendete von
				 * dem Client zu empfangen.
				 */
				byte control;
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				control = dis.readByte();
				System.out.println(control);
				/**
				 * Handelt es sich um ein null Object wird es nicht weiter
				 * bearbeitet
				 */

				if ((control & 1) == 1) {
					System.out.println("Up was pressed!");
					up = true;
				}
				else{
					up = false;
				}
				if ((control & 16) == 16) {
					System.out.println("Space was pressed!");
					space = true;
				}
				else{
					space = false;
				}
				if ((control & 2) == 2) {
					System.out.println("Down was pressed!");
					down = true;
				}
				else{
					down = false;
				}
				if ((control & 4) == 4) {
					System.out.println("Left was pressed!");
					left = true;
				}
				else{
					left = false;
				}
				if ((control & 8) == 8) {
					System.out.println("Right was pressed!");
					right = true;
				}
				else{
					right = false;
				}

				gameWorld.getIwillDestroyYouTank().setUp(up);
				gameWorld.getIwillDestroyYouTank().setDown(down);
				gameWorld.getIwillDestroyYouTank().setLeft(left);
				gameWorld.getIwillDestroyYouTank().setRight(right);
				gameWorld.getIwillDestroyYouTank().setSpace(space);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	private void updateIwillDestoryYouTank(boolean up, boolean down, boolean left, boolean right, boolean space,
//			GameWorld gameWorld) {
//		synchronized (gameWorld) {
//
//			if (up) {
//				gameWorld.getIwillDestroyYouTank().move(false);
//			}
//			if (down) {
//				gameWorld.getIwillDestroyYouTank().move(true);
//			}
//			if (left)
//				gameWorld.getIwillDestroyYouTank().setWinkel(
//						gameWorld.getIwillDestroyYouTank().getWinkel() - GameSettings.HELDENWINKELCHANGESPEED);
//			if (right)
//				gameWorld.getIwillDestroyYouTank().setWinkel(
//						gameWorld.getIwillDestroyYouTank().getWinkel() + GameSettings.HELDENWINKELCHANGESPEED);
//
//			gameWorld.getIwillDestroyYouTank().erhöheLastShot();
//
//			if (space && gameWorld.getIwillDestroyYouTank().getLastShot() >= GameSettings.HELDENFEUERRATE) {
//				// if (enableSounds) {
//				// view.playSound(GameSettings.SFXSHOTFIRED);
//				// }
//				MoveableObject movObj = new MoveableObject();
//				movObj.setPosition(
//						gameWorld.getIwillDestroyYouTank().getX() + (gameWorld.getIwillDestroyYouTank().getWidth() / 2),
//						gameWorld.getIwillDestroyYouTank().getY()
//								+ (gameWorld.getIwillDestroyYouTank().getHeight() / 2),
//						GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
//				movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
//				movObj.setWinkel(gameWorld.getIwillDestroyYouTank().getWinkel());
//				gameWorld.getProjektileClient().add(movObj);
//				gameWorld.getIwillDestroyYouTank().setLastShot(0);
//			}
//		}
//	}
}

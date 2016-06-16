package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.GameWorld;
import model.MoveableObject;

/**
 * Diese Klasse empfängt die Tastendrücke von dem Client und wertet diese weiter aus.
 *
 * @author til
 *
 */
public class ServerThreadTCPControl extends Thread {
	private ServerSocket serverSocket;
	private GameWorld gameWorld = null;

	public ServerThreadTCPControl(GameWorld gameworld) throws IOException {
		serverSocket = new ServerSocket(8888);
		this.gameWorld = gameworld;
	}

	public void run() {
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				/**
				 * Es wird ein InputstreamReader gestartet um das Gesendete von
				 * dem Client zu empfangen. Danach wird das byte an die handleInput methode
				 * weitergereicht.
				 */
				byte control;
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				control = dis.readByte();
				handleInput(control);
				dis.close();
				clientSocket.close();
				Thread.sleep(10);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Diese Methode wertet das byte aus und setzt bei einem erfolg den dafür vorgesehenen boolean auf true.
 	* Zur überpfüung wird das bitweise und verwendet.
 	* @param input
	*/
	private void handleInput(byte input){
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		boolean space = false;

		if ((input & 1) == 1) {
			System.out.println("Up was pressed!");
			up = true;
		}
		if ((input & 16) == 16) {
			System.out.println("Space was pressed!");
			space = true;
		}
		if ((input & 2) == 2) {
			System.out.println("Down was pressed!");
			down = true;
		}
		if ((input & 4) == 4) {
			System.out.println("Left was pressed!");
			left = true;
		}
		if ((input & 8) == 8) {
			System.out.println("Right was pressed!");
			right = true;
		}
		updateIwillDestoryYouTank(up, down, left, right, space, gameWorld);

	}
	/**
	 *@param up 
	 *@param down
	 *@param left
	 *@param right
	 *@param space
	 *@param GameWorld
	 */
	private void updateIwillDestoryYouTank(boolean up, boolean down, boolean left, boolean right, boolean space,
			GameWorld gameWorld) {
		synchronized (gameWorld) {

			if (up) {
				gameWorld.getIwillDestroyYouTank().move(false);
			}
			if (down) {
				gameWorld.getIwillDestroyYouTank().move(true);
			}
			if (left)
				gameWorld.getIwillDestroyYouTank().setWinkel(
						gameWorld.getIwillDestroyYouTank().getWinkel() - GameSettings.HELDENWINKELCHANGESPEED);
			if (right)
				gameWorld.getIwillDestroyYouTank().setWinkel(
						gameWorld.getIwillDestroyYouTank().getWinkel() + GameSettings.HELDENWINKELCHANGESPEED);

			gameWorld.getIwillDestroyYouTank().erhöheLastShot();

			if (space && gameWorld.getIwillDestroyYouTank().getLastShot() >= GameSettings.HELDENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(
						gameWorld.getIwillDestroyYouTank().getX() + (gameWorld.getIwillDestroyYouTank().getWidth() / 2),
						gameWorld.getIwillDestroyYouTank().getY()
								+ (gameWorld.getIwillDestroyYouTank().getHeight() / 2),
						GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
				movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
				movObj.setWinkel(gameWorld.getIwillDestroyYouTank().getWinkel());
				gameWorld.getProjektileClient().add(movObj);
				gameWorld.getIwillDestroyYouTank().setLastShot(0);
			}
		}
	}
}

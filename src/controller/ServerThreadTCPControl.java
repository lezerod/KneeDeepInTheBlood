package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.GameWorld;
import model.MoveableObject;

/**
 * Diese Klasse empfaengt die Tastendruecke von dem Client und wertet diese
 * Weiter aus.
 *
 * @author til
 *
 */
public class ServerThreadTCPControl extends Thread {
	private volatile ServerSocket serverSocket;
	private GameWorld gameWorld = null;

	public ServerThreadTCPControl(GameWorld gameworld) throws IOException {
		this.gameWorld = gameworld;
		serverSocket = new ServerSocket(8889);

	}

	public void run() {
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();

				/**
				 * Es wird ein InputstreamReader gestartet um das Gesendete von
				 * dem Client zu empfangen. Danach wird das byte an die
				 * handleInput methode weitergereicht.
				 */
				byte control;
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				control = dis.readByte();
				System.out.println(control);

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
	 * Diese Methode wertet das byte aus und setzt bei einem erfolg den daf�r
	 * vorgesehenen boolean auf true. Zur �berpf�ung wird das bitweise "und"
	 * verwendet.
	 *
	 * @param input
	 */
	private void handleInput(byte input) {
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		boolean space = false;

		if ((input & 1) == 1) {
			System.out.println("Up was pressed!");
			up = true;
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
		if ((input & 16) == 16) {
			System.out.println("Space was pressed!");
			space = true;
		}

		updateIwillDestoryYouTank(up, down, left, right, space, gameWorld);

	}

	/**
	 * Diese Methode gibt die ausgewerteten Angaben aus dem byte an die
	 * Gameworld weiter.
	 *
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @param space
	 * @param gameWorld
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

			gameWorld.getIwillDestroyYouTank().erhoeheLastShot();

			if (space && gameWorld.getIwillDestroyYouTank().getLastShot() >= GameSettings.HELDENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(
						gameWorld.getIwillDestroyYouTank().getX() + (gameWorld.getIwillDestroyYouTank().getWidth() / 2),
						gameWorld.getIwillDestroyYouTank().getY()
								+ (gameWorld.getIwillDestroyYouTank().getHeight() / 2),
						GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHOEHE);
				movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
				movObj.setWinkel(gameWorld.getIwillDestroyYouTank().getWinkel());
				gameWorld.getProjektileClient().add(movObj);
				gameWorld.getIwillDestroyYouTank().setLastShot(0);
			}
		}
	}
}

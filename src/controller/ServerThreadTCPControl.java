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
	private boolean up = true;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;
	private GameWorld gameworld = null;

	public ServerThreadTCPControl(GameWorld gameworld) throws IOException {
		serverSocket = new ServerSocket(8888);
		this.gameworld = gameworld;
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

				if((control & 1) == 1){
					System.out.println("Up was pressed!");
					up = true;
				}
				if( (control & 16) == 16){
					System.out.println("Space was pressed!");
					space = true;}
				if((control & 2) == 2){
					System.out.println("Down was pressed!");
					down = true;
				}
				if((control & 4) == 4){
					System.out.println("Left was pressed!");
					left = true;
				}
				if((control & 8) == 8){
					System.out.println("Right was pressed!");
					right = true;
				}
				moveClient(up, down, left, right, space, gameworld);

			} catch (Exception e) {
				e.printStackTrace();
		}
	}}

	public void moveClient(boolean up, boolean down, boolean left, boolean right, boolean space, GameWorld gameWorld) {
		System.out.println("Habs geschafft");

		System.out.println(this.up);
		if (this.up) {
			System.out.println("up is true");
			gameWorld.getHeldenfahrzeug().move(false);}
			if (this.down) {
				gameWorld.getHeldenfahrzeug().move(true);
			}

			if (this.left)
				gameWorld.getHeldenfahrzeug()
						.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel() - GameSettings.HELDENWINKELCHANGESPEED);
			if (this.right)
				gameWorld.getHeldenfahrzeug()
						.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel() + GameSettings.HELDENWINKELCHANGESPEED);

			gameWorld.getHeldenfahrzeug().erhöheLastShot();

			if (this.space && gameWorld.getHeldenfahrzeug().getLastShot() >= GameSettings.HELDENFEUERRATE) {
				MoveableObject movObj = new MoveableObject();
				movObj.setPosition(
						gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
						gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
						GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
				movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
				movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
				gameWorld.getProjektileFriendly().add(movObj);
				gameWorld.getHeldenfahrzeug().setLastShot(0);
			}

		 this.up = false;
		 this.down = false;
		 this.left = false;
		 this.right = false;
		 this.space = false;
	}
}

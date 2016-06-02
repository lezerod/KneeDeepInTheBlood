package controller;

import java.io.BufferedReader;
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
 *Diese Klasse empfängt die Tastendrücke von dem Client und wertet diese Weiter aus.
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
				 * Es wird ein InputstreamReader gestartet um das Gesendete von dem Client zu empfangen.
				 */
				String control = new String();
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				control = in.readLine();

				/**
				 * Handelt es sich um ein null Object wird es nicht weiter bearbeitet
				 */
				if (control != null) {
					searchStuff(control);
					}
				in.close();
				clientSocket.close();
				Thread.sleep(10);
			}
			catch(NullPointerException e){
				System.out.println("Kein Objekt");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	public void searchStuff(String in){
			for(int i = 0; i != in.length(); i++){
			char c = in.charAt(i);
			compareStuff(i, c);}
			moveClient(this.up, this.down, this.left, this.right, this.space, this.gameworld);}

	public void compareStuff(int i, char c){
		System.out.println(c);
				switch (i) {
				case 0:
					System.out.println("erster Case");
					this.up = check(c);
					System.out.println(this.up);
					break;
				case 1:
					System.out.println("zweiter case");
					this.down = check(c);
					break;
				case 2:
					System.out.println("dritter case");
					this.left = check(c);
					break;
				case 3:
					System.out.println("vierter case");
					this.right = check(c);
					break;
				case 4:
					System.out.println("letzter case");
					this.space = check(c);
					break;
			}}

	public boolean check(char t){
		if(t == '1'){
		return true;}
		else{
			System.out.println("leider nicht!");
			return false;
		}
	}
	public void moveClient(boolean up, boolean down, boolean left, boolean right, boolean space, GameWorld gameWorld){
		System.out.println("Habs geschafft");

		System.out.println(this.up);
		if(this.up == true){
			System.out.println("up is true");
			gameWorld.getHeldenfahrzeug().move(false);
		if(this.down == true){
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
			movObj.setPosition(gameWorld.getHeldenfahrzeug().getX() + (gameWorld.getHeldenfahrzeug().getWidth() / 2),
					gameWorld.getHeldenfahrzeug().getY() + (gameWorld.getHeldenfahrzeug().getHeight() / 2),
					GameSettings.PROJEKTILBREITE, GameSettings.PROJEKTILHÖHE);
			movObj.setSpeed(GameSettings.PROJEKTILFRIENDLYSPEED);
			movObj.setWinkel(gameWorld.getHeldenfahrzeug().getWinkel());
			gameWorld.getProjektileFriendly().add(movObj);
			gameWorld.getHeldenfahrzeug().setLastShot(0);
	}

//			this.up = false;
//			this.down = false;
//			this.left = false;
//			this.right = false;
//			this.space = false;
		}
	}
}

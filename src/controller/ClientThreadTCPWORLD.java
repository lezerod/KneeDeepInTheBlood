package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import model.GameWorld;
import view.MainWindow;

/**
 * Diese Klasse empf�ngt die gesendete GameWorld von dem Server und updatet mit
 * ihr die View des Clients.
 *
 * @author til
 *
 */
public class ClientThreadTCPWORLD extends Thread {
	MainWindow window = null;
	GameWorld world = null;
	String ip = null;

	/**
	 * Innerhalb des Konstruktors wird eine Neue Gameworld erstellt und die View
	 * des Clients �bergeben.
	 *
	 * @param gameworld
	 * @param view
	 */
	public ClientThreadTCPWORLD(GameWorld gameworld, MainWindow view, String ip) {
		this.world = gameworld;
		this.window = view;
		this.ip = ip;
	}

	public void run() {
		while (true) {
			try {
				// Verbindung zu Port 13000 auf localhost aufbauen:
				Socket socket = new Socket(this.ip, 13003);
				/**
				 * Der Thread empf�ngt die Gesendete GameWorld von dem Server
				 * und updatet mit ihr die View des Clients.
				 */
				if(socket.isConnected()){
				InputStream i = socket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(i);
				world = (GameWorld) ois.readObject();
				if(world != null){
				window.updateView(world);
				world = null;}
				ois.close();
				i.close();
				socket.close();
			}}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}
	}
}

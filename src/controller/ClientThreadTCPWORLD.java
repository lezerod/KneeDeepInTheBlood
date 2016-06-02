package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import model.GameWorld;
import view.ClientView;
import view.ClientViewNew;
import view.MainWindow;

/**
 * Diese Klasse empfängt die gesendete GameWorld von dem Server und updatet mit
 * ihr die View des Clients.
 *
 * @author til
 *
 */
public class ClientThreadTCPWORLD extends Thread {
	ClientViewNew window = new ClientViewNew();
	GameWorld world = null;

	/**
	 * Innerhalb des Konstruktors wird eine Neue Gameworld erstellt und die View
	 * des Clients übergeben.
	 *
	 * @param gameworld
	 * @param view
	 */
	public ClientThreadTCPWORLD(GameWorld gameworld, ClientViewNew view) {
		this.world = gameworld;
		this.window = view;

	}

	public void run() {
		while (true) {
			try {
				// Verbindung zu Port 13000 auf localhost aufbauen:
				Socket socket = new Socket("localhost", 13001);
				/**
				 * Der Thread empfängt die Gesendete GameWorld von dem Server
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

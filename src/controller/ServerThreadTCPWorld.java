package controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameWorld;

/**
 *Diese Klasse sendet die GameWorld von dem Server an den Client.
 * @author til
 *
 */
public class ServerThreadTCPWorld extends Thread {
	private ServerSocket serverSocket;
	private GameWorld world;
/**
 * Der Konstruktor benötigt die aktuelle Gameworld als Parametet aus dem GameUpdateThread um immer die aktuellste GameWorld zu versenden.
 * @param world
 * @throws IOException
 */
	public ServerThreadTCPWorld(GameWorld world) throws IOException {
		// Warte auf Anfragen auf Port 13000:
		serverSocket = new ServerSocket(13002);
		this.world = world;
	}

	public void run() {

		while (true) {
			try {

				Socket clientSocket = serverSocket.accept();
				OutputStream os = clientSocket.getOutputStream();
				ObjectOutputStream outOb = new ObjectOutputStream(os);
				synchronized (world) {
					outOb.writeObject(world);

				}
				outOb.flush();
				outOb.close();
				os.close();
				clientSocket.close();
				Thread.sleep(10);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameWorld;

/**
 * Diese Klasse sendet die GameWorld von dem Server an den Client.
 *
 * @author til
 *
 */
public class ServerThreadTCPWorld extends Thread {
	private volatile ServerSocket serverSocket;
	private GameWorld world;
	private volatile boolean flag = true;

	/**
	 * Der Konstruktor benoetigt die aktuelle Gameworld als Parametet aus dem
	 * GameUpdateThread um immer die aktuellste GameWorld zu versenden.
	 *
	 * @param world
	 * @throws IOException
	 */
	public ServerThreadTCPWorld(GameWorld world) throws IOException {
		// Warte auf Anfragen auf Port 13002:
		this.world = world;
		serverSocket = new ServerSocket(13003);

	}

	public void run() {

		while (flag) {
			try {
				Socket clientSocket = serverSocket.accept();
				world.getIwillDestroyYouTank().setConnected(true);
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
	public void startRunning(){
		flag = true;
	}
	public void stopRunning(){
		flag = true;
	}
}

package controller;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.EventList;
import view.MainWindow;

/**
 * In dieser Klasse wird die Steuerung von dem Client an den Server geschickt.
 * Die eingaben werden als kodierter String an den Server geschickt. Die
 * Kodierung lautet wie folgt: 1. UP 2. DOWN 3. LEFT 4. RIGHT 5. SPACE
 *
 * @author til
 *
 */
public class ClientThreadTCPControl extends Thread implements EventList {
	private MainWindow view;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;
	private String ip = null;

	/**
	 * Konstruktor welcher die View als Parameter benoetigt um auf den
	 * Eventlistener zuzugreifen.
	 *
	 * @param view
	 */
	public ClientThreadTCPControl(MainWindow view, String ip) {
		this.view = view;
		this.ip = ip;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Thread welcher in einer Dauerschleife laeuft.
	 */
	public void run() {
		/**
		 * Initialiesieren des Eventlisteners
		 */
		init();
		while (true) {
			try {
				/**
				 * Es werden die booelean abgefragt ob eine Taste gedrueckt wurde.
				 * Bei einer gedrueckten Taste wird dies zu dem byte addiert.
				 */
				byte send = 0;
				if (up) {
					System.out.println("oben");

					send += 1;
				}
				if (down) {
					send += 2;
				}
				if (left) {
					send += 4;
				}
				if (right) {
					send += 8;
				}
				if (space) {
					send += 16;
				}

				if (send != 0) {
					Socket socket = new Socket(this.ip, 8889);
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.write(send);
					out.newLine();
					out.flush();
					out.close();
					socket.close();
				}

				Thread.sleep(10);

			} catch (Exception e) {

			}
		}

	}

	/**
	 * Diese Methode registriert die Tastendruecke und setzt den zugewiesenen
	 * boolean auf true.
	 *
	 * @param e
	 */
	@Override
	public void raiseKeyDownEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.UP)
			up = true;
		if (e.getCode() == KeyCode.DOWN)
			down = true;
		if (e.getCode() == KeyCode.LEFT)
			left = true;
		if (e.getCode() == KeyCode.RIGHT)
			right = true;
		if (e.getCode() == KeyCode.SPACE)
			space = true;
	}

	/**
	 * Diese Methode registriert wenn eine Taste nicht mehr gedrueckt wird und
	 * setzt den zugewiesenen booean auf false.
	 *
	 * @param e
	 */
	@Override
	public void raiseKeyUpEvent(KeyEvent e) {
		if (e.getCode() == KeyCode.UP)
			up = false;
		if (e.getCode() == KeyCode.DOWN)
			down = false;
		if (e.getCode() == KeyCode.LEFT)
			left = false;
		if (e.getCode() == KeyCode.RIGHT)
			right = false;
		if (e.getCode() == KeyCode.SPACE)
			space = false;

	}

	/**
	 * Diese Methode Meldet den EventListener an.
	 */
	private void init() {
		view.registerEventListener(this);

	}

	@Override
	public void raiseMenuClick(int menuIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void raiseNewGameClick(int menuIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void raiseSettingsClick(String key, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void raiseConnectClick(boolean connect, String Ip) {
		// TODO Auto-generated method stub

	}

}

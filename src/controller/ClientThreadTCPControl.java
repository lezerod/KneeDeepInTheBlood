package controller;

import java.io.BufferedWriter;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.ClientView;
import view.ClientViewNew;
import view.EventList;

/**
 * In dieser Klasse wird die Steuerung von dem Client an den Server geschickt.
 * Die eingaben werden als kodierter String an den Server geschickt. Die
 * Kodierung lautet wie folgt:
 * 1. UP
 * 2. DOWN
 * 3. LEFT
 * 4. RIGHT
 * 5. SPACE
 *
 * @author til
 *
 */
public class ClientThreadTCPControl extends Thread implements EventList {
	private ClientViewNew view;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;

	/**
	 * Konstruktor welcher die View als Parameter benötigt um auf den
	 * Eventlistener zuzugreifen.
	 *
	 * @param view
	 */
	public ClientThreadTCPControl(ClientViewNew view) {
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Thread welcher in einer Dauerschleife läuft.
	 */
	public void run() {
		/**
		 * Initialiesieren des Eventlisteners
		 */
		init();
		while (true) {
			try {
				/**
				 * Es werden die booelean abgefragt ob eine Taste gedrükt wurde.
				 */
				String ausgabe = "";
				if (up == true) {
					/**
					 * Bei einem Tastendruck wird die Methode setOne ausgeführt
					 * und der String wird mit einer 1 an der ersten Stelle
					 * befüllt.
					 */
					ausgabe = setOne(ausgabe);
				} else {
					/**
					 * Bei keinem Tastendruck wird die Methode setZero
					 * ausgeführt und der String wird mit einer an der ersten
					 * Stelle befüllt.
					 */
					ausgabe = setZero(ausgabe);
				}
				if (down == true) {
					ausgabe = setOne(ausgabe);
				} else {
					ausgabe = setZero(ausgabe);
				}
				if (left == true) {
					ausgabe = setOne(ausgabe);
				} else {
					ausgabe = setZero(ausgabe);
				}
				if (right == true) {
					ausgabe = setOne(ausgabe);
				} else {
					ausgabe = setZero(ausgabe);
				}
				if (space == true) {
					ausgabe = setOne(ausgabe);
				} else {
					ausgabe = setZero(ausgabe);
				}
				/**
				 * Nun wird geprüft ob der String ausgabe nur aus Nullen
				 * besteht. Ist dies nicht der Fall Wird ein der String per
				 * BufferedWriter an den Server geschickt zu weitern
				 * bearbeitung.
				 */
				if (!ausgabe.equals("00000")) {
					Socket socket = new Socket("localhost", 8888);
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.write(ausgabe);
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
	 * Diese Methode setzt eine 1 an die nächste Stelle eines String.
	 *
	 * @param in
	 * @return
	 */
	public String setOne(String in) {
		in = in + "1";
		return in;

	}

	/**
	 * Diese Methode setzt eine an die nächste Stelle eines String.
	 *
	 * @param in
	 * @return
	 */
	public String setZero(String in) {
		in = in + "0";
		return in;
	}

	/**
	 * Diese Methode registriert die Tastendrücke und setzt den zugewiesenen
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
		// TODO Auto-generated method stub
	}

	/**
	 * Diese Methode registriert wenn eine Taste nicht mehr gedrückt wird und
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
		// TODO Auto-generated method stub

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

}

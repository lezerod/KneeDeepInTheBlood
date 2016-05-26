package controller;

import javafx.event.Event;
import model.GameWorld;
import view.EventList;
import view.MainWindow;

/**
 * Diese Klasse kontrolliert den Startvorgang des Spiels
 * @author Julien
 *
 */
public class GameController{

	/**
	 * Der Konstruktor erzeugt eine Instanz der View
	 */
	public GameController() {
		MainWindow view = new MainWindow();
		view.show();
	}

}

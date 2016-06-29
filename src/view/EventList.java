package view;

import javafx.scene.input.KeyEvent;

/**
 * Dieses Interface definiert die Schnittstelle zwischen View und Controller
 *
 * @author Julien
 *
 */
public interface EventList {

	/**
	 * Methode wird aufgerufen, wenn eine Taste wird im Spiel runtergedrueckt
	 *
	 * @param e
	 *            Der KeyCode
	 */
	public void raiseKeyDownEvent(KeyEvent e);

	/**
	 * Methode wird aufgerufen, wenn eine Taste wird im Spiel losgelassen wird
	 *
	 * @param e
	 *            Der KeyCode
	 */
	public void raiseKeyUpEvent(KeyEvent e);

	/**
	 * Methode wird aufgerufen, wenn ein Menuepunkt im MainMenu angeklickt wurde
	 *
	 * @param menuIndex
	 *            Der Index des Menuepunktes
	 */
	public void raiseMenuClick(int menuIndex);

	/**
	 * Methode wird aufgerufen, wenn ein Menuepunkt im NewGame Menue angeklickt
	 * wurde
	 *
	 * @param menuIndex
	 *            Der Index des Menuepunktes
	 */
	public void raiseNewGameClick(int menuIndex);

	/**
	 * Diese Methode wird aufgerufen, wenn eine Einstellung in der SceneSettings
	 * geaendert wurde.
	 *
	 * @param key
	 *            Der eindeutige Code der Einstellung
	 * @param value
	 *            Der neue Wert der Einstellung
	 */
	public void raiseSettingsClick(String key, int value);

	/**
	 * Diese Methode wird aufgerufen wenn auf einen Label in der SceneConnect
	 * gedrueckt wird.
	 *
	 * @param connect
	 *            true, wenn auf connect gedrueckt wurde, false wenn auf
	 *            abbrechen geklickt wurde.
	 * @param Ip
	 *            die IP, falls auf Connect gedrueckt wurde
	 */
	public void raiseConnectClick(boolean connect, String Ip);

}

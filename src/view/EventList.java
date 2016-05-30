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
	 * Methode wird aufgerufen, wenn eine Taste wird im Spiel runtergedr�ckt
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
	 * Methode wird aufgerufen, wenn ein Men�punkt im MainMenu angeklickt wurde
	 * 
	 * @param menuIndex
	 *            Der Index des Men�punktes
	 */
	public void raiseMenuClick(int menuIndex);

	/**
	 * Methode wird aufgerufen, wenn ein Men�punkt im NewGame Men� angeklickt
	 * wurde
	 * 
	 * @param menuIndex
	 *            Der Index des Men�punktes
	 */
	public void raiseNewGameClick(int menuIndex);

	// diese beiden sind noch nicht fertig implementiert !
	public void raiseSettingsClick(int menuIndex);

	public void raiseConnectClick(int menuIndex);

}

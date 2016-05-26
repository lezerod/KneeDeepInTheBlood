package view;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;

/**
 * Dieses Interface definiert die Schnittstelle zwischen View und Controller
 * @author Julien
 *
 */
public interface EventList {

	public void raiseKeyDownEvent(KeyEvent e);
	public void raiseKeyUpEvent(KeyEvent e);
	
}

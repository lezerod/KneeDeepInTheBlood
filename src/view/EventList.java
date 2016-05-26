package view;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;

public interface EventList {

	public void raiseKeyDownEvent(KeyEvent e);
	public void raiseKeyUpEvent(KeyEvent e);
	
}

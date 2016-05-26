package controller;

import javafx.event.Event;
import model.GameWorld;
import view.EventList;
import view.MainWindow;

public class GameController{

	GameWorld gameWorld;
	MainWindow view;

	public GameController() {
		view = new MainWindow();
		view.show();
	}

}

package controller;

import view.MainWindow;

/**
 * Dieses Klasse beinhaltet nur die Main-Methode
 * @author Julien
 *
 */
public class ClassMain {

	/**
	 * Hier startet das Programm und erzeugt eine neue Instanz
	 * des GameControllers
	 * @param args Übergabeparameter
	 */
	public static void main(String[] args) {
		MainWindow view = new MainWindow();
		view.show();
	}

}

/**
 * Diese Klasse stellt die GUI des Spiels dar
 * @author Julien Duchow
 */
package view;

import java.util.List;

import control.GameSettings;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Position;
import model.SpielfeldObject;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinMain extends Application {

	public static void show() {

		launch();

	}

	// Diese Variabeln dienen nur zum Testen !!
	private int rotateTest = 0;
	private Position positionTest = new Position(0, 0);
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		//Create a Fullscreen application with a black background
		Pane root = new Pane();
		root.setStyle("-fx-background-color: black;");
		primaryStage.setFullScreen(true);

		//Fensterbreite ermitteln
		double fensterBreite = Screen.getPrimary().getVisualBounds().getWidth();
		double fensterHoehe = Screen.getPrimary().getVisualBounds().getHeight();
		
		//Create the border of the gameWorld
		Rectangle worldBorder = new Rectangle();
		double spielfeldBreite = fensterBreite - (GameSettings.abstandWeltLinks + GameSettings.abstandWeltRechts);
		double spielfeldHoehe = fensterHoehe - (GameSettings.abstandWeltOben + GameSettings.abstandWeltUnten);
		worldBorder.setWidth(spielfeldBreite);
		worldBorder.setHeight(spielfeldHoehe);
		worldBorder.setLayoutX(GameSettings.abstandWeltLinks);
		worldBorder.setLayoutY(GameSettings.abstandWeltOben);
		worldBorder.setStyle("-fx-stroke: green;-fx-stroke-width: 5;");
		root.getChildren().add(worldBorder);
		
		//Status Anzeige:
		
		//Lebensanzeige
		Label lblInfLeben = new Label();
		lblInfLeben.setLayoutX(GameSettings.abstandWeltLinks);
		lblInfLeben.setLayoutY(GameSettings.statusAbstand);
		lblInfLeben.setText("Health");
		lblInfLeben.setStyle("-fx-text-fill: white;");
		root.getChildren().add(lblInfLeben);
		
		Rectangle recLebenVoll = new Rectangle();
		recLebenVoll.setStyle("-fx-fill: RGB(50,50,50)");
		recLebenVoll.setWidth(GameSettings.lebensAnzeigeBreite);
		recLebenVoll.setHeight(GameSettings.lebensAnzeigeHöhe);
		recLebenVoll.setLayoutX(GameSettings.abstandWeltLinks);
		recLebenVoll.setLayoutY(GameSettings.abstandWeltOben - GameSettings.lebensAnzeigeHöhe - GameSettings.statusAbstand);
		root.getChildren().add(recLebenVoll);
		
		Rectangle recLeben = new Rectangle();
		recLeben.setStyle("-fx-fill: RED");
		recLeben.setWidth(GameSettings.lebensAnzeigeBreite / 2);
		recLeben.setHeight(GameSettings.lebensAnzeigeHöhe);
		recLeben.setLayoutX(GameSettings.abstandWeltLinks);
		recLeben.setLayoutY(GameSettings.abstandWeltOben - GameSettings.lebensAnzeigeHöhe - GameSettings.statusAbstand);
		root.getChildren().add(recLeben);
		
		// Create the "Heldenfahrzeug" image
		Image imgHeldenfahrzeug = new Image(getClass().getResource("images/julien_panzer.png").toExternalForm(), GameSettings.heldenFahrzBreite, GameSettings.heldenFahrzHoehe, true,
				true);
		ImageView imgVHeldenfahrzeug = new ImageView();
		imgVHeldenfahrzeug.setImage(imgHeldenfahrzeug);
		//mittig ausrichten
		positionTest.setX((fensterBreite / 2)-(GameSettings.heldenFahrzBreite/2));
		positionTest.setY((fensterHoehe / 2)-(GameSettings.heldenFahrzHoehe/2));
		imgVHeldenfahrzeug.setLayoutX(positionTest.getX());
		imgVHeldenfahrzeug.setLayoutY(positionTest.getY());
		root.getChildren().add(imgVHeldenfahrzeug);

		
		Scene scene = new Scene(root);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				
				if (ke.getCode() == KeyCode.LEFT) {
					rotateTest-=GameSettings.winkelChangeSpeed;
				}
				if (ke.getCode() == KeyCode.RIGHT) {
					rotateTest+=GameSettings.winkelChangeSpeed;
				}
				if (ke.getCode() == KeyCode.UP) {
					positionTest = updateHeldenFahrzeugPosition(rotateTest, GameSettings.moveSpeed, positionTest.getX(), positionTest.getY(), false);
				}
				if (ke.getCode() == KeyCode.DOWN) {
					positionTest = updateHeldenFahrzeugPosition(rotateTest, GameSettings.moveSpeed, positionTest.getX(), positionTest.getY(), true);
				}
				
				//Position prüfen
				
				if (positionTest.getY() <= GameSettings.abstandWeltOben) {
					positionTest.setY(GameSettings.abstandWeltOben);
				}
				if (positionTest.getY() >= (GameSettings.abstandWeltUnten + spielfeldHoehe)) {
					positionTest.setY(GameSettings.abstandWeltUnten + spielfeldHoehe);
				}
				if (positionTest.getX() <= GameSettings.abstandWeltLinks) {
					positionTest.setX(GameSettings.abstandWeltLinks);
				}
				if (positionTest.getX() + GameSettings.heldenFahrzBreite >= GameSettings.abstandWeltRechts + spielfeldBreite) {
					positionTest.setX(GameSettings.abstandWeltRechts + spielfeldBreite - GameSettings.heldenFahrzBreite);
				}
				
				imgVHeldenfahrzeug.setRotate(rotateTest);
				imgVHeldenfahrzeug.setLayoutX(positionTest.getX());
				imgVHeldenfahrzeug.setLayoutY(positionTest.getY());
			}
		});

		primaryStage.setScene(scene);

		primaryStage.show();

	}
	
	/**
	 * Inizialisiert die View(erstellt Steuerelemte und passt diese an)
	 */
	private void initView() {
		
	}
	
	/**
	 * Methode stellt die Schnittstelle zum Controller her. Sie updated die Anzeige
	 * @param spielfeldObjekte Die Liste an Spielfeldobjekten, die auf dem Spielfeld sind
	 */
	public void update(List<SpielfeldObject> spielfeldObjekte) {
		
	}
	
	/**
	 * Diese Funktion berechnet die neue Position und gibt diese zurück an den Aufrufer
	 * @param winkel Der Winkel des zu berechnenden Objects
	 * @param speed Die Geschwindigkeit des Objekts
	 * @param x Die X-Koordinate
	 * @param y Die Y-Koordinate
	 * @return Die neu berechnete Position
	 */
	private Position updateHeldenFahrzeugPosition(int winkel, int speed, double x, double y, boolean negativ) {
		double deltaX;
		double deltaY;
		deltaY = Math.cos(winkel * (Math.PI/180)) * speed;
		deltaX = Math.sin(winkel * (Math.PI/180)) * speed;
		if(negativ)
		{
			return new Position(x - deltaX, y + deltaY);
		}
		else
		{
			return new Position(x + deltaX, y - deltaY);
		}
		
		
	}
	

}

/**
 * Diese Klasse stellt die GUI des Spiels dar
 * @author Julien Duchow
 */

package view;

import java.util.ArrayList;

import controller.GameSettings;
import controller.GameUpdateThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Alien;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.MoveableObject;

public class MainWindow extends Application {

	private ArrayList<EventList> listener = new ArrayList<EventList>();

	private Stage primaryStage;
	private BorderPane paneGame;
	private Scene sceneGame;
	private Pane spielfeld = new Pane();

	// kurzfristige Lösung
	private ImageView heldenFahrzeugImgV = new ImageView();
	private ArrayList<ImageView> aliensImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileFriendlyImgV = new ArrayList<ImageView>();

	/**
	 * startet die GUI Anzeige
	 */
	public void show() {
		launch();
	}

	/**
	 * wird automatisch von launch aufgerufen
	 */
	@Override
	public void start(Stage primStage) throws Exception {
		this.primaryStage = primStage;

		initGameView();
		switchScene(sceneGame);

		primaryStage.setTitle("Knee deep in the blood");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(GameSettings.IMGALIENPFAD)));

		primaryStage.show();

		// Starten des GameUpdateThreads
		GameUpdateThread gameUpdateThread = new GameUpdateThread(new GameWorld(GameSettings.BREITE, GameSettings.HÖHE),
				this);
		gameUpdateThread.start();

	}

	/**
	 * wechselt die Scene, die angezeigt wird
	 * @param scene Die neue Scene
	 */
	private void switchScene(Scene scene) {
		primaryStage.setScene(scene);
	}
	
	/**
	 * inizialisiert die View
	 */
	private void initGameView() {
		
		this.paneGame = new BorderPane();
		this.sceneGame = new Scene(paneGame);

		registerEvents();

		erzeugeSteuerelemente();

	}

	/**
	 * erzeugt alle Steuerelemente
	 */
	private void erzeugeSteuerelemente() {
		erzeugeSceneGame();
	}
	
	/**
	 * erzeugt alle Steuerelemente für SceneGame
	 */
	private void erzeugeSceneGame() {
		// Create the Border Panes:
		AnchorPane top = new AnchorPane();
		top.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setTop(top);
		top.setPadding(new Insets(GameSettings.RANDGRÖSSE));

		AnchorPane left = new AnchorPane();
		left.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setLeft(left);
		left.setPadding(new Insets(GameSettings.RANDGRÖSSE));

		AnchorPane right = new AnchorPane();
		right.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setRight(right);
		right.setPadding(new Insets(GameSettings.RANDGRÖSSE));

		AnchorPane bottom = new AnchorPane();
		bottom.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setBottom(bottom);
		bottom.setPadding(new Insets(GameSettings.RANDGRÖSSE));

		// Create the center Pane:

		Pane center = new Pane();
		center.setStyle("-fx-border-color: RGB(50,50,50);-fx-border-width: 1px;-fx-background-color: RGB(0,0,0);");
		paneGame.setCenter(center);

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGBACKGROUNDPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(1);
		imgVBackground.setLayoutY(1);
		imgVBackground.setFitHeight(GameSettings.HÖHE);
		imgVBackground.setFitWidth(GameSettings.BREITE);

		center.getChildren().add(imgVBackground);

		spielfeld = new Pane();
		spielfeld.setLayoutX(1);
		spielfeld.setLayoutY(1);
		center.getChildren().add(spielfeld);

		erzeugeDynamischeSteuerelemente();
	}
	
	/**
	 * ! Vorrübergehende Lösung !
	 */
	private void erzeugeDynamischeSteuerelemente() {
		for (int i = 0; i <= 9; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGALIENPFAD).toExternalForm(), 100, 100, true,
					true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			aliensImgV.add(imgView);
		}

		for (int i = 0; i <= 99; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGPROJEKTILPFAD).toExternalForm(), 100, 100,
					true, true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			projektileImgV.add(imgView);
		}

		for (int i = 0; i <= 9; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGPROJEKTILFRIENDLYPFAD).toExternalForm(), 100,
					100, true, true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			projektileFriendlyImgV.add(imgView);
		}

		Image img = new Image(getClass().getResource(GameSettings.IMGHELDENFAHRZEUGPFAD).toExternalForm(), 100, 100,
				true, true);
		heldenFahrzeugImgV = new ImageView();
		heldenFahrzeugImgV.setFitHeight(0);
		heldenFahrzeugImgV.setFitWidth(0);
		heldenFahrzeugImgV.setImage(img);

	}

	/**
	 * leitet die Events an die Listener weiter
	 */
	private void registerEvents() {
		sceneGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseKeyDownEvent(keyEvent);
				}
			}
		});

		sceneGame.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseKeyUpEvent(keyEvent);
				}
			}
		});
	}

	/**
	 * ermöglicht es dem Controller sich als listener zu registrieren
	 */
	public void registerEventListener(EventList e) {
		listener.add(e);
	}

	/**
	 * wird vom ControllerThread aufgerufen und updated die GUI gemäß der aktuellen GameWorld
	 * @param gameWorld
	 */
	public void updateView(GameWorld gameWorld) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				spielfeld.getChildren().clear();
				
				updateHeldenFahrzeug(gameWorld.getHeldenfahrzeug());
				updateAliens(gameWorld.getAliens());
				updateProjektile(gameWorld.getProjektile(), gameWorld.getProjektileFriendly());

			};
		});

	}

	/**
	 * updated das Heldenfahrzeug auf dem Spielfeld
	 * @param heldenfahrzeug das Heldenfahrzeug
	 */
	private void updateHeldenFahrzeug(HeldenFahrzeug heldenfahrzeug) {
		heldenFahrzeugImgV.setFitHeight(heldenfahrzeug.getHeight());
		heldenFahrzeugImgV.setFitWidth(heldenfahrzeug.getWidth());
		heldenFahrzeugImgV.setLayoutX(heldenfahrzeug.getX());
		heldenFahrzeugImgV.setLayoutY(heldenfahrzeug.getY());
		heldenFahrzeugImgV.setRotate(heldenfahrzeug.getWinkel());
		spielfeld.getChildren().add(heldenFahrzeugImgV);
	}

	/**
	 * updated die Aliens auf dem Spielfeld
	 * @param aliens Liste der Aliens
	 */
	private void updateAliens(ArrayList<Alien> aliens) {
		for (int i = 0; i < aliens.size(); i++) {
			ImageView imgVAlien = aliensImgV.get(i);
			imgVAlien.setFitHeight(aliens.get(i).getHeight());
			imgVAlien.setFitWidth(aliens.get(i).getWidth());
			imgVAlien.setLayoutX(aliens.get(i).getX());
			imgVAlien.setLayoutY(aliens.get(i).getY());
			spielfeld.getChildren().add(imgVAlien);
		}
	}

	/**
	 * updated die Projektile auf dem Spielfeld
	 * @param enemyProjektile die Alienprojektile
	 * @param friendlyProjektile die Heldenfahrzeugprojektile
	 */
	private void updateProjektile(ArrayList<MoveableObject> enemyProjektile,
			ArrayList<MoveableObject> friendlyProjektile) {
		updateEnemyProjektile(enemyProjektile);
		updateFriendlyProjektile(friendlyProjektile);
	}

	/**
	 * updated die freundlichen projektile
	 * @param friendlyProjektile die Heldenfahrzeugprojektile
	 */
	private void updateFriendlyProjektile(ArrayList<MoveableObject> friendlyProjektile) {
		for (int i = 0; i < friendlyProjektile.size(); i++) {
			ImageView imgVProj = projektileFriendlyImgV.get(i);
			imgVProj.setFitHeight(friendlyProjektile.get(i).getHeight());
			imgVProj.setFitWidth(friendlyProjektile.get(i).getWidth());
			imgVProj.setLayoutX(friendlyProjektile.get(i).getX());
			imgVProj.setLayoutY(friendlyProjektile.get(i).getY());
			spielfeld.getChildren().add(imgVProj);
		}
	}

	/**
	 * updated die feindlichen Projektile
	 * @param enemyProjektile die Projektile der Aliens
	 */
	private void updateEnemyProjektile(ArrayList<MoveableObject> enemyProjektile) {
		for (int i = 0; i < enemyProjektile.size(); i++) {
			ImageView imgVProj = projektileImgV.get(i);
			imgVProj.setFitHeight(enemyProjektile.get(i).getHeight());
			imgVProj.setFitWidth(enemyProjektile.get(i).getWidth());
			imgVProj.setLayoutX(enemyProjektile.get(i).getX());
			imgVProj.setLayoutY(enemyProjektile.get(i).getY());
			spielfeld.getChildren().add(imgVProj);
		}
	}

}

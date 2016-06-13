/**
 * Diese Klasse stellt die GUI des Spiels dar
 * @author Julien Duchow
 */

package view;

import java.util.ArrayList;

import controller.ClientThreadTCPControl;
import controller.ClientThreadTCPWORLD;
import controller.GameSettings;
import controller.GameUpdateThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Alien;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.MoveableObject;

public class ClientViewNew extends Application {

	private ArrayList<EventList> listener = new ArrayList<EventList>();

	private Stage primaryStage;

	private BorderPane paneGame;
	private Scene sceneGame;

	private Pane paneMainMenu;
	private Scene sceneMainMenu;

	private Pane spielfeld = new Pane();
	private Label lblLeben;
	private Label lblAliensSlain;
	private Label lblTimeLeft;

	// kurzfristige Lösung
	private ImageView heldenFahrzeugImgV = new ImageView();
	private ArrayList<ImageView> aliensImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileFriendlyImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileClientImgV = new ArrayList<ImageView>();

	/**
	 * startet die GUI Anzeige
	 */
	public void show() {
		launch();
	}

	/**
	 * wird automatisch von launch() aufgerufen
	 */
	@Override
	public void start(Stage primStage) throws Exception {
		this.primaryStage = primStage;

		initGameView();
//		switchScene(sceneMainMenu);
		switchScene(sceneGame);

		primaryStage.setTitle("Knee deep in the blood");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(GameSettings.IMGALIENPFAD)));
		primaryStage.show();
		primaryStage.setResizable(false);

		// Starten des GameUpdateThreads
//		GameUpdateThread gameUpdateThread = new GameUpdateThread(new GameWorld(GameSettings.BREITE, GameSettings.HÖHE),
//				this);
//		gameUpdateThread.start();
//				new ClientThreadTCPControl(this).start();
//				new ClientThreadTCPWORLD(new GameWorld(GameSettings.BREITE, GameSettings.HÖHE), this).start();
//				new ClientThreadTCPControl(this).start();
//		new ClientHandlerTCP(new ClientThreadTCPControl(this), new ClientThreadTCPWORLD(new GameWorld(GameSettings.BREITE, GameSettings.HÖHE), this));

	}

	/**
	 * wechselt die Scene, die angezeigt wird
	 * @param scene Die neue Scene
	 */
	public void switchScene(Scene scene) {
		primaryStage.setScene(scene);
	}

	/**
	 * inizialisiert die View
	 */
	private void initGameView() {

		this.paneGame = new BorderPane();
		this.sceneGame = new Scene(paneGame);

		this.paneMainMenu = new Pane();
		this.sceneMainMenu = new Scene(paneMainMenu, GameSettings.BREITE + (2 * GameSettings.RANDGRÖSSE) , GameSettings.HÖHE + (2 * GameSettings.RANDGRÖSSE));

		registerEvents();

		erzeugeSteuerelemente();

	}

	/**
	 * erzeugt alle Steuerelemente
	 */
	private void erzeugeSteuerelemente() {
		erzeugeSceneMainMenu();
		erzeugeSceneGame();
	}

	/**
	 * erzeugt alle Steuerelemente für SceneMainMenu
	 */
	private void erzeugeSceneMainMenu() {
		// Create the Border Panes:

		paneMainMenu.setStyle("-fx-background-color: RGB(0,0,0);");


		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGTITLEPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(GameSettings.BREITE / 2 + GameSettings.RANDGRÖSSE);
		imgVBackground.setLayoutY(20);
		imgVBackground.setFitHeight(GameSettings.HÖHE - 20);
		imgVBackground.setFitWidth(GameSettings.BREITE / 2);

		paneMainMenu.getChildren().add(imgVBackground);

		Label lbl = new Label("Knee deep in the blood");
		lbl.setLayoutX(60);
		lbl.setLayoutY(80);
		lbl.setPrefWidth(500);
		lbl.setFont(new Font(45));
		lbl.setStyle("-fx-text-fill: rgb(150, 50, 50);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);


		Pane menu = new Pane();
		menu.getChildren().add(lbl);
		paneMainMenu.getChildren().add(menu);

		//Title-Label

		Label menLbl = new Label();
		menLbl.setText("New Game");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(180);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		//register Event
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	for (int i=0;i<listener.size();i++) {
	        		listener.get(i).raiseMenuClick(0);
	        	}
	        }
	    });
		menu.getChildren().add(menLbl);

		//Menu-Labels:

		menLbl = new Label();
		menLbl.setText("Connect to game");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(240);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	for (int i=0;i<listener.size();i++) {
	        		listener.get(i).raiseMenuClick(1);
	        	}
	        }
	    });
		menu.getChildren().add(menLbl);

		menLbl = new Label();
		menLbl.setText("Settings");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(300);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	for (int i=0;i<listener.size();i++) {
	        		listener.get(i).raiseMenuClick(2);
	        	}
	        }
	    });
		menu.getChildren().add(menLbl);

		menLbl = new Label();
		menLbl.setText("Exit");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(360);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	for (int i=0;i<listener.size();i++) {
	        		listener.get(i).raiseMenuClick(3);
	        	}
	        }
	    });
		menu.getChildren().add(menLbl);


	}

	/**
	 * erzeugt alle Steuerelemente für SceneGame
	 */
	private void erzeugeSceneGame() {
		// Create the Border Panes:
		Pane top = new Pane();
		top.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setTop(top);
		// top.setPadding(new Insets(GameSettings.RANDGRÖSSE));

		lblLeben = new Label();
		lblAliensSlain = new Label();
		lblTimeLeft = new Label();

		//Die StatusLbl hinzufügen:
		lblLeben.setText("Lifes: 3");
		lblLeben.setFont(new Font(30));
		lblLeben.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblLeben.setLayoutX(GameSettings.RANDGRÖSSE * 2);
		lblLeben.setLayoutY(80);
		lblLeben.setPrefWidth(GameSettings.BREITE);
		top.getChildren().add(lblLeben);

		lblAliensSlain.setText("Aliens slain: 0");
		lblAliensSlain.setFont(new Font(30));
		lblAliensSlain.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblAliensSlain.setLayoutX(GameSettings.RANDGRÖSSE * 2);
		lblAliensSlain.setLayoutY(80);
		lblAliensSlain.setPrefWidth(GameSettings.BREITE);
		lblAliensSlain.setAlignment(Pos.CENTER);
		lblAliensSlain.setTextAlignment(TextAlignment.CENTER);
		top.getChildren().add(lblAliensSlain);

		lblTimeLeft.setText("3:00");
		lblTimeLeft.setFont(new Font(30));
		lblTimeLeft.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblTimeLeft.setLayoutX(GameSettings.RANDGRÖSSE * 2);
		lblTimeLeft.setLayoutY(80);
		lblTimeLeft.setPrefWidth(GameSettings.BREITE);
		lblTimeLeft.setAlignment(Pos.CENTER_RIGHT);
		lblTimeLeft.setTextAlignment(TextAlignment.RIGHT);
		top.getChildren().add(lblTimeLeft);

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

				updateStatusLbl(gameWorld.getLeben(), gameWorld.getAliensSlain(), gameWorld.getThreadTicks(), gameWorld.getMinutesToWin());

				spielfeld.getChildren().clear();

				updateHeldenFahrzeug(gameWorld.getHeldenfahrzeug());
				updateAliens(gameWorld.getAliens());
				updateProjektile(gameWorld.getProjektile(), gameWorld.getProjektileFriendly());

			};
		});

	}

	private void updateStatusLbl(int leben, int aliensSlain, int Ticks, int minutesToWin){
		lblLeben.setText("Lifes: " + leben);
		lblAliensSlain.setText("Alien slain: " + aliensSlain);
		//So jetzt die zeit berechen
		int millis = Ticks * GameSettings.THREADTICKTIME;
		int sekunden = millis / 1000;
		int sekLeft = (minutesToWin*60) - sekunden;
		//Sekundenzeit in schönen String wandeln:
		int minLeft = 0;
		while (sekLeft>59) {
			sekLeft-=60;
			minLeft+=1;
		}
		String sekString = Integer.toString(sekLeft);
		if (sekString.length() == 1) {
			sekString = "0" + sekString;
		}
		lblTimeLeft.setText(minLeft + ":" + sekString);
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

	/**
	 * formatiert den Label im "Menü-Style"
	 * @param lbl der zu formatierende Label
	 * @return der formatierte Label
	 */
	private  Label formatiereMenuLabel(Label lbl) {
		lbl.setFont(new Font(32));
		lbl.setStyle("-fx-text-fill: rgb(255, 255, 255);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);
		lbl.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	lbl.setStyle("-fx-text-fill: rgb(150, 50, 50);");
	        	sceneMainMenu.setCursor(Cursor.HAND);
	        }
	    });
		lbl.setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	lbl.setStyle("-fx-text-fill: rgb(255, 255, 255);");
	        	sceneMainMenu.setCursor(Cursor.DEFAULT);
	        }
	    });
		return lbl;
	}

	public Scene getSceneGame() {
		return sceneGame;
	}

	public void setSceneGame(Scene sceneGame) {
		this.sceneGame = sceneGame;
	}

	public Scene getSceneMainMenu() {
		return sceneMainMenu;
	}

	public void setSceneMainMenu(Scene sceneMainMenu) {
		this.sceneMainMenu = sceneMainMenu;
	}

}

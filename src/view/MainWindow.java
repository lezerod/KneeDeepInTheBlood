/**
 * Diese Klasse stellt die GUI des Spiels dar.
 *
 * @author Planung der Verknuepfung von View+Controller und Planung des Aufbaus der View:
 *  Julien, Till, Marco; Umsetzung: Julien
 *
 */

package view;

import java.net.URL;
import java.util.ArrayList;

import controller.ClientThreadTCPControl;
import controller.ClientThreadTCPWORLD;
import controller.GameSettings;
import controller.GameUpdateThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Alien;
import model.GameObject;
import model.GameWorld;
import model.HeldenFahrzeug;
import model.IwillDestroyYouTank;
import model.MoveableObject;
import javafx.scene.control.CheckBox;

public class MainWindow extends Application {

	private ArrayList<EventList> listener = new ArrayList<EventList>();

	private MediaPlayer musikPlayer;

	private Stage primaryStage;

	private BorderPane paneGame;
	private Scene sceneGame;

	private Pane paneMainMenu;
	private Scene sceneMainMenu;

	private Pane paneNewGame;
	private Scene sceneNewGame;

	private Pane paneSettings;
	private Scene sceneSettings;

	private Pane paneConnect;
	private Scene sceneConnect;

	private Pane spielfeld = new Pane();
	private Label lblLeben;
	private Label lblAliensSlain;
	private Label lblTimeLeft;

	// zur Darstellung der Projektile, der Aliens und des Heldenfahrzeugs
	private ImageView heldenFahrzeugImgV = new ImageView();
	private ImageView iWillDestroyYouTankImgV = new ImageView();
	private ImageView heldenFahrzeugBfgImgV = new ImageView();
	private ArrayList<ImageView> aliensImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> immAlienImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> itemImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileFriendlyImgV = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektiliWillDestroyImgV = new ArrayList<ImageView>();

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
		switchScene(sceneMainMenu);

		primaryStage.setTitle("Knee deep in the blood");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(GameSettings.IMGALIENPFAD)));
		primaryStage.show();
		primaryStage.setResizable(false);

		// Starten des GameUpdateThreads
		GameUpdateThread gameUpdateThread = new GameUpdateThread(new GameWorld(GameSettings.BREITE, GameSettings.HOEHE),
				this);
		gameUpdateThread.start();

	}

	/**
	 * wechselt die Scene, die angezeigt wird
	 *
	 * @param scene
	 *            die neue Scene
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
		this.sceneMainMenu = new Scene(paneMainMenu, GameSettings.BREITE + (2 * GameSettings.RANDGROESSE),
				GameSettings.HOEHE + (2 * GameSettings.RANDGROESSE));

		this.paneNewGame = new Pane();
		this.sceneNewGame = new Scene(paneNewGame, GameSettings.BREITE + (2 * GameSettings.RANDGROESSE),
				GameSettings.HOEHE + (2 * GameSettings.RANDGROESSE));

		this.paneSettings = new Pane();
		this.sceneSettings = new Scene(paneSettings, GameSettings.BREITE + (2 * GameSettings.RANDGROESSE),
				GameSettings.HOEHE + (2 * GameSettings.RANDGROESSE));

		this.paneConnect = new Pane();
		this.sceneConnect = new Scene(paneConnect, GameSettings.BREITE + (2 * GameSettings.RANDGROESSE),
				GameSettings.HOEHE + (2 * GameSettings.RANDGROESSE));

		registerEvents();

		erzeugeSteuerelemente();

	}

	/**
	 * erzeugt alle Steuerelemente
	 */
	private void erzeugeSteuerelemente() {
		erzeugeSceneMainMenu();
		erzeugeSceneGame();
		erzeugeSceneNewGame();
		erzeugeSceneSettings();
		erzeugeSceneConnect();
	}

	/**
	 * erzeugt alle Steuerelemente fuer SceneMainMenu
	 */
	private void erzeugeSceneMainMenu() {
		// Create the Border Panes:

		paneMainMenu.setStyle("-fx-background-color: RGB(0,0,0);");

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGTITLEPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(GameSettings.BREITE / 2 + GameSettings.RANDGROESSE);
		imgVBackground.setLayoutY(20);
		imgVBackground.setFitHeight(GameSettings.HOEHE - 20);
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

		// Title-Label

		Label menLbl = new Label();
		menLbl.setText("New Game");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(180);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		// register Event
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseMenuClick(0);
				}
			}
		});
		menu.getChildren().add(menLbl);

		// Menu-Labels:

		menLbl = new Label();
		menLbl.setText("Connect to game");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(240);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
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
				for (int i = 0; i < listener.size(); i++) {
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
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseMenuClick(3);
				}
			}
		});
		menu.getChildren().add(menLbl);

	}

	/**
	 * erzeugt die Steuerlemente der SceneNewGame
	 */
	private void erzeugeSceneNewGame() {

		paneNewGame.setStyle("-fx-background-color: RGB(0,0,0);");

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGTITLEPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(GameSettings.BREITE / 2 + GameSettings.RANDGROESSE);
		imgVBackground.setLayoutY(20);
		imgVBackground.setFitHeight(GameSettings.HOEHE - 20);
		imgVBackground.setFitWidth(GameSettings.BREITE / 2);

		paneNewGame.getChildren().add(imgVBackground);

		Label lbl = new Label("New Game");
		lbl.setLayoutX(60);
		lbl.setLayoutY(80);
		lbl.setPrefWidth(500);
		lbl.setFont(new Font(45));
		lbl.setStyle("-fx-text-fill: rgb(150, 50, 50);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);

		Pane menu = new Pane();
		menu.getChildren().add(lbl);
		paneNewGame.getChildren().add(menu);

		// Title-Label

		Label menLbl = new Label();
		menLbl.setText("Easy");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(180);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		// register Event
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseNewGameClick(0);
				}
			}
		});
		menu.getChildren().add(menLbl);

		// Menu-Labels:

		menLbl = new Label();
		menLbl.setText("Middle");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(240);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseNewGameClick(1);
				}
			}
		});
		menu.getChildren().add(menLbl);

		menLbl = new Label();
		menLbl.setText("Hard");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(300);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseNewGameClick(2);
				}
			}
		});
		menu.getChildren().add(menLbl);

		menLbl = new Label();
		menLbl.setText("back to Main Menu");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(400);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseNewGameClick(3);
				}
			}
		});
		menu.getChildren().add(menLbl);

	}

	/**
	 * erzeugt die Steuerlemente der SceneSettings
	 */
	private void erzeugeSceneSettings() {

		paneSettings.setStyle("-fx-background-color: RGB(0,0,0);");

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGTITLEPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(GameSettings.BREITE / 2 + GameSettings.RANDGROESSE);
		imgVBackground.setLayoutY(20);
		imgVBackground.setFitHeight(GameSettings.HOEHE - 20);
		imgVBackground.setFitWidth(GameSettings.BREITE / 2);

		paneSettings.getChildren().add(imgVBackground);

		// Title-Label

		Label lbl = new Label("Settings");
		lbl.setLayoutX(60);
		lbl.setLayoutY(80);
		lbl.setPrefWidth(500);
		lbl.setFont(new Font(45));
		lbl.setStyle("-fx-text-fill: rgb(150, 50, 50);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);

		Pane menu = new Pane();
		menu.getChildren().add(lbl);
		paneSettings.getChildren().add(menu);

		final CheckBox chk = new CheckBox();
		chk.setText("Enable Sounds");
		chk.setStyle("-fx-text-fill: rgb(255, 255, 255);");
		chk.setLayoutX(250);
		chk.setLayoutY(180);
		chk.setPrefWidth(150);
		chk.setFont(new Font(18));
		chk.setTextAlignment(TextAlignment.CENTER);
		chk.setAlignment(Pos.CENTER);
		chk.setSelected(true);
		chk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					if (chk.isSelected()) {
						listener.get(i).raiseSettingsClick("sound", 1);
					} else {
						listener.get(i).raiseSettingsClick("sound", 0);
					}
				}
			}
		});
		menu.getChildren().add(chk);

		final CheckBox chk2 = new CheckBox();
		chk2.setText("Enable Music");
		chk2.setStyle("-fx-text-fill: rgb(255, 255, 255);");
		chk2.setLayoutX(250);
		chk2.setLayoutY(240);
		chk2.setPrefWidth(150);
		chk2.setFont(new Font(18));
		chk2.setTextAlignment(TextAlignment.CENTER);
		chk2.setAlignment(Pos.CENTER);
		chk2.setSelected(true);
		chk2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					if (chk2.isSelected()) {
						listener.get(i).raiseSettingsClick("music", 1);
					} else {
						listener.get(i).raiseSettingsClick("music", 0);
					}
				}
			}
		});
		menu.getChildren().add(chk2);

		Label menLbl = new Label();
		menLbl.setText("back to Main Menu");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(340);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseNewGameClick(3);
				}
			}
		});
		menu.getChildren().add(menLbl);

	}

	/**
	 * erzeugt alle Steuerelemente fuer SceneConnect
	 */
	private void erzeugeSceneConnect() {

		paneConnect.setStyle("-fx-background-color: RGB(0,0,0);");

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGTITLEPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(GameSettings.BREITE / 2 + GameSettings.RANDGROESSE);
		imgVBackground.setLayoutY(20);
		imgVBackground.setFitHeight(GameSettings.HOEHE - 20);
		imgVBackground.setFitWidth(GameSettings.BREITE / 2);

		paneConnect.getChildren().add(imgVBackground);

		// Title-Label

		Label lbl = new Label("Connect to Game");
		lbl.setLayoutX(60);
		lbl.setLayoutY(80);
		lbl.setPrefWidth(500);
		lbl.setFont(new Font(45));
		lbl.setStyle("-fx-text-fill: rgb(150, 50, 50);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);

		Pane menu = new Pane();
		menu.getChildren().add(lbl);
		paneConnect.getChildren().add(menu);

		lbl = new Label("Enter IP:");
		lbl.setLayoutX(200);
		lbl.setLayoutY(210);
		lbl.setPrefWidth(200);
		lbl.setFont(new Font(18));
		lbl.setStyle("-fx-text-fill: rgb(255, 255, 255);");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);
		menu.getChildren().add(lbl);

		TextField tbIp = new TextField();
		tbIp.setFont(new Font(18));
		tbIp.setLayoutX(200);
		tbIp.setLayoutY(250);
		tbIp.setPrefWidth(200);
		tbIp.setAlignment(Pos.CENTER);
		menu.getChildren().add(tbIp);

		Label menLbl = new Label();
		menLbl.setText("Connect");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(300);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseConnectClick(true, tbIp.getText());
				}
			}
		});
		menu.getChildren().add(menLbl);

		menLbl = new Label();
		menLbl.setText("back to Main Menu");
		menLbl.setLayoutX(60);
		menLbl.setLayoutY(400);
		menLbl.setPrefWidth(500);
		menLbl = formatiereMenuLabel(menLbl);
		menLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < listener.size(); i++) {
					listener.get(i).raiseConnectClick(false,"");
				}
			}
		});
		menu.getChildren().add(menLbl);

	}

	/**
	 * erzeugt alle Steuerelemente fuer SceneGame
	 */
	private void erzeugeSceneGame() {
		// Create the Border Panes:
		Pane top = new Pane();
		top.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setTop(top);
		// top.setPadding(new Insets(GameSettings.RANDGROESSE));

		lblLeben = new Label();
		lblAliensSlain = new Label();
		lblTimeLeft = new Label();

		// Die StatusLbl hinzufuegen:
		lblLeben.setText("Lifes: 3");
		lblLeben.setFont(new Font(30));
		lblLeben.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblLeben.setLayoutX(GameSettings.RANDGROESSE * 2);
		lblLeben.setLayoutY(80);
		lblLeben.setPrefWidth(GameSettings.BREITE);
		top.getChildren().add(lblLeben);

		lblAliensSlain.setText("Aliens slain: 0");
		lblAliensSlain.setFont(new Font(30));
		lblAliensSlain.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblAliensSlain.setLayoutX(GameSettings.RANDGROESSE * 2);
		lblAliensSlain.setLayoutY(80);
		lblAliensSlain.setPrefWidth(GameSettings.BREITE);
		lblAliensSlain.setAlignment(Pos.CENTER);
		lblAliensSlain.setTextAlignment(TextAlignment.CENTER);
		top.getChildren().add(lblAliensSlain);

		lblTimeLeft.setText("3:00");
		lblTimeLeft.setFont(new Font(30));
		lblTimeLeft.setStyle("-fx-text-fill: rgb(150, 150, 150);");
		lblTimeLeft.setLayoutX(GameSettings.RANDGROESSE * 2);
		lblTimeLeft.setLayoutY(80);
		lblTimeLeft.setPrefWidth(GameSettings.BREITE);
		lblTimeLeft.setAlignment(Pos.CENTER_RIGHT);
		lblTimeLeft.setTextAlignment(TextAlignment.RIGHT);
		top.getChildren().add(lblTimeLeft);

		AnchorPane left = new AnchorPane();
		left.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setLeft(left);
		left.setPadding(new Insets(GameSettings.RANDGROESSE));

		AnchorPane right = new AnchorPane();
		right.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setRight(right);
		right.setPadding(new Insets(GameSettings.RANDGROESSE));

		AnchorPane bottom = new AnchorPane();
		bottom.setStyle("-fx-background-color: RGB(0,0,0);");
		paneGame.setBottom(bottom);
		bottom.setPadding(new Insets(GameSettings.RANDGROESSE));

		// Create the center Pane:

		Pane center = new Pane();
		center.setStyle("-fx-border-color: RGB(50,50,50);-fx-border-width: 1px;-fx-background-color: RGB(0,0,0);");
		paneGame.setCenter(center);

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGBACKGROUNDPFAD).toExternalForm());
		ImageView imgVBackground = new ImageView();
		imgVBackground.setImage(imgBackground);
		imgVBackground.setLayoutX(1);
		imgVBackground.setLayoutY(1);
		imgVBackground.setFitHeight(GameSettings.HOEHE);
		imgVBackground.setFitWidth(GameSettings.BREITE);

		center.getChildren().add(imgVBackground);

		spielfeld = new Pane();
		spielfeld.setLayoutX(1);
		spielfeld.setLayoutY(1);
		center.getChildren().add(spielfeld);

		erzeugeDynamischeSteuerelemente();
	}

	/**
	 * Diese Methode erzeugt eine Liste von ImageViews, die spaeter zur
	 * Darstellung des aktuellen Spielfeldes genutzt werden. Da die Bilder hier
	 * schon geladen werden, spart man spaeter diese Zeit und erhoeht die
	 * Framrate.
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

		for(int i = 0; i <= 9; i++){
			Image img = new Image(getClass().getResource(GameSettings.IMGALIENIMMUN).toExternalForm(), 100, 100, true,
					true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			immAlienImgV.add(imgView);
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
			Image img = new Image(getClass().getResource(GameSettings.IMGPROJEKTILECLIENT).toExternalForm(), 100,
					100, true, true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			projektiliWillDestroyImgV.add(imgView);
		}

		for(int i = 0; i <= 5; i++){
			Image img = new Image(getClass().getResource(GameSettings.IMGITEMBFG).toExternalForm(), 100, 100, true, true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			itemImgV.add(imgView);
		}

		Image img = new Image(getClass().getResource(GameSettings.IMGHELDENFAHRZEUGPFAD).toExternalForm(), 100, 100,
				true, true);
		heldenFahrzeugImgV = new ImageView();
		heldenFahrzeugImgV.setFitHeight(0);
		heldenFahrzeugImgV.setFitWidth(0);
		heldenFahrzeugImgV.setImage(img);


		Image img2 = new Image(getClass().getResource(GameSettings.IMGIWILLDESTORYYOUTANKPFAD).toExternalForm(), 100, 100, true, true);
		iWillDestroyYouTankImgV = new ImageView();
		iWillDestroyYouTankImgV.setFitHeight(0);
		iWillDestroyYouTankImgV.setFitWidth(0);
		iWillDestroyYouTankImgV.setImage(img2);
		iWillDestroyYouTankImgV.setVisible(false);

		Image img3 = new Image(getClass().getResource(GameSettings.IMGHELDENFAHRZEUGBFGPFAD).toExternalForm(), 100, 100,
				true, true);
		heldenFahrzeugBfgImgV = new ImageView();
		heldenFahrzeugBfgImgV.setFitHeight(0);
		heldenFahrzeugBfgImgV.setFitWidth(0);
		heldenFahrzeugBfgImgV.setImage(img3);
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
	 * ermoeglicht es dem Controller sich als listener zu registrieren
	 */
	public void registerEventListener(EventList e) {
		listener.add(e);
	}

	/**
	 * wird vom ControllerThread aufgerufen und updated die GUI gemaess der
	 * aktuellen GameWorld
	 *
	 * @param gameWorld
	 */
	public void updateView(GameWorld gameWorld) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				updateStatusLbl(gameWorld.getLeben(), gameWorld.getAliensSlain(), gameWorld.getThreadTicks(),
						gameWorld.getMinutesToWin());

				spielfeld.getChildren().clear();

				updateHeldenFahrzeug(gameWorld.getHeldenfahrzeug());
				updateAliens(gameWorld.getAliens());
				updateProjektile(gameWorld.getProjektile(), gameWorld.getProjektileFriendly());
				updateItems(gameWorld.getItems());
				updateImmunAliens(gameWorld.getImmunAliens());

				if(gameWorld.getIwillDestroyYouTank().isConnected()){
				updateIwillDestroyYouTank(gameWorld.getIwillDestroyYouTank());
				updateiWillProjektile(gameWorld.getProjektileClient());
				}
			};
		});

	}

	/**
	 * diese Methode updated die 3 Label in dem Spiel die Auskunft ueber den
	 * aktuellen Status geben
	 *
	 * @param leben
	 *            Das aktuelle eigene Leben
	 * @param aliensSlain
	 *            die Anzahl der getoeteten Aliens
	 * @param Ticks
	 *            die Anzahl der Schleifen-Durchlaeufe (fuer die Berechnung der
	 *            Zeit bis zum Sieg)
	 * @param minutesToWin
	 *            die Minuten die insgesamt ueberstanden werden muessen(abhaengig
	 *            vom Schwierigkeitsgrad)
	 */
	private void updateStatusLbl(int leben, int aliensSlain, int Ticks, int minutesToWin) {
		lblLeben.setText("Lifes: " + leben);
		lblAliensSlain.setText("Alien slain: " + aliensSlain);
		// So jetzt die zeit berechen
		int millis = Ticks * GameSettings.THREADTICKTIME;
		int sekunden = millis / 1000;
		int sekLeft = (minutesToWin * 60) - sekunden;
		// Sekundenzeit in schoenen String wandeln:
		int minLeft = 0;
		while (sekLeft > 59) {
			sekLeft -= 60;
			minLeft += 1;
		}
		String sekString = Integer.toString(sekLeft);
		if (sekString.length() == 1) {
			sekString = "0" + sekString;
		}
		lblTimeLeft.setText(minLeft + ":" + sekString);
	}

	/**
	 * updated das Heldenfahrzeug auf dem Spielfeld
	 *
	 * @param heldenfahrzeug
	 *            das Heldenfahrzeug
	 */
	private void updateHeldenFahrzeug(HeldenFahrzeug heldenfahrzeug) {
		if(heldenfahrzeug.isHasSpezialWeapon()){
		heldenFahrzeugBfgImgV.setFitHeight(heldenfahrzeug.getHeight());
		heldenFahrzeugBfgImgV.setFitWidth(heldenfahrzeug.getWidth());
		heldenFahrzeugBfgImgV.setLayoutX(heldenfahrzeug.getX());
		heldenFahrzeugBfgImgV.setLayoutY(heldenfahrzeug.getY());
		heldenFahrzeugBfgImgV.setRotate(heldenfahrzeug.getWinkel());
		spielfeld.getChildren().add(heldenFahrzeugBfgImgV);
		}
		else{
		heldenFahrzeugImgV.setFitHeight(heldenfahrzeug.getHeight());
		heldenFahrzeugImgV.setFitWidth(heldenfahrzeug.getWidth());
		heldenFahrzeugImgV.setLayoutX(heldenfahrzeug.getX());
		heldenFahrzeugImgV.setLayoutY(heldenfahrzeug.getY());
		heldenFahrzeugImgV.setRotate(heldenfahrzeug.getWinkel());
		spielfeld.getChildren().add(heldenFahrzeugImgV);
	}}

	public void activateIwillDestroyYouTank(IwillDestroyYouTank iwillDestroyYouTank){
		iWillDestroyYouTankImgV.setVisible(true);
	}
	private void updateIwillDestroyYouTank(IwillDestroyYouTank iwillDestroyYouTank){
		iWillDestroyYouTankImgV.setFitHeight(iwillDestroyYouTank.getHeight());
		iWillDestroyYouTankImgV.setFitWidth(iwillDestroyYouTank.getWidth());
		iWillDestroyYouTankImgV.setLayoutX(iwillDestroyYouTank.getX());
		iWillDestroyYouTankImgV.setLayoutY(iwillDestroyYouTank.getY());
		iWillDestroyYouTankImgV.setRotate(iwillDestroyYouTank.getWinkel());
		spielfeld.getChildren().add(iWillDestroyYouTankImgV);
	}


	/**
	 * updated die Aliens auf dem Spielfeld
	 *
	 * @param aliens
	 *            Liste der Aliens
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
	 *
	 * @param enemyProjektile
	 *            die Alienprojektile
	 * @param friendlyProjektile
	 *            die Heldenfahrzeugprojektile
	 */
	private void updateProjektile(ArrayList<MoveableObject> enemyProjektile,
			ArrayList<MoveableObject> friendlyProjektile){
		updateEnemyProjektile(enemyProjektile);
		updateFriendlyProjektile(friendlyProjektile);
	}

	private void updateItems(ArrayList<GameObject> items){
		for(int i = 0; i < items.size(); i++){
			ImageView imgVItem = itemImgV.get(i);
			imgVItem.setFitHeight(items.get(i).getHeight());
			imgVItem.setFitWidth(items.get(i).getWidth());
			imgVItem.setLayoutX(items.get(i).getX());
			imgVItem.setLayoutY(items.get(i).getY());
			imgVItem.setVisible(true);
			spielfeld.getChildren().add(imgVItem);
		}
	}

	/**
	 * updated die freundlichen projektile
	 *
	 * @param friendlyProjektile
	 *            die Heldenfahrzeugprojektile
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
	 *
	 * @param enemyProjektile
	 *            die Projektile der Aliens
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
	private void updateiWillProjektile(ArrayList<MoveableObject> iWillProjektile){
		for (int i = 0; i < iWillProjektile.size(); i++) {
			ImageView imgVProj = projektiliWillDestroyImgV.get(i);
			imgVProj.setFitHeight(iWillProjektile.get(i).getHeight());
			imgVProj.setFitWidth(iWillProjektile.get(i).getWidth());
			imgVProj.setLayoutX(iWillProjektile.get(i).getX());
			imgVProj.setLayoutY(iWillProjektile.get(i).getY());
			spielfeld.getChildren().add(imgVProj);
		}

	}

	private void updateImmunAliens(ArrayList<Alien> aliensImmun){
		for (int i = 0; i < aliensImmun.size(); i++) {
			ImageView imgVProj = immAlienImgV.get(i);
			imgVProj.setFitHeight(aliensImmun.get(i).getHeight());
			imgVProj.setFitWidth(aliensImmun.get(i).getWidth());
			imgVProj.setLayoutX(aliensImmun.get(i).getX());
			imgVProj.setLayoutY(aliensImmun.get(i).getY());
			spielfeld.getChildren().add(imgVProj);
		}

	}

	/**
	 * formatiert den Label im "Menue-Style"
	 *
	 * @param lbl
	 *            der zu formatierende Label
	 * @return der formatierte Label
	 */
	private Label formatiereMenuLabel(Label lbl) {
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

	public void playSound(String pfad) {
		final URL resource = getClass().getResource(pfad);
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	public void playMusic(String pfad) {
		final URL resource = getClass().getResource(pfad);
		final Media media = new Media(resource.toString());
		musikPlayer = new MediaPlayer(media);
		musikPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {

				musikPlayer.seek(Duration.ZERO);
			}
		});
		musikPlayer.play();
	}
	public void startClient(String ip, MainWindow view){
		Platform.runLater(new Runnable() {
			String g = ip;
			MainWindow h = view;
			@Override
			public void run() {
				new ClientThreadTCPControl(h, g).start();;
				new ClientThreadTCPWORLD(new GameWorld(GameSettings.BREITE, GameSettings.HOEHE), h, g).start();;
				// TODO Auto-generated method stub

			}
		});
	}

	public void stopMusic() {
		musikPlayer.stop();
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

	public Scene getSceneNewGame() {
		return sceneNewGame;
	}

	public void setSceneNewGame(Scene sceneNewGame) {
		this.sceneNewGame = sceneNewGame;
	}

	public Scene getSceneSettings() {
		return sceneSettings;
	}

	public void setSceneSettings(Scene sceneSettings) {
		this.sceneSettings = sceneSettings;
	}

	public Scene getSceneConnect() {
		return sceneConnect;
	}

	public void setSceneConnect(Scene sceneConnect) {
		this.sceneConnect = sceneConnect;
	}

}

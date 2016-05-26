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
import model.GameWorld;


public class MainWindow extends Application {

	private ArrayList<EventList> listener = new ArrayList<EventList>();

	private Stage primaryStage;
	private BorderPane paneGame;
	private Scene sceneGame;
	private Pane spielfeld = new Pane();

	private ArrayList<ImageView> aliens = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektile = new ArrayList<ImageView>();
	private ArrayList<ImageView> projektileFriendly = new ArrayList<ImageView>();

	public void show() {
		this.launch();
	}

	@Override
	public void start(Stage primStage) throws Exception {
		this.primaryStage = primStage;
		
		initGameView();
		primaryStage.setScene(sceneGame);

		primaryStage.setTitle("Knee deep in the blood");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(GameSettings.IMGALIENPFAD)));

		primaryStage.show();

		//Starten des GameUpdateThreads
		GameUpdateThread gameUpdateThread = new GameUpdateThread(new GameWorld(GameSettings.BREITE, GameSettings.HÖHE),
				this);
		gameUpdateThread.start();


	}

	public void registerEventListener(EventList e) {
		listener.add(e);
	}
	
	private void initGameView() {
		this.paneGame = new BorderPane();
		this.sceneGame = new Scene(paneGame);
		
		registerEvents();

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

		Image imgBackground = new Image(getClass().getResource(GameSettings.IMGBACKGROUNDPFAD).toExternalForm());// ,
																													// GameSettings.BREITE,
																													// GameSettings.HÖHE,true,true);
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
	
	private void erzeugeDynamischeSteuerelemente() {
		for (int i = 0; i <= 9; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGALIENPFAD).toExternalForm(), 100, 100, true,
					true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			aliens.add(imgView);
		}
		
		
		for (int i = 0; i <= 99; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGPROJEKTILPFAD).toExternalForm(), 100, 100, true,
					true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			projektile.add(imgView);
		}
		
		for (int i = 0; i <= 9; i++) {
			Image img = new Image(getClass().getResource(GameSettings.IMGPROJEKTILFRIENDLYPFAD).toExternalForm(), 100, 100, true,
					true);
			ImageView imgView = new ImageView();
			imgView.setFitHeight(0);
			imgView.setFitWidth(0);
			imgView.setImage(img);
			projektileFriendly.add(imgView);
		}
	}
	
	private void registerEvents() {
		sceneGame.setOnKeyPressed(
			      new EventHandler<KeyEvent>()
			      {
			         @Override
			         public void handle(KeyEvent keyEvent)
			         {
			            for (int i = 0;i<listener.size();i++) {
			            	listener.get(i).raiseKeyDownEvent(keyEvent);
			            }
			         }
			      }
			);
		
		sceneGame.setOnKeyReleased(
			      new EventHandler<KeyEvent>()
			      {
			         @Override
			         public void handle(KeyEvent keyEvent)
			         {
			            for (int i = 0;i<listener.size();i++) {
			            	listener.get(i).raiseKeyUpEvent(keyEvent);
			            }
			         }
			      }
			);
	}

	public void updateView(GameWorld gameWorld) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				spielfeld.getChildren().clear();

				for (int i = 0; i < gameWorld.getAliens().size(); i++) {

					ImageView imgVAlien = aliens.get(i);
					imgVAlien.setFitHeight(gameWorld.getAliens().get(i).getHeight());
					imgVAlien.setFitWidth(gameWorld.getAliens().get(i).getWidth());
					imgVAlien.setLayoutX(gameWorld.getAliens().get(i).getX());
					imgVAlien.setLayoutY(gameWorld.getAliens().get(i).getY());

					spielfeld.getChildren().add(imgVAlien);

				}
				
				for (int i = 0; i < gameWorld.getProjektile().size(); i++) {

					ImageView imgVProj = projektile.get(i);
					imgVProj.setFitHeight(gameWorld.getProjektile().get(i).getHeight());
					imgVProj.setFitWidth(gameWorld.getProjektile().get(i).getWidth());
					imgVProj.setLayoutX(gameWorld.getProjektile().get(i).getX());
					imgVProj.setLayoutY(gameWorld.getProjektile().get(i).getY());

					
					spielfeld.getChildren().add(imgVProj);

				}
				
				for (int i = 0; i < gameWorld.getProjektileFriendly().size(); i++) {

					ImageView imgVProj = projektileFriendly.get(i);
					imgVProj.setFitHeight(gameWorld.getProjektile().get(i).getHeight());
					imgVProj.setFitWidth(gameWorld.getProjektile().get(i).getWidth());
					imgVProj.setLayoutX(gameWorld.getProjektile().get(i).getX());
					imgVProj.setLayoutY(gameWorld.getProjektile().get(i).getY());

					
					spielfeld.getChildren().add(imgVProj);

				}

				// Heldenfahrzeug:
				Image imgHeld = new Image(getClass().getResource(GameSettings.IMGHELDENFAHRZEUGPFAD).toExternalForm());
				ImageView imgVHeld = new ImageView();
				imgVHeld.setImage(imgHeld);
				imgVHeld.setFitHeight(gameWorld.getHeldenfahrzeug().getHeight());
				imgVHeld.setFitWidth(gameWorld.getHeldenfahrzeug().getWidth());
				imgVHeld.setLayoutX(gameWorld.getHeldenfahrzeug().getX());
				imgVHeld.setLayoutY(gameWorld.getHeldenfahrzeug().getY());

				imgVHeld.setRotate(gameWorld.getHeldenfahrzeug().getWinkel());
				
				spielfeld.getChildren().add(imgVHeld);

			};

		});

	}

}

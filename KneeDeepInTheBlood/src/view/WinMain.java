package view;


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

public class WinMain extends Application {

	Rectangle rectangle = new Rectangle();
	
	public WinMain() {
		
	}
	
	public static void show(){
		
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
Pane root = new Pane();
			


		rectangle.setLayoutX(200);
		rectangle.setLayoutY(200);
		rectangle.setWidth(10);
		rectangle.setHeight(10);
		
		rectangle.setStyle("-fx-fill: orange;");
	
		rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
		      public void handle(MouseEvent me) {
		        System.out.println("Mouse pressed");
		      }
		    });
		
		
		
		root.getChildren().add(rectangle);
		
		root.setStyle("-fx-background-color: black;");
		
		Scene scene = new Scene(root, GameSettings.breite, GameSettings.höhe);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            if (ke.getCode() == KeyCode.LEFT) {
	            	rectangle.setLayoutX(rectangle.getLayoutX()-GameSettings.moveSpeed);
	            }
	            if (ke.getCode() == KeyCode.RIGHT) {
	            	rectangle.setLayoutX(rectangle.getLayoutX()+GameSettings.moveSpeed);
	            }
	        }
	    });
		
		
		//rectangle.getTransforms().add(new Rotate)
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	

}

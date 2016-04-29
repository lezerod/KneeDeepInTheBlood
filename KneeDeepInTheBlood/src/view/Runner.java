import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Runner extends Application {

	private static final double W = 600, H = 400;

	private static final String HERO_IMAGE_LOC = "http://dullahansoft.com/pixel/wp-content/uploads/2014/07/enterprise-big.png";
	private static final String PROJECTIL_IMAGE_LOC = "http://files.gamebanana.com/img/ico/sprays/510788ce439b7.gif";
	private static final String ENEMY_IMAGE_LOC = "http://vignette4.wikia.nocookie.net/metroid/images/4/40/Metroid_super.gif/revision/latest?cb=20090415203739";

	private Image spaceshipImage;
	private Image daLaserImage;
	private Image enemyImage;
	private Node spaceship;
	private Node enemy;
	private Group space;
	boolean running, goNorth, goSouth, goEast, goWest, shoot;
	private int counter = 0;

	@Override
	public void start(Stage stage) throws Exception {
		spaceshipImage = new Image(HERO_IMAGE_LOC, 100, 100, false, false);
		spaceship = new ImageView(spaceshipImage);
		space = new Group(spaceship);

		/**
		 *
		 * ENEMY TEST
		 */
		enemyImage = new Image(ENEMY_IMAGE_LOC, 100, 100, false, false);
		enemy = new ImageView(enemyImage);
		space.getChildren().add(enemy);
		enemy.relocate(250, 1);
		/**
		 *
		 *
		 */

		/**
		 *
		 * Startposition
		 *
		 */
		moveHeroTo((W / 2), (H - spaceshipImage.getHeight()));

		Scene scene = new Scene(space, W, H, Color.BLACK);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goNorth = true;
					break;
				case DOWN:
					goSouth = true;
					break;
				case LEFT:
					goWest = true;
					break;
				case RIGHT:
					goEast = true;
					break;
				case SHIFT:
					running = true;
					break;
				case SPACE:
					shoot = true;
					break;
				}
				if (shoot) {
					shootMissels();
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goNorth = false;
					break;
				case DOWN:
					goSouth = false;
					break;
				case LEFT:
					goWest = false;
					break;
				case RIGHT:
					goEast = false;
					break;
				case SHIFT:
					running = false;
					break;
				case SPACE:
					shoot = false;
					break;
				}

			}
		});

		/**
		 * Methode für Resize
		 */
		// scene.widthProperty().addListener(new ChangeListener<Number>() {
		// @Override public void changed(ObservableValue<? extends Number>
		// observableValue, Number oldSceneWidth, Number newSceneWidth) {
		// System.out.println("Width: " + newSceneWidth);
		// }
		// });
		// scene.heightProperty().addListener(new ChangeListener<Number>() {
		// @Override public void changed(ObservableValue<? extends Number>
		// observableValue, Number oldSceneHeight, Number newSceneHeight) {
		// System.out.println("Height: " + newSceneHeight);
		// }
		// });

		stage.setScene(scene);
		stage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (goNorth)
					dy -= 1;
				if (goSouth)
					dy += 1;
				if (goEast)
					dx += 1;
				if (goWest)
					dx -= 1;
				if (running) {
					dx *= 3;
					dy *= 3;
				}

				moveHeroBy(dx, dy);
			}

		};
		timer.start();
	}

	private void moveHeroBy(int dx, int dy) {
		if (dx == 0 && dy == 0)
			return;

		final double cx = spaceship.getBoundsInLocal().getWidth() / 2;
		final double cy = spaceship.getBoundsInLocal().getHeight() / 2;

		double x = cx + spaceship.getLayoutX() + dx;
		double y = cy + spaceship.getLayoutY() + dy;
		// System.out.println("X = " + spaceship.getLayoutX() + " Y = " +
		// spaceship.getLayoutY());
		moveHeroTo(x, y);
	}

	private void moveHeroTo(double x, double y) {
		final double cx = spaceship.getBoundsInLocal().getWidth() / 2;
		final double cy = spaceship.getBoundsInLocal().getHeight() / 2;

		if (x - cx >= 0 && x + cx <= W && y - cy >= 0 && y + cy <= H) {
			spaceship.relocate(x - cx, y - cy);
		}
	}

	private void shootMissels() {
		System.out.println(counter);

		if (counter == 5) {
			System.out.println("TOO MANY LAZORS");
		} else {
			counter++;

			daLaserImage = new Image(PROJECTIL_IMAGE_LOC, 100, 100, false, false);
			Node daLaser = new ImageView(daLaserImage);
			space.getChildren().add(daLaser);
			daLaser.setLayoutX(spaceship.getLayoutX());
			daLaser.setLayoutY(spaceship.getLayoutY());
			// System.out.println(daLaser.getBoundsInLocal());
			// System.out.println(daLaser.getLayoutBounds());

			new AnimationTimer() {

				@Override
				public void handle(long now) {
					double dY = daLaser.getLayoutY();
					double dX = daLaser.getLayoutX();
					daLaser.relocate(dX, dY - 5);
					if (collision(daLaser, enemy)) {
						space.getChildren().remove(enemy);
					}
					// System.out.println(dY);
					if (dY <= 0) {
						space.getChildren().remove(daLaser);
						this.stop();
						counter--;
					}
				}
			}.start();

		}
	}

	public boolean collision(Node lazor, Node Enemy) {
		if (lazor.getBoundsInParent().intersects(Enemy.getBoundsInParent())) {
			return true;
		} else
			return false;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Brick of the checkers board game. A brick has a x/y position and a color. A brick has implemented
 * mouse events to move the brick to another position.
 */
class Brick extends Pane {
  private double xPos, yPos, mouseX, mouseY;

  /**
   * Constructor to create a new brick object.
   *
   * @param color Color of the brick
   * @param x Position x
   * @param y Position y
   */
  Brick(Color color, int x, int y) {
    Circle brick = new Circle(0.33 * Checkers.GRID_SIZE, color);
    move(x, y);
    getChildren().add(brick);

    setOnMousePressed(
        e -> {
          mouseX = e.getSceneX();
          mouseY = e.getSceneY();
        });

    setOnMouseDragged(
        e -> {
          double deltaX = e.getSceneX() - mouseX;
          double deltaY = e.getSceneY() - mouseY;
          relocate(deltaX + xPos, deltaY + yPos);
        });

    // There is a third event "setOnMouseReleased" implemented in the main application. This third
    // event has to be in the main application to have access to further objects.
  }

  /**
   * Placement of the brick.
   *
   * @param x Position x
   * @param y Position y
   */
  void move(int x, int y) {
    xPos = x * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2;
    yPos = y * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2;
    relocate(xPos, yPos);
  }
}

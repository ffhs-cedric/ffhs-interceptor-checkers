import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Brick of the checkers board game. A brick has a x/y position and a color. A brick has implemented
 * mouse events to move the brick to another position.
 */
class Brick extends Pane {

  private final Color color;
  private final int moveDir;
  private final boolean isDame;

  private double mouseX;
  private double mouseY;
  private double x;
  private double y;

  /**
   * Constructor to create a new brick object.
   *
   * @param color Color of the brick
   * @param x Position x
   * @param y Position y
   */
  Brick(Color color, int x, int y, boolean isDame) {
    this.color = color;
    this.moveDir = color.equals(Player.P1_COLOR) ? 1 : -1;
    this.isDame = isDame;

    Circle brick = new Circle(0.33 * Checkers.GRID_SIZE, this.getColor());
    move(x, y);

    setOnMousePressed(
        e -> {
          mouseX = e.getSceneX();
          mouseY = e.getSceneY();
        });

    setOnMouseDragged(
        e -> {
          double deltaX = e.getSceneX() - mouseX;
          double deltaY = e.getSceneY() - mouseY;
          relocate(deltaX + getX(), deltaY + getY());
        });

    // There is a third event "setOnMouseReleased" implemented in the main application. This third
    // event has to be in the main application to have access to further objects.

    if (this.isDame()) {
      brick.setStroke(Color.GOLD);
      brick.setStrokeWidth(2);
    }
    getChildren().add(brick);
  }

  /**
   * Placement of the brick.
   *
   * @param x Position x
   * @param y Position y
   */
  void move(int x, int y) {
    setX(x * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2);
    setY(y * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2);
    relocate(getX(), getY());
  }

  /** Reset position on invalid movement */
  void resetMove() {
    relocate(getX(), getY());
  }

  double getX() {
    return x;
  }

  private void setX(double x) {
    this.x = x;
  }

  double getY() {
    return y;
  }

  private void setY(double y) {
    this.y = y;
  }

  Color getColor() {
    return color;
  }

  int getMoveDir() {
    return moveDir;
  }

  boolean isDame() {
    return isDame;
  }
}

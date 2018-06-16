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
    this.moveDir = color.equals(Checkers.P1_COLOR) ? 1 : -1;
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
   * Determines if current brick IS able to devour an opposing brick
   *
   * @param board
   * @return Returns true if brick is able to eat an opposing brick
   */
  boolean isAbleToDevour(Field[][] board) {

    int dir = getMoveDir();
    int x = Field.getFieldCoord(getX());
    int y = Field.getFieldCoord(getY());

    for (int i = -1; i < 2; i += 2) {
      int x1 = x + i;
      int x2 = x + i * 2;
      int y1 = y + dir;
      int y2 = y + dir * 2;

      if (Field.inBoardRange(x1, y1) && Field.inBoardRange(x2, y2)) {
        Brick t = board[x1][y1].getBrick();
        if (t != null && !t.getColor().equals(getColor()) && board[x2][y2].getBrick() == null) {
          return true;
        }
      }
      if (isDame()) {
        y1 = y + dir * -1;
        y2 = y + dir * -2;

        if (Field.inBoardRange(x1, y1) && Field.inBoardRange(x2, y2)) {
          Brick t = board[x1][y1].getBrick();
          if (t != null && !t.getColor().equals(getColor()) && board[x2][y2].getBrick() == null) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * TODO
   *
   * @param dir
   * @param y
   * @return
   */
  static boolean hasFieldBoundaryReached(int dir, int y) {
    return y + dir < 0 || y + dir > 7;
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

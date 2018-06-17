import javafx.scene.paint.Color;

abstract class Player implements Runnable {
  static final Color P1_COLOR = Color.DARKGREEN;
  static final Color P2_COLOR = Color.DARKRED;

  private Color color;

  Player(Color color) {
    this.color = color;
  }

  /** */
  abstract void getMove();

  // abstract Brick brickHandler(int x, int y, boolean d);

  Color getColor() {
    return color;
  }
}

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Field of the checkers board game. A field has a x/y position and a color. A field can have a
 * brick set or not.
 */
class Field extends Rectangle {

  private static final Color BRIGHT = Color.BEIGE;
  private static final Color DARK = Color.BLACK;

  private Brick brick;
  private final Color color;

  /**
   * Constructor to create a new field object.
   *
   * @param x Position x
   * @param y Position y
   * @param dark Color switcher, dark is DARK, !dark is BRIGHT
   */
  Field(int x, int y, boolean dark) {
    setWidth(Checkers.GRID_SIZE);
    setHeight(Checkers.GRID_SIZE);
    relocate(x * Checkers.GRID_SIZE, y * Checkers.GRID_SIZE);

    color = dark ? DARK : BRIGHT;
    setFill(color);
  }

  /** @return */
  boolean isDark() {
    return getColor().equals(Field.DARK);
  }

  boolean isEmpty() {
    return getBrick() == null;
  }

  /**
   * Determines field according to pixel position
   *
   * @param pos Pixel position to get field for
   * @return Returns field of the board
   */
  static int getFieldCoord(double pos) {
    return (int) pos / Checkers.GRID_SIZE;
  }

  /**
   * TODO
   *
   * @param x
   * @param y
   * @return
   */
  static boolean inBoardRange(int x, int y) {
    return x >= 0 && y >= 0 && x < Checkers.GRID_COUNT && y < Checkers.GRID_COUNT;
  }

  /**
   * Set a brick object on to the field
   *
   * @param brick Brick object to set on to the field
   */
  void setBrick(Brick brick) {
    this.brick = brick;
  }

  /** @return */
  Brick getBrick() {
    return brick;
  }

  Color getColor() {
    return color;
  }
}

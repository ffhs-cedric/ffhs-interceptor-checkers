import javafx.scene.paint.Color;

/**
 * Field of the checkers board game. A field has a x/y position and a color. A field can have a
 * brick set or not.
 */
class Field extends Board {

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
   * Test if x&z pair is in board range
   *
   * @param x x coordinate
   * @param y z coordinate
   * @return Returns true if coordinates are in board range
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

    /** if (brick != null) { addToBrickGroup(brick); }* */
  }

  /** @return */
  Brick getBrick() {
    return brick;
  }

  /** @param brick */
  void removeBrick(Brick brick) {
    setBrick(null);

    removeFromBrickGroup(brick);
  }

  Color getColor() {
    return color;
  }
}

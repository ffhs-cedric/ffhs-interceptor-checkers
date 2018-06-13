import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Field of the checkers board game. A field has a x/y position and a color. A field can have a
 * brick set or not.
 */
class Field extends Rectangle {

  private static final Color BRIGHT = Color.LIGHTGREY;
  private static final Color DARK = Color.BLACK;

  private Brick brick;
  private Color color;

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
}

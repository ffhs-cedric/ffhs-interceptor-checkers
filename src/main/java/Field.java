import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Field of the checkers board game. A field has a x/y position and a color. A field can have a
 * brick set or not.
 */
class Field extends Rectangle {

  private Brick brick;

  /**
   * Constructor to create a new field object.
   *
   * @param x Position x
   * @param y Position y
   * @param black Color switcher, black is black, !black is white
   */
  Field(int x, int y, boolean black) {
    setWidth(Checkers.GRID_SIZE);
    setHeight(Checkers.GRID_SIZE);
    relocate(x * Checkers.GRID_SIZE, y * Checkers.GRID_SIZE);
    setFill(black ? Color.valueOf("black") : Color.valueOf("white"));
  }

  /**
   * Set a brick object on to the field
   *
   * @param brick Brick object to set on to the field
   */
  void setBrick(Brick brick) {
    this.brick = brick;
  }
}

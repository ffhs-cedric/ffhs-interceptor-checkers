import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Field extends Rectangle {

  Brick brick;

  Field(int x, int y, boolean black) {
    setWidth(Checkers.GRID_SIZE);
    setHeight(Checkers.GRID_SIZE);
    relocate(x * Checkers.GRID_SIZE, y * Checkers.GRID_SIZE);
    setFill(black ? Color.valueOf("black") : Color.valueOf("white"));
  }

  public Brick getBrick() {
    return brick;
  }

  public void setBrick(Brick brick) {
    this.brick = brick;
  }
}

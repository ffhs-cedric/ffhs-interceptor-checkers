import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Field extends Rectangle {

  Field(int x, int y, boolean black) {
    setWidth(Checkers.GRID_SIZE);
    setHeight(Checkers.GRID_SIZE);
    relocate(x * Checkers.GRID_SIZE, y * Checkers.GRID_SIZE);
    setFill(black ? Color.valueOf("black") : Color.valueOf("white"));
  }
}

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Brick extends Pane {
  Brick(Color color, int x, int y) {
    double radius = 0.33 * Checkers.GRID_SIZE;

    Circle brick = new Circle(radius, color);
    brick.setStroke(Color.BLACK);
    brick.relocate(x * Checkers.GRID_SIZE + (radius / 2), y * Checkers.GRID_SIZE + (radius / 2));
    getChildren().add(brick);
  }
}

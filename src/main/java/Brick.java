import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Brick extends Pane {
  private double xPos, yPos, mouseX, mouseY;

  Brick(Color color, int x, int y) {
    Circle brick = new Circle(0.33 * Checkers.GRID_SIZE, color);
    move(x, y);
    getChildren().add(brick);

    setOnMousePressed(
        e -> {
          mouseX = e.getSceneX();
          mouseY = e.getSceneY();
        });

    setOnMouseDragged(
        e -> {
          double deltaX = e.getSceneX() - mouseX;
          double deltaY = e.getSceneY() - mouseY;
          relocate(deltaX + xPos, deltaY + yPos);
        });
  }

  void move(int x, int y) {
    xPos = x * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2;
    yPos = y * Checkers.GRID_SIZE + Checkers.GRID_SIZE / 2;
    relocate(xPos, yPos);
  }
}

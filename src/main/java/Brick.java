import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Brick extends Pane {
  private double xPos, yPos, mouseX, mouseY;

  Brick(Color color, int x, int y) {

    double radius = 0.33 * Checkers.GRID_SIZE;

    xPos = x * Checkers.GRID_SIZE + (radius / 2);
    yPos = y * Checkers.GRID_SIZE + (radius / 2);

    Circle brick = new Circle(radius, color);
    brick.setStroke(Color.BLACK);
    brick.relocate(xPos, yPos);
    getChildren().add(brick);

    setOnMousePressed(
        e -> {
          setFocused(true);
          mouseX = e.getSceneX();
          mouseY = e.getSceneY();
        });

    setOnMouseDragged(
        e -> {
          double deltaX = e.getSceneX() - mouseX;
          double deltaY = e.getSceneY() - mouseY;
          relocate(deltaX + xPos, deltaY + yPos);
          xPos = brick.getCenterX();
          yPos = brick.getCenterY();
        });
  }
}

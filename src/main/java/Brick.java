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
          mouseX = e.getSceneX();
          mouseY = e.getSceneY();
        });

    setOnMouseDragged(
        e -> {
          relocate((e.getSceneX() - mouseX + xPos) / 2, (e.getSceneY() - mouseY + yPos) / 2);
          System.out.println("xPos:" + xPos);
          System.out.println("yPos:" + yPos);
          System.out.println("xScene:" + (e.getSceneX()));
          System.out.println("ySCene:" + (e.getSceneY()));
          System.out.println("xNew:" + (e.getSceneX() - mouseX + xPos));
          System.out.println("yNew:" + (e.getSceneY() - mouseY + yPos));
        });
  }
}

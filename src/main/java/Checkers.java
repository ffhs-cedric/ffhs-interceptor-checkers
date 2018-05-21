import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Piotr Mazurek
 * @author Patrick Glaser
 * @author Cedric Muehlebach
 */
public class Checkers extends Application {

  static final int GRID_SIZE = 100;
  private static final int GRID_COUNT = 8;

  private Group gridGroup = new Group();
  private Group brickGroup = new Group();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = new Scene(initGame());
    primaryStage.setTitle("FFHS-CHECKERS \u00a9interceptor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Parent initGame() {
    Pane panel = new Pane();
    panel.setPrefSize(GRID_SIZE * GRID_COUNT, GRID_SIZE * GRID_COUNT);
    panel.getChildren().addAll(gridGroup, brickGroup);

    for (int x = 0; x < GRID_COUNT; x++) {
      for (int y = 0; y < GRID_COUNT; y++) {
        Field field = new Field(x, y, (x + y) % 2 != 0);
        gridGroup.getChildren().add(field);

        Brick brick;
        if (y <= 2 && (x + y) % 2 != 0) {
          brick = new Brick(Color.DARKGREEN, x, y);
          brickGroup.getChildren().add(brick);
        } else if (y >= 5 && (x + y) % 2 != 0) {
          brick = new Brick(Color.DARKRED, x, y);
          brickGroup.getChildren().add(brick);
        }
      }
    }

    return panel;
  }
}

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

  private Field[][] board = new Field[GRID_COUNT][GRID_COUNT];

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(initGame());
    stage.setTitle("FFHS-CHECKERS \u00a9interceptor");
    stage.setScene(scene);
    stage.show();
  }

  private Parent initGame() {
    Pane panel = new Pane();
    panel.setPrefSize(GRID_SIZE * GRID_COUNT, GRID_SIZE * GRID_COUNT);
    panel.getChildren().addAll(gridGroup, brickGroup);

    for (int x = 0; x < GRID_COUNT; x++) {
      for (int y = 0; y < GRID_COUNT; y++) {
        Field field = new Field(x, y, (x + y) % 2 != 0);
        gridGroup.getChildren().add(field);
        board[x][y] = field;

        if (y <= 2 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(Color.DARKGREEN, x, y);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        } else if (y >= 5 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(Color.DARKRED, x, y);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        }
      }
    }

    return panel;
  }

  private Brick brickHandler(Color color, int x, int y) {
    Brick brick = new Brick(color, x, y);

    brick.setOnMouseReleased(
        e -> {
          int newXPos = getField(brick.getLayoutX());
          int newYPos = getField(brick.getLayoutY());
          System.out.println(newXPos);
          System.out.println(newYPos);

          brick.move(newXPos, newYPos);
          board[x][y].setBrick(null);
          board[getField(newXPos)][getField(newYPos)].setBrick(brick);
        });

    return brick;
  }

  private int getField(double pos) {
    return (int) pos / GRID_SIZE;
  }
}

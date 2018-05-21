import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FFHS group interceptor - Semesterarbeit "Dame-Brettspiel". As frontend we have chosen JavaFX as
 * it was developed as Swing replacement. Enjoy the game!
 *
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

  /**
   * Main method / entry point of this checkers game
   *
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Implements method of class javafx.application.Application
   *
   * @param stage Main stage of this JavaFX application
   */
  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(initGame());
    stage.setTitle("FFHS-CHECKERS \u00a9interceptor");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Initializes checkers game. Creates a panel, a game board, fields and bricks
   *
   * @return Returns created Pane object used by the JavaFX Scene
   */
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

  /**
   * Wrapper for creating bricks. Wrapper is needed to define a setOnMouseReleased event in this
   * main class.
   *
   * @param color Color of new brick
   * @param x Field position (x) of brick on game board
   * @param y Field position (y) of brick on game board
   * @return Returns created brick
   */
  private Brick brickHandler(Color color, int x, int y) {
    Brick brick = new Brick(color, x, y);

    brick.setOnMouseReleased(
        e -> {
          int newXPos = getBoardField(brick.getLayoutX());
          int newYPos = getBoardField(brick.getLayoutY());

          brick.move(newXPos, newYPos);
          board[x][y].setBrick(null);
          board[getBoardField(newXPos)][getBoardField(newYPos)].setBrick(brick);
        });

    return brick;
  }

  /**
   * Calculates field according to pixel position
   *
   * @param pos Pixel position to get field for
   * @return Returns field of the board
   */
  private int getBoardField(double pos) {
    return (int) pos / GRID_SIZE;
  }
}

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FFHS group interceptor - Semesterarbeit "Dame-Brettspiel". Enjoy the game!
 *
 * @author Piotr Mazurek
 * @author Patrick Glaser
 * @author Cedric Muehlebach
 */
public class Checkers extends Application {

  static final int GRID_SIZE = 100;
  static final int GRID_COUNT = 8;
  static final Color P1_COLOR = Color.DARKGREEN;
  static final Color P2_COLOR = Color.DARKRED;

  private final Group gridGroup = new Group();
  private final Group brickGroup = new Group();

  private final Field[][] board = new Field[GRID_COUNT][GRID_COUNT];

  private boolean playerOneOnTurn = true;

  /**
   * Main method / entry point of this checkers game
   *
   * @param args Application arguments
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
          Brick brick = brickHandler(P1_COLOR, x, y, false);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        } else if (y >= 5 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(P2_COLOR, x, y, false);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        }
      }
    }

    return panel;
  }

  /**
   * Wrapper for creating bricks. This wrapper is needed to define a setOnMouseReleased event in the
   * main class.
   *
   * @param color Color of new brick
   * @param x Field position (x) of brick on game board
   * @param y Field position (y) of brick on game board
   * @return Returns created brick
   */
  private Brick brickHandler(Color color, int x, int y, boolean d) {
    Brick brick = new Brick(color, x, y, d);

    brick.setOnMouseReleased(
        e -> {
          if (isOnTurn(brick)) {
            processMove(brick);
          }
          brick.resetMove();
        });

    return brick;
  }

  /**
   * TODO
   *
   * @param brick
   */
  private void processMove(Brick brick) {
    int oX = Field.getFieldCoord(brick.getX());
    int oY = Field.getFieldCoord(brick.getY());
    int nX = Field.getFieldCoord(brick.getLayoutX());
    int nY = Field.getFieldCoord(brick.getLayoutY());
    int yDiff = nY - oY;

    Field sourceField = board[oX][oY];
    Field targetField = board[nX][nY];
    if (targetField.isDark() && targetField.isEmpty()) {

      // Move one field
      if (!playerAbleToDevour(playerOneOnTurn) && isValidStep(brick, 1, yDiff)) {

        brick.move(nX, nY);
        sourceField.setBrick(null);
        targetField.setBrick(brick);

        // Test for dame transition
        if (Brick.hasFieldBoundaryReached(brick.getMoveDir(), Field.getFieldCoord(brick.getY()))) {
          replaceBrickWithDame(brick, nX, nY);
        }
        playerOneOnTurn = !playerOneOnTurn;

        // Move two fields
      } else if (brick.isAbleToDevour(board) && isValidStep(brick, 2, yDiff)) {

        // Process move
        brick.move(nX, nY);
        sourceField.setBrick(null);
        targetField.setBrick(brick);

        // Remove devoured brick
        int bX = oX + ((nX - oX) / 2);
        int bY = oY + ((nY - oY) / 2);
        brickGroup.getChildren().remove(board[bX][bY].getBrick());
        board[bX][bY].setBrick(null);

        // Test for dame transition
        if (Brick.hasFieldBoundaryReached(brick.getMoveDir(), Field.getFieldCoord(brick.getY()))) {
          replaceBrickWithDame(brick, nX, nY);
        }

        if (!playerAbleToDevour(playerOneOnTurn)) {
          playerOneOnTurn = !playerOneOnTurn;
        }
      }
    }
  }

  /**
   * TODO
   *
   * @param b
   * @param nX
   * @param nY
   */
  private void replaceBrickWithDame(Brick b, int nX, int nY) {
    Brick dame = brickHandler(b.getColor(), nX, nY, true);
    brickGroup.getChildren().add(dame);
    board[nX][nY].setBrick(dame);
    brickGroup.getChildren().remove(b);
  }

  /**
   * TODO
   *
   * @param b
   * @param step
   * @param depth
   * @return
   */
  private boolean isValidStep(Brick b, int step, int depth) {
    return (step == b.getMoveDir() * depth) || (b.isDame() && (step == b.getMoveDir() * -depth));
  }

  /**
   * Determines if current player is able to devour an opposing brick
   *
   * @param p1 Boolean var for player switch. True means player 1 is on turn, false means player 2
   *     is on turn
   * @return Returns true if current player is able to eat an opposing brick
   */
  private boolean playerAbleToDevour(boolean p1) {

    Color player = p1 ? P1_COLOR : P2_COLOR;

    for (int x = 0; x < GRID_COUNT; x++) {
      for (int y = 0; y < GRID_COUNT; y++) {
        Brick b = board[x][y].getBrick();

        if (b != null && b.getColor().equals(player)) {
          if (b.isAbleToDevour(board)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Checks if moving brick color is on turn
   *
   * @param brick Moving brick
   * @return Returns true if moving brick is on turn
   */
  private boolean isOnTurn(Brick brick) {
    return brick.getColor().equals(P1_COLOR) && playerOneOnTurn
        || brick.getColor().equals(P2_COLOR) && !playerOneOnTurn;
  }
}

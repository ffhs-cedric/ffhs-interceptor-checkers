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
  private static final int GRID_COUNT = 8;
  static Color P1_COLOR = Color.DARKGREEN;
  static Color P2_COLOR = Color.DARKRED;

  private Group gridGroup = new Group();
  private Group brickGroup = new Group();

  private Field[][] board = new Field[GRID_COUNT][GRID_COUNT];

  private boolean playerOneOnTurn = true;

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
          Brick brick = brickHandler(P1_COLOR, x, y);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        } else if (y >= 5 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(P2_COLOR, x, y);
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
  private Brick brickHandler(Color color, int x, int y) {
    Brick brick = new Brick(color, x, y);

    brick.setOnMouseReleased(
        e -> {
          if (isOnTurn(brick)) {

            int oX = getFieldCoordinate(brick.xPos);
            int oY = getFieldCoordinate(brick.yPos);
            int nX = getFieldCoordinate(brick.getLayoutX());
            int nY = getFieldCoordinate(brick.getLayoutY());

            // Deny to move on an occupied field
            if (board[nX][nY].getBrick() == null) {

              // Move one field
              if (nY - oY == brick.moveDir && playerNotAbleToDevour(playerOneOnTurn)) {

                brick.move(nX, nY);
                board[oX][oY].setBrick(null);
                board[nX][nY].setBrick(brick);
                playerOneOnTurn = !playerOneOnTurn;

                // Move two fields
              } else if (nY - oY == brick.moveDir * 2 && brickIsAbleToDevour(brick)) {

                int bX = oX + ((nX - oX) / 2);
                int bY = oY + ((nY - oY) / 2);
                Brick brickBetween = board[bX][bY].getBrick();

                // Process move
                brick.move(nX, nY);
                board[oX][oY].setBrick(null);
                board[nX][nY].setBrick(brick);

                // Remove devoured brick
                board[bX][bY].setBrick(null);
                brickGroup.getChildren().remove(brickBetween);

                if (playerNotAbleToDevour(playerOneOnTurn)) {
                  playerOneOnTurn = !playerOneOnTurn;
                }
              }
            }
          }

          brick.resetMove();
        });

    return brick;
  }

  /**
   * Determines if current player is NOT able to devour an opposing brick
   *
   * @param p1 Boolean var for player switch. True means player 1 is on turn, false means player 2
   *     is on turn
   * @return Returns true if current player is able to eat an opposing brick
   */
  private boolean playerNotAbleToDevour(boolean p1) {

    Color player = p1 ? P1_COLOR : P2_COLOR;

    for (int x = 0; x < GRID_COUNT; x++) {
      for (int y = 0; y < GRID_COUNT; y++) {
        Brick b = board[x][y].getBrick();

        if (b != null && b.color.equals(player)) {
          if (brickIsAbleToDevour(b)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * Determines if current brick IS able to devour an opposing brick
   *
   * @param b Current brick
   * @return Returns true if brick is able to eat an opposing brick
   */
  private boolean brickIsAbleToDevour(Brick b) {

    int dir = b.moveDir;
    int x = getFieldCoordinate(b.xPos);
    int y = getFieldCoordinate(b.yPos);

    for (int i = -1; i < 2; i += 2) {
      int x1 = x + i;
      int x2 = x + i * 2;
      int y1 = y + dir;
      int y2 = y + dir * 2;

      if (x1 >= 0 && y1 >= 0 && x1 < 8 && y1 < 8) {
        if (x2 >= 0 && y2 >= 0 && x2 < 8 && y2 < 8) {
          Brick t = board[x1][y1].getBrick();
          if (t != null && !t.color.equals(b.color) && board[x2][y2].getBrick() == null) {
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
    return brick.color.equals(P1_COLOR) && playerOneOnTurn
        || brick.color.equals(P2_COLOR) && !playerOneOnTurn;
  }

  /**
   * Determines field according to pixel position
   *
   * @param pos Pixel position to get field for
   * @return Returns field of the board
   */
  private int getFieldCoordinate(double pos) {
    return (int) pos / GRID_SIZE;
  }
}

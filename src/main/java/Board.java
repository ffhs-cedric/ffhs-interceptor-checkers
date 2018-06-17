import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

class Board extends Rectangle {

  private Field[][] board;
  private Group gridGroup = new Group();
  private Group brickGroup = new Group();
  private Player playerOne;
  private Player playerTwo;

  Board() {}

  /** Defaul constructor */
  Board(Player playerOne, Player playerTwo) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;

    board = new Field[Checkers.GRID_COUNT][Checkers.GRID_COUNT];
    initBoard();
  }

  /** */
  private void initBoard() {
    for (int x = 0; x < Checkers.GRID_COUNT; x++) {
      for (int y = 0; y < Checkers.GRID_COUNT; y++) {
        Field field = new Field(x, y, (x + y) % 2 != 0);
        gridGroup.getChildren().add(field);
        board[x][y] = field;

        System.out.println("create brick");
        if (y <= 2 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(playerOne, x, y, false);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        } else if (y >= 5 && (x + y) % 2 != 0) {
          Brick brick = brickHandler(playerTwo, x, y, false);
          brickGroup.getChildren().add(brick);
          field.setBrick(brick);
        }
      }
    }
  }

  /**
   * Wrapper for creating bricks. This wrapper is needed to define a setOnMouseReleased event in the
   * main class.
   *
   * @param x Field position (x) of brick on game board
   * @param y Field position (y) of brick on game board
   * @return Returns created brick
   */
  Brick brickHandler(Player player, int x, int y, boolean d) {

    Brick brick = new Brick(player.getColor(), x, y, d);

    // if (player.getClass() == Human.class) {
    brick.setOnMouseReleased(
        e -> {
          player.getMove();
          // player.updateBrick(brick);
        });
    // }

    return brick;
  }

  /**
   * TODO
   *
   * @param dir
   * @param y
   * @return
   */
  static boolean reachedFieldBoundary(int dir, int y) {
    return y + dir < 0 || y + dir > 7;
  }

  Group getGridGroup() {
    return gridGroup;
  }

  Group getBrickGroup() {
    return brickGroup;
  }

  void addToBrickGroup(Brick brick) {
    this.brickGroup.getChildren().add(brick);
  }

  void removeFromBrickGroup(Brick brick) {
    this.brickGroup.getChildren().remove(brick);
  }

  /**
   * TODO
   *
   * @param brick
   * @return
   */
  boolean ableToDevour(Brick brick) {

    int dir = brick.getMoveDir();
    int x = getFieldCoord(brick.getX());
    int y = getFieldCoord(brick.getY());

    for (int i = -1; i < 2; i += 2) {
      int x1 = x + i;
      int x2 = x + i * 2;
      int y1 = y + dir;
      int y2 = y + dir * 2;

      if (Field.inBoardRange(x1, y1) && Field.inBoardRange(x2, y2)) {
        Brick t = board[x1][y1].getBrick();
        if (t != null
            && !t.getColor().equals(brick.getColor())
            && board[x2][y2].getBrick() == null) {
          return true;
        }
      }
      if (brick.isDame()) {
        y1 = y + dir * -1;
        y2 = y + dir * -2;

        if (Field.inBoardRange(x1, y1) && Field.inBoardRange(x2, y2)) {
          Brick t = board[x1][y1].getBrick();
          if (t != null
              && !t.getColor().equals(brick.getColor())
              && board[x2][y2].getBrick() == null) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Determines if current player is able to devour an opposing brick
   *
   * @param player Boolean var for player switch. True means player 1 is on turn, false means player
   *     2 is on turn
   * @return Returns true if current player is able to eat an opposing brick
   */
  boolean ableToDevour(Player player) {

    for (int x = 0; x < Checkers.GRID_COUNT; x++) {
      for (int y = 0; y < Checkers.GRID_COUNT; y++) {
        Brick b = getField(x, y).getBrick();

        if (b != null && b.getColor().equals(player.getColor())) {
          if (ableToDevour(b)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  Field getField(int x, int y) {
    return board[x][y];
  }

  /**
   * Determines field according to pixel position
   *
   * @param pos Pixel position to get field for
   * @return Returns field of the board
   */
  static int getFieldCoord(double pos) {
    return (int) pos / Checkers.GRID_SIZE;
  }

  /** @param f */
  void replaceBrickWithDame(Field f) {
    Brick b = f.getBrick();
    int nX = getFieldCoord(b.getLayoutX());
    int nY = getFieldCoord(b.getLayoutY());

    Brick dame = new Brick(b.getColor(), nX, nY, true);
    f.removeBrick(b);
    f.setBrick(dame);
  }

  /**
   * TODO
   *
   * @param b
   * @param step
   * @param depth
   * @return
   */
  boolean isValidStep(Brick b, int step, int depth) {
    return (step == b.getMoveDir() * depth) || (b.isDame() && (step == b.getMoveDir() * -depth));
  }
}

import javafx.scene.paint.Color;

public class Human extends Player {

  private Board board;
  private Brick brick;
  private final boolean[] lock;
  private boolean waitValue;

  /**
   * Default constructor
   *
   * @param lock
   */
  Human(boolean[] lock, Color color, boolean starts) {
    super(color);
    this.lock = lock;
    this.waitValue = starts;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's <code>run</code> method to be called in that separately
   * executing thread.
   *
   * <p>The general contract of the method <code>run</code> is that it may take any action
   * whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    while (true) {
      getMove();
    }
  }

  @Override
  void getMove() {

    synchronized (lock) {
      while (lock[0] != waitValue) {
        try {
          lock.wait();
        } catch (InterruptedException e) {

        }
        if (processMove(brick)) {
          lock[0] = !waitValue;
          lock.notifyAll();
        } else {
          brick.resetMove();
        }
      }
    }
  }

  /**
   * processes humans move on mouse dragged release event.
   *
   * @param brick Brick to move
   */
  private boolean processMove(Brick brick) {

    int oX = Board.getFieldCoord(brick.getX());
    int oY = Board.getFieldCoord(brick.getY());
    int nX = Board.getFieldCoord(brick.getLayoutX());
    int nY = Board.getFieldCoord(brick.getLayoutY());
    int yDiff = nY - oY;

    Field sourceField = board.getField(oX, oY);
    Field targetField = board.getField(nX, nY);
    if (targetField.isDark() && targetField.isEmpty()) {

      // Move one field
      if (board.ableToDevour(this) && board.isValidStep(brick, 1, yDiff)) {

        brick.move(nX, nY);
        sourceField.setBrick(null);
        targetField.setBrick(brick);

        // Test for dame transition
        if (Board.reachedFieldBoundary(brick.getMoveDir(), Board.getFieldCoord(brick.getY()))) {
          board.replaceBrickWithDame(targetField);
        }

        return true;

        // Move two fields
      } else if (board.ableToDevour(brick) && board.isValidStep(brick, 2, yDiff)) {

        // Process move
        brick.move(nX, nY);
        sourceField.setBrick(null);
        targetField.setBrick(brick);

        // Remove devoured brick
        int bX = oX + ((nX - oX) / 2);
        int bY = oY + ((nY - oY) / 2);
        Field f = board.getField(bX, bY);
        f.removeBrick(f.getBrick());

        // Test for dame transition
        if (Board.reachedFieldBoundary(brick.getMoveDir(), Board.getFieldCoord(brick.getY()))) {
          board.replaceBrickWithDame(targetField);
        }

        if (!board.ableToDevour(this)) {
          return true;
        }
      }
    }

    return false;
  }

  void updateBoard(Board board) {
    this.board = board;
  }

  void updateBrick(Brick brick) {
    this.brick = brick;
  }
}

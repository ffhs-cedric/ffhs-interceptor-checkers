public class Game {

  private Board board;
  private Runnable playerOne;
  private Runnable playerTwo;
  boolean[] alternator = new boolean[1];

  Game() {}

  /**
   * TODO
   *
   * @return
   */
  boolean isGameOver() {
    int p1 = 0;
    int p2 = 0;

    for (int x = 0; x < Checkers.GRID_COUNT; x++) {
      for (int y = 0; y < Checkers.GRID_COUNT; y++) {
        Brick b = board.getField(x, y).getBrick();
        if (b != null) {
          if (b.getColor().equals(Player.P1_COLOR)) {
            p1++;
          } else {
            p2++;
          }
        }
      }
    }

    return p1 < 1 || p2 < 1;
  }

  Board getBoard() {
    return board;
  }

  void setBoard(Board board) {
    this.board = board;
  }

  Runnable getPlayerOne() {
    return playerOne;
  }

  void setPlayerOne(Runnable playerOne) {
    this.playerOne = playerOne;
  }

  Runnable getPlayerTwo() {
    return playerTwo;
  }

  void setPlayerTwo(Runnable playerTwo) {
    this.playerTwo = playerTwo;
  }
}

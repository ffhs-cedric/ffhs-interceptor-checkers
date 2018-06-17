/** */
class Game {

  private Board board;
  private Player playerOne;
  private Player playerTwo;
  boolean[] alternator = new boolean[1];

  /** Default constructor */
  Game() {
    alternator[0] = true;
  }

  /**
   * Determines if game is over
   *
   * @return Returns true when game is over
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

  Player getPlayerOne() {
    return playerOne;
  }

  void setPlayerOne(Player playerOne) {
    this.playerOne = playerOne;
  }

  Player getPlayerTwo() {
    return playerTwo;
  }

  void setPlayerTwo(Player playerTwo) {
    this.playerTwo = playerTwo;
  }
}

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FFHS group interceptor - Semesterarbeit "Dame-Brettspiel". Enjoy the game!
 *
 * @author Piotr Mazurek
 * @author Patrick Glaser
 * @author Cedric Muehlebach
 */
public class Checkers extends Application {

  static final int GRID_SIZE = 50;
  static final int GRID_COUNT = 8;



  /**
   * Main method / entry point of this checkers game
   *
   * @param args Application arguments
   */
  public static void main(String[] args) throws InterruptedException {
    launch(args);
  }

  /**
   * Implements method of class javafx.application.Application
   *
   * @param stage Main stage of this JavaFX application
   */
  @Override
  public void start(Stage stage) {
    Game game = new Game();

    Scene scene = new Scene(initGame(game));
    stage.setTitle("FFHS-CHECKERS \u00a9interceptor");
    stage.setScene(scene);
    stage.show();

    Thread t1 = new Thread(game.getPlayerOne());
    t1.start();
    Thread t2 = new Thread(game.getPlayerTwo());
    t2.start();

    /*while (game.isGameOver()) {
      t1.interrupt();
      t2.interrupt();
    }

    System.out.println("Game Over !");
    System.exit(0);*/
  }

  /**
   * Initializes checkers game. Creates a panel, a game board, fields and bricks
   *
   * @return Returns created Pane object used by the JavaFX Scene
   */
  private Parent initGame(Game game) {

    game.setPlayerOne(new Human(game.alternator, Player.P1_COLOR));
    game.setPlayerTwo(new Human(game.alternator, Player.P2_COLOR));
    game.setBoard(new Board((Player) game.getPlayerOne(), (Player) game.getPlayerTwo()));

    Pane panel = new Pane();
    panel.setPrefSize(GRID_SIZE * GRID_COUNT, GRID_SIZE * GRID_COUNT);
    panel.getChildren().addAll(game.getBoard().getGridGroup(), game.getBoard().getBrickGroup());

    return panel;
  }
}

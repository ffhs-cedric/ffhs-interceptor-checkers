import javafx.scene.paint.Color;

public class Computer extends Player {

  private boolean[] lock;
  private final boolean waitValue = false;

  /** Default constructor */
  public Computer(Color color) {
      super(color);
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
        processMinimaxMove(); // TODO
        lock[0] = !waitValue;
        lock.notifyAll();
      }
    }
  }


  void processMinimaxMove() {
    System.out.println("Berechne CPU move..");
  }
}

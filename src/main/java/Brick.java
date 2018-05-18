import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Brick extends Pane {
    private final BrickType type;


    // TODO: create (draw) brick
    public Brick(BrickType type, int x, int y) {
        this.type = type;

        Ellipse ellipse = new Ellipse(Checkers.GRID_SIZE * 0.3135, Checkers.GRID_SIZE * 0.27);
        ellipse.setFill(Color.BLACK);

        ellipse.setStrokeWidth(Checkers.GRID_SIZE * 0.03);

        ellipse.setTranslateX((Checkers.GRID_SIZE - Checkers.GRID_SIZE * 0.3135 * 2) / 2);
        ellipse.setTranslateY(
                (Checkers.GRID_SIZE - Checkers.GRID_SIZE * 0.27 * 2) / 2
                        + Checkers.GRID_SIZE * 0.07);

        Ellipse secondEllipse = new Ellipse(Checkers.GRID_SIZE * 0.3135, Checkers.GRID_SIZE * 0.27);
        ellipse.setFill(type == BrickType.GREEN ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(Checkers.GRID_SIZE * 0.03);

        ellipse.setTranslateX((Checkers.GRID_SIZE - Checkers.GRID_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((Checkers.GRID_SIZE - Checkers.GRID_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(ellipse, secondEllipse);

    }
}

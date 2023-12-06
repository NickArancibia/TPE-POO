package frontend.model;

import backend.model.Point;
import backend.model.Square;
import javafx.scene.paint.Color;

public class DrawableSquare extends DrawableRectangle {
    public DrawableSquare(Point topLeft, double size, Color color) {
        super(new Square(topLeft, size, color.toString()));
    }
}

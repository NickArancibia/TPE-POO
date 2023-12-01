package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableSquare extends DrawableRectangle {
    public DrawableSquare(Point topLeft, double size, Color color) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size), color);
    }
}

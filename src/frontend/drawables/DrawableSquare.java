package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableSquare extends DrawableRectangle {
    public DrawableSquare(Point topLeft, double size, Color color) {
        super(topLeft, size, size, color);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }
}

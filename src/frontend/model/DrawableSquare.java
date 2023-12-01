package frontend.model;

import backend.model.Point;

public class DrawableSquare extends DrawableRectangle {
    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

}

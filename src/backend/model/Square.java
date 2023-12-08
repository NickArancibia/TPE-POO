package backend.model;

import backend.DrawManager;

public class Square extends Rectangle {
    public Square(Point topLeft, double size, String colorAsHex, DrawManager<Rectangle> drawManager) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size), colorAsHex, drawManager);
    } 

    public Square(Point from, Point to, String colorAsHex, DrawManager<Rectangle> drawManager) {
        this(from, Math.abs(to.getX() - from.getX()), colorAsHex, drawManager);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }
}

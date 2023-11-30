package backend.model;

public interface Figure {
    boolean pointInFigure(Point p);
    void move(double deltaX, double deltaY);
}

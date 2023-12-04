package backend.model;

public interface Figure {
    boolean pointInFigure(Point p);
    void move(double deltaX, double deltaY);
    boolean isFigureInRectangle(Point topLeft, Point bottomRight);
    void scaleUp();
    void scaleDown();
    void flipV();
    void flipH();
    void rotate();
}

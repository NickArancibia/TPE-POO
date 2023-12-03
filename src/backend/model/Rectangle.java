package backend.model;

public class Rectangle implements Figure {
    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean pointInFigure(Point p) {
        return p.getX() > topLeft.getX() && p.getX() < bottomRight.getX() &&
			   p.getY() > topLeft.getY() && p.getY() < bottomRight.getY();
    }

    @Override
    public void move(double deltaX, double deltaY) {
        topLeft.move(deltaX, deltaY);
        bottomRight.move(deltaX, deltaY);
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        return this.topLeft.isPointInRectangle(topLeft, bottomRight) && this.bottomRight.isPointInRectangle(topLeft, bottomRight);
    }
}

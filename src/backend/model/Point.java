package backend.model;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public boolean isPointInRectangle(Point startPoint, Point endPoint){
        return x >= startPoint.x && x <= endPoint.x && y <= startPoint.y && y >= endPoint.y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }
}

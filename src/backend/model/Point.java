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

    public boolean isPointInRectangle(Point topLeft, Point bottomRight){
        return x >= topLeft.x && x <= bottomRight.x && y >= topLeft.y && y <= bottomRight.y;
    }

    public double distance(Point otherPoint){
        double diffX = x - otherPoint.x;
        double diffY = y - otherPoint.y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }
}

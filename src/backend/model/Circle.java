package backend.model;

public class Circle extends Ellipse {
    public Circle(Point centerPoint, double radius, String colorAsHex) {
        super(centerPoint, radius * 2, radius * 2, colorAsHex);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getsMayorAxis() / 2;
    }
}

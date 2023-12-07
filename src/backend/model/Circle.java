package backend.model;

import backend.DrawManager;

public class Circle extends Ellipse {
    public Circle(Point centerPoint, double radius, String colorAsHex, DrawManager<Ellipse> drawManager) {
        super(centerPoint, radius, radius, colorAsHex, drawManager);
    }

    public double getRadius(){
        return getsMayorAxis() / 2;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }
}

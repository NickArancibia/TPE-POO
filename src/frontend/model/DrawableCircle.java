package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {
    public DrawableCircle(Point centerPoint, double radius, Color color) {
        super(centerPoint, radius, color);
    }

    public double getRadius(){
        return getsMayorAxis() / 2;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }
}

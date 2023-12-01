package frontend.model;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {
    public DrawableCircle(Point centerPoint, double radius, Color color) {
        super(new Circle(centerPoint, radius), color);
    }
}

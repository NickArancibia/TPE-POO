package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawableEllipse {
    public DrawableCircle(Point centerPoint, double radius, Color color) {
        super(centerPoint, radius * 2, radius * 2, color);
    }
}

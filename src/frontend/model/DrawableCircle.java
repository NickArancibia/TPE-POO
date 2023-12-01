package frontend.model;

import backend.model.Point;

public class DrawableCircle extends DrawableEllipse {
    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius * 2, radius * 2);
    }

}

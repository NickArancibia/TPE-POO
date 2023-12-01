package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableEllipse extends Ellipse implements DrawableFigure {
    private DrawableState drawableState = new DrawableState();

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color) {
        super(centerPoint, sMayorAxis, sMinorAxis);
        drawableState.setColor(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(drawableState.getColor());
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }
}

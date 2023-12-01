package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableEllipse extends Ellipse implements DrawableFigure {
    private Color color;

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color) {
        super(centerPoint, sMayorAxis, sMinorAxis);
        setColor(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}

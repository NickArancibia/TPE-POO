package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends Ellipse implements Drawable {

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color) {
        super(centerPoint, sMayorAxis, sMinorAxis, color.toString());
    }

    public DrawableEllipse(Point centerPoint, double radius, Color color) {
        super(centerPoint, radius, radius, color.toString());
    }

    private Color getColor(){
        return Color.web(getColorAsHex());
    }

    private void drawShadow(GraphicsContext gc) {
        if (isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2) + 10.0, getCenterPoint().getY() - (getsMinorAxis() / 2) + 10.0, getsMayorAxis(), getsMinorAxis());
        }
    }

    private void drawGradient(GraphicsContext gc) {
        if (isGradientToggled()) {
            RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, getColor()),
                    new Stop(1, getColor().invert()));
            gc.setFill(radialGradient);
            return;
        } 

        gc.setFill(getColor());
    }

    private void drawBevel(GraphicsContext gc) {
        if (isBevelToggled()) {
            double arcX = getCenterPoint().getX() - (getsMayorAxis() / 2);
            double arcY = getCenterPoint().getY() - (getsMinorAxis() / 2);
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, getsMayorAxis(), getsMinorAxis(), 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, getsMayorAxis(), getsMinorAxis(), 225, 180, ArcType.OPEN);
            gc.setLineWidth(1);
        }
    }

    public void drawFigure(GraphicsContext gc) {
        drawShadow(gc);
        drawGradient(gc);

        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());

        drawBevel(gc);
    }
}

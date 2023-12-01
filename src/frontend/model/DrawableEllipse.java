package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends Ellipse implements DrawableFigure {
    private DrawableState drawableState = new DrawableState();

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color) {
        super(centerPoint, sMayorAxis, sMinorAxis);
        drawableState.setColor(color);
    }

    private void handleShadow(GraphicsContext gc) {
        if (drawableState.isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2) + 10.0, getCenterPoint().getY() - (getsMinorAxis() / 2) + 10.0, getsMayorAxis(), getsMinorAxis());
        }
    }

    private void handleGradient(GraphicsContext gc) {
        if (drawableState.isGradientToggled()) {
            RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, drawableState.getColor()),
                    new Stop(1, drawableState.getColor().invert()));
            gc.setFill(radialGradient);
        }
    }

    private void handleBevel(GraphicsContext gc) {
        if (drawableState.isBevelToggled()) {
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

    @Override
    public void draw(GraphicsContext gc) {
        handleShadow(gc);
        handleGradient(gc);
        if (!drawableState.isGradientToggled()) gc.setFill(drawableState.getColor());

        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());

        handleBevel(gc);
    }

    @Override
    public DrawableState getDrawableState() {
        return drawableState;
    }
}

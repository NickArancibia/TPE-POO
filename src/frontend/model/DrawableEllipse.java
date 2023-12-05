package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends DrawableFigure<Ellipse> {
    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color) {
        super(new Ellipse(centerPoint, sMayorAxis, sMinorAxis), color);
    }

    public DrawableEllipse(Ellipse ellipse, Color color) {
        super(ellipse, color);
    }

    private void drawShadow(GraphicsContext gc) {
        if (isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillOval(baseFigure.getCenterPoint().getX() - (baseFigure.getsMayorAxis() / 2) + 10.0, baseFigure.getCenterPoint().getY() - (baseFigure.getsMinorAxis() / 2) + 10.0, baseFigure.getsMayorAxis(), baseFigure.getsMinorAxis());
        }
    }

    private void drawGradient(GraphicsContext gc) {
        if (isGradientToggled()) {
            RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, color),
                    new Stop(1, color.invert()));
            gc.setFill(radialGradient);
            return;
        } 

        gc.setFill(color);
    }

    private void drawBevel(GraphicsContext gc) {
        if (isBevelToggled()) {
            double arcX = baseFigure.getCenterPoint().getX() - (baseFigure.getsMayorAxis() / 2);
            double arcY = baseFigure.getCenterPoint().getY() - (baseFigure.getsMinorAxis() / 2);
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, baseFigure.getsMayorAxis(), baseFigure.getsMinorAxis(), 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, baseFigure.getsMayorAxis(), baseFigure.getsMinorAxis(), 225, 180, ArcType.OPEN);
            gc.setLineWidth(1);
        }
    }

    @Override
    protected void drawFigure(GraphicsContext gc) {
        drawShadow(gc);
        drawGradient(gc);

        gc.strokeOval(baseFigure.getCenterPoint().getX() - (baseFigure.getsMayorAxis() / 2), baseFigure.getCenterPoint().getY() - (baseFigure.getsMinorAxis() / 2), baseFigure.getsMayorAxis(), baseFigure.getsMinorAxis());
        gc.fillOval(baseFigure.getCenterPoint().getX() - (baseFigure.getsMayorAxis() / 2), baseFigure.getCenterPoint().getY() - (baseFigure.getsMinorAxis() / 2), baseFigure.getsMayorAxis(), baseFigure.getsMinorAxis());

        drawBevel(gc);
    }
}

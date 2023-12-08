package frontend.drawManagers;

import backend.DrawManager;
import backend.model.Ellipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class EllipseDrawManager implements DrawManager<Ellipse> {
    private final GraphicsContext gc;
    
    public EllipseDrawManager(GraphicsContext gc) {
        this.gc = gc;
    }

    private Color getColor(Ellipse figure) {
        return Color.valueOf(figure.getColorAsHex());
    }

    @Override
    public void drawShadow(Ellipse figure) {
        if (figure.isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillOval(figure.getCenterPoint().getX() - (figure.getsMayorAxis() / 2) + 10.0,
                    figure.getCenterPoint().getY() - (figure.getsMinorAxis() / 2) + 10.0, figure.getsMayorAxis(), figure.getsMinorAxis());
        }
    }

    @Override
    public void drawGradient(Ellipse figure) {
        if (figure.isGradientToggled()) {
            RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, getColor(figure)),
                    new Stop(1, getColor(figure).invert()));
            gc.setFill(radialGradient);
            return;
        }

        gc.setFill(getColor(figure));
    }

    @Override
    public void drawBevel(Ellipse figure) {
        if (figure.isBevelToggled()) {
            double arcX = figure.getCenterPoint().getX() - (figure.getsMayorAxis() / 2);
            double arcY = figure.getCenterPoint().getY() - (figure.getsMinorAxis() / 2);
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, figure.getsMayorAxis(), figure.getsMinorAxis(), 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, figure.getsMayorAxis(), figure.getsMinorAxis(), 225, 180, ArcType.OPEN);
            gc.setLineWidth(1);
        }
    }

    @Override
    public void drawShape(Ellipse figure, boolean selected) {
        gc.fillOval(figure.getCenterPoint().getX() - (figure.getsMayorAxis() / 2), figure.getCenterPoint().getY() - (figure.getsMinorAxis() / 2), figure.getsMayorAxis(), figure.getsMinorAxis());
        gc.setStroke(selected ? Color.RED : Color.BLACK);
        gc.strokeOval(figure.getCenterPoint().getX() - (figure.getsMayorAxis() / 2), figure.getCenterPoint().getY() - (figure.getsMinorAxis() / 2), figure.getsMayorAxis(), figure.getsMinorAxis());
    }
}

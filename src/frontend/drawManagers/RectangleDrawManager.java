package frontend.drawManagers;

import backend.DrawManager;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class RectangleDrawManager implements DrawManager<Rectangle> {
    private final GraphicsContext gc;
    
    public RectangleDrawManager(GraphicsContext gc) {
        this.gc = gc;
    }

    private Color getColor(Rectangle figure) {
        return Color.valueOf(figure.getColorAsHex());
    }

    @Override
    public void drawShadow(Rectangle figure) {
        if (figure.isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillRect(figure.getTopLeft().getX() + 10.0,
                    figure.getTopLeft().getY() + 10.0,
                    Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()),
                    Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
        }
    }

    @Override
    public void drawGradient(Rectangle figure) {
        if (figure.isGradientToggled()) {
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, getColor(figure)),
                    new Stop(1, getColor(figure).invert()));
            gc.setFill(linearGradient);
            return;
        }

        gc.setFill(getColor(figure));
    }

    @Override
    public void drawBevel(Rectangle figure) {
        if (figure.isBevelToggled()) {
            double x = figure.getTopLeft().getX();
            double y = figure.getTopLeft().getY();
            double width = Math.abs(x - figure.getBottomRight().getX());
            double height = Math.abs(y - figure.getBottomRight().getY());
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(x, y, x + width, y);
            gc.strokeLine(x, y, x, y + height);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x + width, y, x + width, y + height);
            gc.strokeLine(x, y + height, x + width, y + height);
            gc.setLineWidth(1);
        }
    }

    @Override
    public void drawShape(Rectangle figure) {
        gc.fillRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
        gc.strokeRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
    }

    @Override
    public void drawBorder(Rectangle figure, boolean selected) {
        gc.setStroke(selected ? Color.RED : Color.BLACK);
        gc.strokeRect(figure.getTopLeft().getX(), figure.getTopLeft().getY(),
                Math.abs(figure.getTopLeft().getX() - figure.getBottomRight().getX()), Math.abs(figure.getTopLeft().getY() - figure.getBottomRight().getY()));
    }
}
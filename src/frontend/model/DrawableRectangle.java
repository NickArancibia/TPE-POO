package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DrawableRectangle extends DrawableFigure<Rectangle> {
    public DrawableRectangle(Point topLeft, Point bottomRight, Color color) {
        super(new Rectangle(topLeft, bottomRight), color);
    }

    public DrawableRectangle(Rectangle rectangle, Color color) {
        super(rectangle, color);
    }

    private void drawShadow(GraphicsContext gc) {
        if (isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillRect(baseFigure.getTopLeft().getX() + 10.0,
                    baseFigure.getTopLeft().getY() + 10.0,
                    Math.abs(baseFigure.getTopLeft().getX() - baseFigure.getBottomRight().getX()),
                    Math.abs(baseFigure.getTopLeft().getY() - baseFigure.getBottomRight().getY()));
        }
    }

    private void drawGradient(GraphicsContext gc) {
        if (isGradientToggled()) {
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, color),
                    new Stop(1, color.invert()));
            gc.setFill(linearGradient);
            return;
        }

        gc.setFill(color);
    }

    private void drawBevel(GraphicsContext gc) {
        if (isBevelToggled()) {
            double x = baseFigure.getTopLeft().getX();
            double y = baseFigure.getTopLeft().getY();
            double width = Math.abs(x - baseFigure.getBottomRight().getX());
            double height = Math.abs(y - baseFigure.getBottomRight().getY());
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
    protected void drawFigure(GraphicsContext gc) {
        drawShadow(gc);
        drawGradient(gc);

        gc.fillRect(baseFigure.getTopLeft().getX(), baseFigure.getTopLeft().getY(),
                Math.abs(baseFigure.getTopLeft().getX() - baseFigure.getBottomRight().getX()), Math.abs(baseFigure.getTopLeft().getY() - baseFigure.getBottomRight().getY()));
        gc.strokeRect(baseFigure.getTopLeft().getX(), baseFigure.getTopLeft().getY(),
                Math.abs(baseFigure.getTopLeft().getX() - baseFigure.getBottomRight().getX()), Math.abs(baseFigure.getTopLeft().getY() - baseFigure.getBottomRight().getY()));

        drawBevel(gc);
    }
}

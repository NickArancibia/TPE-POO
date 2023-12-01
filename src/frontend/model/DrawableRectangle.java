package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends Rectangle implements DrawableFigure {
    private DrawableState drawableState = new DrawableState();

    public DrawableRectangle(Point topLeft, Point bottomRight, Color color) {
        super(topLeft, bottomRight);
        drawableState.setColor(color);
    }

    private void handleShadow(GraphicsContext gc) {
        if (drawableState.isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillRect(rectangle.getTopLeft().getX() + 10.0,
                    rectangle.getTopLeft().getY() + 10.0,
                    Math.abs(getTopLeft().getX() - getBottomRight().getX()),
                    Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        }
    }

    private void handleGradient(GraphicsContext gc) {
        if (drawableState.isGradientToggled()) {
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, drawableState.getColor()),
                    new Stop(1, drawableState.getColor().invert()));
            gc.setFill(linearGradient);
        }
    }

    private void handleBevel(GraphicsContext gc) {
        if (drawableState.isBevelToggled()) {
            double x = getTopLeft().getX();
            double y = getTopLeft().getY();
            double width = Math.abs(x - getBottomRight().getX());
            double height = Math.abs(y - getBottomRight().getY());
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(x, y, x + width, y);
            gc.strokeLine(x, y, x, y + height);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x + width, y, x + width, y + height);
            gc.strokeLine(x, y + height, x + width, y + height);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        handleShadow();
        handleGradient();
        if (!drawableState.isGradientToggled()) gc.setFill(drawableState.getColor());

        gc.fillRect(getTopLeft().getX(),getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));

        handleBevel();
    }

    @Override
    public DrawableState getDrawableState() {
        return drawableState;
    }
}

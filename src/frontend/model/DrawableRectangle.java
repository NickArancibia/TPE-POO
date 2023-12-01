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

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(drawableState.getColor());
        gc.fillRect(getTopLeft().getX(),getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}

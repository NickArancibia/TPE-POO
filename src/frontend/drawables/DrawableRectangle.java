package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DrawableRectangle extends Rectangle implements Drawable {
    
    public DrawableRectangle(Point topLeft, Point bottomRight, Color color) {
        super(topLeft, bottomRight, color.toString());
    }

    public DrawableRectangle(Point topLeft, double width, double height, Color color) {
        super(topLeft, width, height, color.toString());
    }
    
    private Color getColor(){
        return Color.web(getColorAsHex());
    }
    
    private void drawShadow(GraphicsContext gc) {
        if (isShadowToggled()) {
            gc.setFill(Color.GRAY);
            gc.fillRect(getTopLeft().getX() + 10.0,
                    getTopLeft().getY() + 10.0,
                    Math.abs(getTopLeft().getX() - getBottomRight().getX()),
                    Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        }
    }

    private void drawGradient(GraphicsContext gc) {
        if (isGradientToggled()) {
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, getColor()),
                    new Stop(1, getColor().invert()));
            gc.setFill(linearGradient);
            return;
        }

        gc.setFill(getColor());
    }

    private void drawBevel(GraphicsContext gc) {
        if (isBevelToggled()) {
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
            gc.setLineWidth(1);
        }
    }

    @Override
    public void drawFigure(GraphicsContext gc) {
        drawShadow(gc);
        drawGradient(gc);

        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));

        drawBevel(gc);
    }
}

package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawableFigure<T extends Figure> implements Figure {
    protected final T baseFigure;
    protected Color color;

    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    DrawableFigure(T baseFigure, Color color) {
        this.baseFigure = baseFigure;
        this.color = color;
    }

    public abstract void draw(GraphicsContext gc);

    public void setGradientToggled(boolean toggle) {
        gradientToggled = toggle;
    }

    public boolean isGradientToggled() {
        return gradientToggled;
    }

    public void setShadowToggled(boolean toggle) {
        shadowToggled = toggle;
    }

    public boolean isShadowToggled() {
        return shadowToggled;
    }

    public void setBevelToggled(boolean toggle) {
        bevelToggled = toggle;
    }

    public boolean isBevelToggled() {
        return bevelToggled;
    }

    @Override
    public boolean pointInFigure(Point p) {
        return baseFigure.pointInFigure(p);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        baseFigure.move(deltaX, deltaY);
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight) {
        return baseFigure.isFigureInRectangle(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return baseFigure.toString();
    }
}

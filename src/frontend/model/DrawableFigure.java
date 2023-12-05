package frontend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    private Set<String> tags = new HashSet<>();

    DrawableFigure(T baseFigure, Color color) {
        this.baseFigure = baseFigure;
        this.color = color;
    }

    protected abstract void handleShadow(GraphicsContext gc);
    protected abstract void handleGradient(GraphicsContext gc);
    protected abstract void handleShape(GraphicsContext gc);
    protected abstract void handleBevel(GraphicsContext gc);

    public boolean isFigureVisible(boolean isFiltering, String filterTag){
        return !isFiltering || tags.contains(filterTag);
    }

    public void draw(GraphicsContext gc){
        handleShadow(gc);
        handleGradient(gc);
        handleShape(gc);
        handleBevel(gc);
    }

    public Color getColor() {
        return color;
    }

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

    public void setTags(Collection<String> tags){
        this.tags = new HashSet<String>(tags);
    }

    public Collection<String> getTags(){
        return tags;
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
    public void scaleUp() {
        baseFigure.scaleUp();
    }

    @Override
    public void scaleDown() {
        baseFigure.scaleDown();
    }

    @Override
    public void rotate() {
        baseFigure.rotate();
    }

    @Override
    public void flipV() {
        baseFigure.flipV();
    }

    @Override
    public void flipH() {
        baseFigure.flipH();
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

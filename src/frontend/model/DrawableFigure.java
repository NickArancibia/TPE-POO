package frontend.model;

import java.util.Collection;
import java.util.Set;

import backend.model.Figure;
import backend.model.Point;
import frontend.TagFilterPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawableFigure<T extends Figure> extends Figure {
    protected final T baseFigure;
    
    DrawableFigure(T baseFigure) {
        super(baseFigure.getColorAsHex());
        this.baseFigure = baseFigure;
    }

    protected abstract void drawFigure(GraphicsContext gc);

    public boolean isFigureVisible(TagFilterPane tagFilterPane){
        return !tagFilterPane.isFiltering() || getTags().contains(tagFilterPane.getFilterTag());
    }

    public void draw(GraphicsContext gc, TagFilterPane tagFilterPane){
        if(isFigureVisible(tagFilterPane)){
            drawFigure(gc);
        }
    }

    public Color getColor() {
        return Color.valueOf(getColorAsHex());
    }

    @Override
    public boolean isShadowToggled() {
        return baseFigure.isShadowToggled();
    }

    @Override
    public void setShadowToggled(boolean toggle) {
        baseFigure.setShadowToggled(toggle);
    }

    @Override
    public boolean isGradientToggled() {
        return baseFigure.isGradientToggled();
    }

    @Override
    public void setGradientToggled(boolean toggle) {
        baseFigure.setGradientToggled(toggle);
    }

     @Override
    public boolean isBevelToggled() {
        return baseFigure.isBevelToggled();
    }

    @Override
    public void setBevelToggled(boolean toggle) {
        baseFigure.setBevelToggled(toggle);
    }

    @Override
    public Set<String> getTags() {
        return baseFigure.getTags();
    }

    @Override
    public void setTags(Collection<String> tags) {
        baseFigure.setTags(tags);
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

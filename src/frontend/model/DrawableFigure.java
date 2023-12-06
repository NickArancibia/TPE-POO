package frontend.model;

import java.util.Set;

import backend.model.Figure;
import backend.model.Point;
import frontend.TagFilterPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawableFigure<T extends Figure> extends Figure {
    protected final T baseFigure;
    protected Color color;

    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    DrawableFigure(T baseFigure, Color color) {
        this.baseFigure = baseFigure;
        this.color = color;
    }

    protected abstract void drawFigure(GraphicsContext gc);

    public boolean hasTag(String tag){
        return baseFigure.hasTag(tag);
    }

    public boolean isFigureVisible(TagFilterPane tagFilterPane){
        return tagFilterPane.getShowAllButton().isSelected() || hasTag(tagFilterPane.getFilterTag());
    }

    public void draw(GraphicsContext gc, TagFilterPane tagFilterPane){
        if(isFigureVisible(tagFilterPane)){
            drawFigure(gc);
        }
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

    public void setTags(Set<String> tags){
        baseFigure.setTags(tags);
    }

    public Set<String> getTags(){
        return baseFigure.getTags();
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

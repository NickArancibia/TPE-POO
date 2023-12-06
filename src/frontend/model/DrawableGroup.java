package frontend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import backend.model.Figure;
import backend.model.FigureGroup;
import frontend.TagFilterPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup extends DrawableFigure<FigureGroup<DrawableFigure<? extends Figure>>> {
    public DrawableGroup(Color color) {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(), color);
    }

    public DrawableGroup() {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(), Color.YELLOW);
    }

    public DrawableGroup(DrawableFigure<? extends Figure> figure) {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(), figure.getColor());
        add(figure);
    }

    public void add(DrawableFigure<? extends Figure> figure){
        baseFigure.add(figure);
        getTags().addAll(figure.getTags());
    }

    public void addAll(DrawableGroup group){
        baseFigure.addAll(group.baseFigure);
        getTags().addAll(group.getTags());
    }

    public void addAll(Collection<DrawableGroup> groups){
        for (DrawableGroup group : groups)
            addAll(group);
    }

    private List<DrawableFigure<? extends Figure>> getFigures(){
        return baseFigure.getGroup();
    }

    public Collection<DrawableGroup> ungroup() {
        List<DrawableGroup> out = new ArrayList<>();

        for(DrawableFigure<? extends Figure> figure : getFigures()) 
            out.add(new DrawableGroup(figure));

        return out;
    }

    public int size(){
        return baseFigure.size();
    }

    private void applyConsumer(Consumer<DrawableFigure<? extends Figure>> consumer) {
        for(DrawableFigure<? extends Figure> figure : getFigures())
            consumer.accept(figure);
    }

    @Override
    protected void drawFigure(GraphicsContext gc) {
        applyConsumer((figure) -> figure.drawFigure(gc));
    }

    @Override
    public void setGradientToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setGradientToggled(toggle));
    }

    @Override
    public void setShadowToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setShadowToggled(toggle));
    }

    @Override
    public void setBevelToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setBevelToggled(toggle));
    }

    private boolean isToggled(Function<DrawableFigure<? extends Figure>, Boolean> toggled) {
        for(DrawableFigure<? extends Figure> figure : getFigures())
            if (!toggled.apply(figure)) return false;

        return true;
    }

    private boolean someToggled(Function<DrawableFigure<? extends Figure>, Boolean> toggled) {
        int count = 0;
        for(DrawableFigure<? extends Figure> figure : getFigures())
            if (toggled.apply(figure)) count++;

        return count != 0 && count != baseFigure.size();
    }

    @Override
    public boolean isShadowToggled() {
        return isToggled((figure) -> figure.isShadowToggled());
    }

    public boolean someShadowToggled() {
        return someToggled((figure) -> figure.isShadowToggled());
    }

    @Override
    public boolean isGradientToggled() {
        return isToggled((figure) -> figure.isGradientToggled());
    }

    public boolean someGradientToggled() {
        return someToggled((figure) -> figure.isGradientToggled());
    }

    @Override
    public boolean isBevelToggled() {
        return isToggled((figure) -> figure.isBevelToggled());
    }

    public boolean someBevelToggled() {
        return someToggled((figure) -> figure.isBevelToggled());
    }

    public void setTags(Set<String> tags){
        super.setTags(tags);
        for(DrawableFigure<? extends Figure> figure : getFigures())
            figure.setTags(tags);
    }

    @Override
    public boolean isFigureVisible(TagFilterPane tagFilterPane){
        for(DrawableFigure<? extends Figure> figure : getFigures())
            if (figure.isFigureVisible(tagFilterPane)) return true;
        return false;
    }
}

package frontend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import backend.model.Figure;
import backend.model.FigureGroup;
import frontend.TagFilterPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup extends DrawableFigure<FigureGroup<DrawableFigure<? extends Figure>>> {
    public DrawableGroup(Color color) {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(color.toString()));
    }

    public DrawableGroup() {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(Color.YELLOW.toString()));
    }

    public DrawableGroup(DrawableFigure<? extends Figure> figure) {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(figure.getColorAsHex()));
        add(figure);
    }

    public void add(DrawableFigure<? extends Figure> figure){
        baseFigure.add(figure);
    }

    public void addAll(DrawableGroup group){
        baseFigure.addAll(group.baseFigure);
    }

    public void addAll(Collection<DrawableGroup> groups){
        for (DrawableGroup group : groups)
            addAll(group);
    }

    private List<DrawableFigure<? extends Figure>> getFigures(){
        return baseFigure.getFigures();
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

    public boolean someShadowToggled() {
        return baseFigure.someShadowToggled();
    }

    public boolean someGradientToggled() {
        return baseFigure.someGradientToggled();
    }

    public boolean someBevelToggled() {
        return baseFigure.someBevelToggled();
    }

    @Override
    public boolean isFigureVisible(TagFilterPane tagFilterPane){
        for(DrawableFigure<? extends Figure> figure : getFigures())
            if (figure.isFigureVisible(tagFilterPane)) return true;
        return false;
    }
}

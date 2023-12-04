package frontend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backend.model.Figure;
import backend.model.FigureGroup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup extends DrawableFigure<FigureGroup<DrawableFigure<? extends Figure>>>{
    public DrawableGroup() {
        super(new FigureGroup<DrawableFigure<? extends Figure>>(), Color.YELLOW);
    }

    public DrawableGroup(Color color){
         super(new FigureGroup<DrawableFigure<? extends Figure>>(), color);
    }

    private void updateProperties(DrawableFigure<? extends Figure> figure) {
        setShadowToggled(isShadowToggled() | figure.isShadowToggled());
        setGradientToggled(isGradientToggled() | figure.isGradientToggled());
        setBevelToggled(isBevelToggled() | figure.isBevelToggled());
    }

    public void add(DrawableFigure<? extends Figure> figure){
        baseFigure.add(figure);
        updateProperties(figure);
    }

    public void addAll(DrawableGroup group){
        baseFigure.addAll(group.baseFigure);
        updateProperties(group);
    }

    public void addAll(Collection<DrawableGroup> groups){
        for (DrawableGroup group : groups)
            addAll(group);
    }

    public FigureGroup<DrawableFigure<? extends Figure>> getFigures(){
        return baseFigure;
    }

    public Collection<DrawableGroup> ungroup() {
        List<DrawableGroup> out = new ArrayList<>();

        for(DrawableFigure<? extends Figure> figure : getFigures()) {
            DrawableGroup group = new DrawableGroup();
            group.add(figure);
            out.add(group);
        }

        return out;
    }

    public int size(){
        return baseFigure.size();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for(DrawableFigure<? extends Figure> figure : baseFigure){
            figure.draw(gc);
        }
    }
    @Override
    public void setGradientToggled(boolean toggle) {
        for(DrawableFigure<? extends Figure> figure : baseFigure){
            figure.setGradientToggled(toggle);
        }
        super.setGradientToggled(toggle);
    }

    @Override
    public void setShadowToggled(boolean toggle) {
        for(DrawableFigure<? extends Figure> figure : baseFigure){
            figure.setShadowToggled(toggle);
        }
        super.setShadowToggled(toggle);
    }

    @Override
    public void setBevelToggled(boolean toggle) {

        for(DrawableFigure<? extends Figure> figure : baseFigure){
            figure.setBevelToggled(toggle);
        }
        super.setBevelToggled(toggle);
    }
}

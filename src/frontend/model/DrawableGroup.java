package frontend.model;

import backend.model.Figure;
import backend.model.FigureGroup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup extends DrawableFigure<FigureGroup<DrawableFigure<? extends Figure>>>{

    public DrawableGroup(Color color){
         super(new FigureGroup<DrawableFigure<? extends Figure>>(), color);
    }

    public void add(DrawableFigure<? extends Figure> figure){
        baseFigure.add(figure);
    }

    public void addAll(DrawableGroup group){
        baseFigure.addAll(group.baseFigure);
    }

    public FigureGroup<DrawableFigure<? extends Figure>> getFigures(){
        return baseFigure;
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

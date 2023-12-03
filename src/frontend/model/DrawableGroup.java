package frontend.model;

import backend.model.Figure;
import backend.model.FigureGroup;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup extends DrawableFigure<FigureGroup<DrawableFigure<? extends Figure>>>{

    public DrawableGroup(Color color){
         super(new FigureGroup<DrawableFigure<? extends Figure>>(), color);
    }

    public void add(DrawableFigure<Figure> figure){
        baseFigure.add(figure);
    }

    public void remove(DrawableFigure<? extends Figure> figure){
        baseFigure.remove(figure);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for(DrawableFigure<? extends Figure> figure : baseFigure.getFigures()){
            figure.draw(gc);
        }
    }
    @Override
    public void setGradientToggled(boolean toggle) {
        for(DrawableFigure<? extends Figure> figure : baseFigure.getFigures()){
            figure.setGradientToggled(toggle);
        }
        super.setGradientToggled(toggle);
    }

    @Override
    public void setShadowToggled(boolean toggle) {

        for(DrawableFigure<? extends Figure> figure : baseFigure.getFigures()){
            figure.setShadowToggled(toggle);
        }
        super.setShadowToggled(toggle);
    }

    @Override
    public void setBevelToggled(boolean toggle) {

        for(DrawableFigure<? extends Figure> figure : baseFigure.getFigures()){
            figure.setBevelToggled(toggle);
        }
        super.setBevelToggled(toggle);
    }
}

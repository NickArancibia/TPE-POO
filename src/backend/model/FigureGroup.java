package backend.model;

import java.util.ArrayList;
import java.util.List;

public class FigureGroup<T extends Figure> extends ArrayList<T> implements Figure{
    
    public FigureGroup(){
        super();
    }

    public List<T> getFigures(){
        return new ArrayList<>(this);
    }

    @Override
    public boolean pointInFigure(Point p){
        for(T figure : this){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    @Override
    public void move(double deltaX, double deltaY){
        for(T figure : this){
            figure.move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : this){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }

    @Override
    public void scaleGrowth() {
        for(Figure figure : this){
            figure.scaleGrowth();
        }
    }

    @Override
    public void scaleReduce() {
        for(Figure figure : this){
            figure.scaleReduce();
        }
    }

    @Override
    public void turnAroundH() {
        for(Figure figure : this){
            figure.turnAroundH();
        }
    }

    @Override
    public void turnAroundV() {
        for(Figure figure : this){
            figure.turnAroundV();
        }
    }

    @Override
    public void spin() {
        for(Figure figure : this){
            figure.spin();
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(T figure : this) {
            s.append(figure.toString());
        }
        return s.toString();
    }
}

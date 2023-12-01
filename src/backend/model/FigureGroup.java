package backend.model;

import java.util.ArrayList;
import java.util.List;

public class FigureGroup<T extends Figure> implements Figure{
    private final List<T> figureList = new ArrayList<>();

    public FigureGroup(){

    }

    public void addFigure(T figure){
        figureList.add(figure);
    }

    public boolean containsFigure(T figure){
        return figureList.contains(figure);
    }

    public void removeFigure(T figure){
        figureList.remove(figure);
    }

    public List<T> getFigures(){
        return new ArrayList<>(figureList);
    }

    @Override
    public boolean pointInFigure(Point p){
        for(Figure figure : figureList){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    @Override
    public void move(double deltaX, double deltaY){
        for(Figure figure : figureList){
            figure.move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : figureList){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(T figure : figureList)
            s.append(figure.toString());
        return s.toString();
    }
}

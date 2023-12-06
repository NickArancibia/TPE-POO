package backend.model;

import java.util.ArrayList;
import java.util.List;

public class FigureGroup<T extends Figure> extends Figure{

    private List<T> group = new ArrayList<>();

    public void add(T figure){
        group.add(figure);
    }

    public void addAll(Iterable<T> figures){
        for(T figure : figures){
            add(figure);
        }
    }

    public void addAll(FigureGroup<T> figureGroup){
        addAll(figureGroup.group);
    }

    public int size(){
        return group.size();
    }

    public List<T> getGroup(){
        return new ArrayList<>(group);
    }

    @Override
    public boolean pointInFigure(Point p){
        for(T figure : group){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    @Override
    public void move(double deltaX, double deltaY){
        for(T figure : group){
            figure.move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : group){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }

    @Override
    public void scaleUp() {
        for(Figure figure : group){
            figure.scaleUp();
        }
    }

    @Override
    public void scaleDown() {
        for(Figure figure : group){
            figure.scaleDown();
        }
    }

    @Override
    public void flipH() {
        for(Figure figure : group){
            figure.flipH();
        }
    }

    @Override
    public void flipV() {
        for(Figure figure : group){
            figure.flipV();
        }
    }

    @Override
    public void rotate() {
        for(Figure figure : group){
            figure.rotate();
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(T figure : group) {
            s.append(figure.toString());
        }
        return s.toString();
    }
}
